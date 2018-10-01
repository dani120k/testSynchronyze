package syncronization.model;

import com.google.gson.Gson;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;

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

    public String getDistinguishedName() {
        return distinguishedName;
    }

    public void setDistinguishedName(String distinguishedName) {
        this.distinguishedName = distinguishedName;
    }
    @Transient
    @Attribute(name="distinguishedName")
    private String distinguishedName;

    //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @Attribute(name="cn")
    private String orgUnitName;

    @Column(name = "description")
    private String description;

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "domain_id")
    private Long domainId;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="org_unit_id", referencedColumnName="id", updatable = false)
    private Collection<UserInfo> userInfos;

    @Transient
    private Long totalUsers;


    @Transient
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
