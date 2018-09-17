package syncronization.model;

import com.google.gson.Gson;

import java.util.Collection;

public class UserCredentials {

    private Long id;
    private Long orgUnitId;

    private Long userInfoId;
    private String userLogin;

    private Boolean enabled;

    private Collection<DeviceInfo> deviceInfos;


    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public Collection<DeviceInfo> getDeviceInfos() {
        return deviceInfos;
    }

    public void setDeviceInfos(Collection<DeviceInfo> deviceInfos) {
        this.deviceInfos = deviceInfos;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Long getOrgUnitId() {
        return orgUnitId;
    }

    public void setOrgUnitId(Long orgUnitId) {
        this.orgUnitId = orgUnitId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(Long userInfoId) {
        this.userInfoId = userInfoId;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
