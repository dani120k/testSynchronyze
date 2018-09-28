package syncronization.mapServices.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import syncronization.ApplicationConfig;
import syncronization.hashMaps.OrgUnitHashMap;
import syncronization.mapServices.ServiceMap;
import syncronization.model.OrgUnit;
import syncronization.model.OrgUnitService;
import syncronization.model.UserInfo;
import syncronization.model.UserInfoService;

import java.util.ArrayList;
import java.util.List;

public class OrgUnitServiceMapImpl implements ServiceMap<OrgUnit> {
    static OrgUnitService orgUnitService = new AnnotationConfigApplicationContext(ApplicationConfig.class).getBean(OrgUnitService.class);
    private OrgUnitHashMap orgUnitHashMap;

    public OrgUnitServiceMapImpl(OrgUnitHashMap orgUnitHashMap){
        this.orgUnitHashMap = orgUnitHashMap;
    }

    public OrgUnitServiceMapImpl(){}

    @Override
    public void update(OrgUnit newOrgUnit){
        OrgUnit oldOrgUnit = orgUnitHashMap.getOrgUnit();
        List<UserInfo> userInfos = new ArrayList<>();
        if (newOrgUnit.getUserInfos() == null)
            newOrgUnit.setUserInfos(new ArrayList<>());
        for(UserInfo userInfo : newOrgUnit.getUserInfos()){
            if (orgUnitHashMap.containsKey(userInfo.getEmail())){
                userInfo.setOrgUnitId(oldOrgUnit.getId());
                userInfo.setId(orgUnitHashMap.get(userInfo.getEmail()).getId());
                new UserInfoMapImpl(orgUnitHashMap.get(userInfo.getEmail())).update(userInfo);
                orgUnitHashMap.remove(userInfo.getEmail());
            } else {
                userInfo.setId(10000L);
                userInfo.setOrgUnitId(oldOrgUnit.getId());
                userInfos.add(userInfo);
            }
        }

        if (orgUnitHashMap.size()!=0) {
            for (UserInfo userInfo : orgUnitHashMap.values()) {
                //userInfo.setOrgUnitId(oldOrgUnit.getId());
                new UserInfoMapImpl(new UserInfo()).delete(userInfo);
                //orgUnitHashMap.remove(userInfo.getEmail());
            }
            orgUnitHashMap.clear();
        }

        for(UserInfo userInfo : userInfos){
            userInfo.setOrgUnitId(oldOrgUnit.getId());
            new UserInfoMapImpl(userInfo).add(userInfo);
        }

        newOrgUnit.setDomainId(oldOrgUnit.getDomainId());
        newOrgUnit.setGroupId(1L);
        newOrgUnit.setId(oldOrgUnit.getId());
        newOrgUnit.setUserInfos(new ArrayList<>());
        if (updateIsRequired(newOrgUnit, oldOrgUnit)) {
            loginfo("update OrgUnit", newOrgUnit);
            orgUnitService.updateOrgUnit(newOrgUnit);
        }


    }

    private boolean updateIsRequired(OrgUnit newOrgUnit, OrgUnit oldOrgUnit){
        if (newOrgUnit.equals(oldOrgUnit)){
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void add(OrgUnit orgUnit){
        System.out.println(orgUnit.toString());
        loginfo("add orgUnit", orgUnit);
        orgUnitService.addOrgUnit(orgUnit);
        OrgUnit updatedOrgUnit = orgUnitService.getByOrgUnitName(orgUnit.getOrgUnitName());
        loginfo("get updated orgUnit", orgUnit);
        if (orgUnit.getUserInfos()!= null){
            for(UserInfo userInfo : orgUnit.getUserInfos()){
                userInfo.setOrgUnitId(updatedOrgUnit.getId());
                new UserInfoMapImpl(userInfo).add(userInfo);
            }
        }
    }

    @Override
    public void delete(OrgUnit orgUnit){
        for(UserInfo userInfo : orgUnit.getUserInfos()){
            new UserInfoMapImpl(userInfo).delete(userInfo);
        }
        orgUnit.setUserInfos(new ArrayList<>());
        loginfo("delete orgUnit", orgUnit);
        orgUnitService.deleteOrgUnit(orgUnit);
    }

    private void loginfo(String info, Object object){
        System.out.println(info);
        System.out.println(object.toString());
        try{
           // Thread.sleep(1000);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
