package com.thd.system.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.thd.base.model.BaseModel;
import org.springframework.context.annotation.Lazy;

@Entity
public class Organization extends BaseModel {

    /**
     * 机构名称
     */
    @Column(nullable = false)
    private String orgName;

    /**
     * 机构说明
     */
    @Column(length = 3000)
    private String orgMemo;

    /**
     * 机构地址
     */
    @Column(length = 1000)
    private String orgAddress;

    @ManyToMany(mappedBy = "organizations", fetch = FetchType.EAGER)
    @Cascade(value = CascadeType.SAVE_UPDATE)
    @Fetch(FetchMode.SELECT)
    private List<Operator> users = new ArrayList<Operator>();

    @ManyToOne
    @Cascade(value = CascadeType.SAVE_UPDATE)
    //@JoinColumn(name = "FK_PARENT_ORG_ID")
    //	@Column
    private Organization parentOrganization;

    @OneToMany(mappedBy = "parentOrganization", fetch = FetchType.EAGER)
    @Cascade(value = CascadeType.SAVE_UPDATE)
    @Fetch(FetchMode.SELECT)
    //	@Column
    private List<Organization> childOrgs = new ArrayList<Organization>();

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgMemo() {
        return orgMemo;
    }

    public void setOrgMemo(String orgMemo) {
        this.orgMemo = orgMemo;
    }

    public String getOrgAddress() {
        return orgAddress;
    }

    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress;
    }

    public List<Operator> getUsers() {
        return users;
    }

    public void setUsers(List<Operator> users) {
        this.users = users;
    }

    public void addUser(Operator user) {
        if (user != null && !user.getOrganizations().contains(this)
                && !this.getUsers().contains(user)) {
            user.getOrganizations().add(this);
            this.getUsers().add(user);
        }
    }

    public void removeUser(Operator user) {
        if (user != null) {
            user.getOrganizations().remove(this);
            this.getUsers().remove(user);
        }
    }

    public Organization getParentOrganization() {
        return parentOrganization;
    }

    public void setParentOrganization(Organization parentOrganization) {
        this.parentOrganization = parentOrganization;
    }

    public List<Organization> getChildOrgs() {
        return childOrgs;
    }

    public void setChildOrgs(List<Organization> childOrgs) {
        this.childOrgs = childOrgs;
    }

    public void addChildOrg(Organization childOrg) {
        if (childOrg != null) {
            childOrg.setParentOrganization(this);
            if (!this.getChildOrgs().contains(childOrg)) {
                this.getChildOrgs().add(childOrg);
            }
        }
    }

    public void clear() {
        for (Operator user : this.getUsers()) {
            user.getOrganizations().remove(this);
        }
        this.getUsers().clear();

        for (Organization org : this.getChildOrgs()) {
            org.setParentOrganization(null);
        }
        this.getChildOrgs().clear();

        if (this.getParentOrganization() != null) {
            this.getParentOrganization().getChildOrgs().remove(this);
            this.setParentOrganization(null);
        }
    }

}
