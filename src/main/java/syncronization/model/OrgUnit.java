package syncronization.model;

import com.google.gson.Gson;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.DnAttribute;
import org.springframework.ldap.odm.annotations.Entry;
import syncronization.importFrom.normalMapper.OrgUnitContextMapper;

import javax.naming.Name;
import javax.persistence.*;
import java.util.Collection;


@Entity
@Table(name = "org_units")
@Entry(objectClasses = { "organizationalUnit", "top" })
public class OrgUnit {

    @org.springframework.ldap.odm.annotations.Id
    @Transient
    private Name dn;

    @Override
    public boolean equals(Object obj) {
        OrgUnit newOrgUnit = (OrgUnit) obj;
        if (newOrgUnit.getDescription() == null)
            newOrgUnit.setDescription("");
        if (description == null)
            description = "";
        if (newOrgUnit.getOrgUnitName().equals(this.getOrgUnitName()) && newOrgUnit.getDescription().equals(this.description))
            return true;
        else
            return false;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.ldap.odm.annotations.Transient
    private Long id;

    @Column(name = "name")
    @Attribute(name="distinguishedName")
    private String orgUnitName;

    @Column(name = "description")
    @Attribute(name="description")
    private String description;

    @Column(name = "group_id")
    @org.springframework.ldap.odm.annotations.Transient
    private Long groupId;

    @Column(name = "domain_id")
    @org.springframework.ldap.odm.annotations.Transient
    private Long domainId;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="org_unit_id", referencedColumnName="id", updatable = false)
    @org.springframework.ldap.odm.annotations.Transient
    private Collection<UserInfo> userInfos;

    @Transient
    @org.springframework.ldap.odm.annotations.Transient
    private Long totalUsers;


    @Transient
    @org.springframework.ldap.odm.annotations.Transient
    private Long totalDevices;


    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrgUnitName() {
        return orgUnitName;
    }

    public void setOrgUnitName(String orgUnitName) {
        this.orgUnitName = orgUnitName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Collection<UserInfo> getUserInfos() {
        return userInfos;
    }

    public void setUserInfos(Collection<UserInfo> userInfos) {
        this.userInfos = userInfos;
    }

    public Long getDomainId() {
        return domainId;
    }

    public void setDomainId(Long domainId) {
        this.domainId = domainId;
    }

    public Long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(Long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public Long getTotalDevices() {
        return totalDevices;
    }

    public void setTotalDevices(Long totalDevices) {
        this.totalDevices = totalDevices;
    }
}
