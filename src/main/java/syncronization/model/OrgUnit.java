package syncronization.model;

import com.google.gson.Gson;

import java.util.Collection;

public class OrgUnit {

    private Long id;
    private String orgUnitName;
    private String description;
    private Long groupId;
    private Long domainId;
    private Collection<UserInfo> userInfos;
    private Long totalUsers;

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

