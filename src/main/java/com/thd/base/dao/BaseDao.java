package com.thd.base.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.annotation.Resource;

@Repository
@SuppressWarnings("unchecked")
public class BaseDao {
    private HibernateTemplate hibernateTemplate;

    @Autowired
    public BaseDao(SessionFactory sessionFactory) {
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    public BaseDao() {
    }

    public Session getSession() {
        return hibernateTemplate.getSessionFactory().getCurrentSession();
        // 需要开启事物，才能得到CurrentSession
        //return sessionFactory.getCurrentSession();
    }

    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public int countRecordsNumber(DetachedCriteria dc, String countDistinctProjections) {
        dc.setProjection(Projections.countDistinct(countDistinctProjections));
        List list = this.getHibernateTemplate().findByCriteria(dc);
        int result = 0;
        for (Object aList : list) {
            Integer item = Integer.parseInt(aList + "");
            result += item;
        }

        dc.setProjection(null);// 避免对dc.setProjection影响到其它地方
        return result;
    }

    public List findByCriteria(DetachedCriteria dc, int... firstResultAndMaxResults) {
        dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        if (firstResultAndMaxResults != null && firstResultAndMaxResults.length == 2) {
            return this.getHibernateTemplate().findByCriteria(dc, firstResultAndMaxResults[0],
                    firstResultAndMaxResults[1]);
        }

        return getHibernateTemplate().findByCriteria(dc);
    }

    public List findByCriteria(DetachedCriteria dc) {
        dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return getHibernateTemplate().findByCriteria(dc);
    }

    /**
     * 求传入的QBC查询总记录条数
     *
     * @param criteria QBC对象
     * @return
     */
    public int getTotalSizeForCriteria(DetachedCriteria criteria) {
        // 获取根据条件分页查询的总行数
        int totalSize = 0;
        criteria.setProjection(Projections.rowCount());
        List list = this.findByCriteria(criteria);
        if (list.isEmpty()) {
            return totalSize;
        } else {
            totalSize = Integer.parseInt(list.get(0).toString());
        }
        criteria.setProjection(null);// 清除count函数
        return totalSize;
    }

    /**
     * 使用HSQL语句检索数据
     *
     * @param queryString
     * @return
     */
    public List find(String queryString) {
        //return getHibernateTemplate().find(queryString);
        Query query = this.getSession().createQuery(queryString);
        return query.list();
    }

    /**
     * 使用带参数的HSQL语句检索数据
     *
     * @param queryString
     * @param value
     * @return
     */
    public List find(String queryString, Object value) {
        //return getHibernateTemplate().find(queryString, value);
        Query query = this.getSession().createQuery(queryString);
        query.setParameter(0, value);
        return query.list();
    }

    /**
     * 使用带参数的HSQL语句检索数据
     *
     * @param queryString
     * @param value
     * @return
     */
    public List find(String queryString, Object[] value) {
        Query query = this.getSession().createQuery(queryString);
        if (value != null) {
            for (int i = 0; i < value.length; i++) {
                query.setParameter(i, value[i]);
            }
        }
        return query.list();

        //return getHibernateTemplate().find(queryString, value);
    }

    /**
     * 使用HSQL语句直接增加、更新、删除实体
     *
     * @param queryString
     * @param values
     * @return
     */
    public int bulkUpdate(String queryString, Object[] values) {
        return getHibernateTemplate().bulkUpdate(queryString, values);
    }

    public <T> T get(Class<T> entityClass, Serializable id) {
        Object result = getHibernateTemplate().get(entityClass, id);
        Assert.notNull(result, "Class[" + entityClass + "] with id[" + id + "] not found!");
        return (T) result;
    }

    public <T> T load(Class<T> entityClass, Serializable id) {
        return (T) getHibernateTemplate().load(entityClass, id);
    }

    public <T> T merge(T model) {
        return (T)this.getSession().merge(model);
    }

    public void saveOrUpdate(Object model) {
        this.getSession().saveOrUpdate(model);
    }

    public String save(Object model) {
        return (String) this.getSession().save(model);
    }

    public <T> void delete(Class<T> entityClass, Serializable id) {
        this.getSession().delete(get(entityClass, id));
    }

    public <T> void delete(T entity) {
        this.getSession().delete(entity);
    }

    public void flush() {
        this.getSession().flush();
    }

    /**
     * 查询某列的最大值id
     *
     * @param criteria QBC对象
     * @return
     */
    public int getMaxIdForCriteria(DetachedCriteria criteria, String propertyName) {
        int maxSize = 0;
        criteria.setProjection(Projections.max(propertyName));
        List list = new ArrayList();
        list = this.findByCriteria(criteria);
        if (list.isEmpty()) {
            return maxSize;
        } else {
            if (list.get(0) == null) {
                return maxSize;
            } else {
                maxSize = Integer.parseInt(list.get(0).toString());
            }
        }
        criteria.setProjection(null);// 清除count函数
        return maxSize;
    }
    //TODO ADD JdbcTemplate

    /**
     * <根据HQL语句查找唯一实体>
     *
     * @param hqlString HQL语句
     * @param values    不定参数的Object数组
     * @return 查询实体
     */
    public <T> T getByHQL(String hqlString, Object... values) {
        Query query = this.getSession().createQuery(hqlString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }

        return (T) query.uniqueResult();
    }

    /**
     * <根据HQL语句查找唯一实体>
     *
     * @param hqlString HQL语句
     * @return 查询实体
     */
    public <T> T getByHQL(String hqlString) {
        Query query = this.getSession().createQuery(hqlString);
        return (T) query.uniqueResult();
    }

    /**
     * <根据SQL语句查找唯一实体>
     *
     * @param sqlString SQL语句
     * @param values    不定参数的Object数组
     * @return 查询实体
     */
    public <T> T getBySQL(String sqlString, Object... values) {
        Query query = this.getSession().createSQLQuery(sqlString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return (T) query.uniqueResult();
    }

    /**
     * <根据HQL得到记录数>
     *
     * @param hql    HQL语句
     * @param values 不定参数的Object数组
     * @return 记录总数
     */
    public <T> T countByHql(String hql, Object... values) {
        Query query = this.getSession().createQuery(hql);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return (T) query.uniqueResult();
    }

}