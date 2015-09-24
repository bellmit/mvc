package com.thd.base.dao.interceptor;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import com.thd.base.model.BaseModel;
import com.thd.base.security.util.AuthenticationUtil;

@SuppressWarnings("serial")
public class BaseDaoInterceptor extends EmptyInterceptor {

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        return audit(entity, state, propertyNames);
    }

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState,
                                String[] propertyNames, Type[] types) throws CallbackException {
        for (int i = 0; i < propertyNames.length; i++) {
            if ("lastUpdateDate".equals(propertyNames[i])) {
                currentState[i] = new Date();
            } else if ("lastUpdateUser".equals(propertyNames[i])) {
                currentState[i] = AuthenticationUtil.getCurrentUser();
            }
        }
        return true;
    }

    private boolean audit(Object entity, Object[] state, String[] propertyNames) {
        boolean changed = false;
        if (entity instanceof BaseModel) {
            for (int i = 0; i < propertyNames.length; i++) {
                String propertyName = propertyNames[i];
                if ("crtDate".equals(propertyName)) {
                    Object currState = state[i];
                    if (currState == null) {
                        state[i] = new Date();
                        changed = true;
                    }
                } else if ("lastUpdateDate".equals(propertyName)) {
                    Object currState = state[i];
                    if (currState == null) {
                        state[i] = new Date();
                        changed = true;
                    }
                } else if ("crtUser".equals(propertyName)) {
                    Object currState = state[i];
                    if (currState == null) {
                        state[i] = AuthenticationUtil.getCurrentUser();
                        changed = true;
                    }
                } else if ("lastUpdateUser".equals(propertyName)) {
                    Object currState = state[i];
                    if (currState == null) {
                        state[i] = AuthenticationUtil.getCurrentUser();
                        changed = true;
                    }
                }
            }
        }

        return changed;
    }
}