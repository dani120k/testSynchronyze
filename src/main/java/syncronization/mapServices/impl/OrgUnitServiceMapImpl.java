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
    @Autowired
    OrgUnitService orgUnitService = new AnnotationConfigApplicationContext(ApplicationConfig.class).getBean(OrgUnitService.class);
    private OrgUnitHashMap orgUnitHashMap;

    public OrgUnitServiceMapImpl(OrgUnitHashMap orgUnitHashMap){
        this.orgUnitHashMap = orgUnitHashMap;
    }

    public OrgUnitServiceMapImpl(){}

    @Override
    public void update(OrgUnit newOrgUnit){
        OrgUnit oldOrgUnit = orgUnitHashMap.getOrgUnit();
        List<UserInfo> userInfos = new ArrayList<>();
        for(UserInfo userInfo : newOrgUnit.getUserInfos()){
            if (orgUnitHashMap.containsKey(userInfo.getEmail())){
                userInfo.setOrgUnitId(oldOrgUnit.getId());
                userInfo.setId(orgUnitHashMap.get(userInfo.getEmail()).getId());
                new UserInfoMapImpl().update(userInfo);
                orgUnitHashMap.remove(userInfo.getEmail());
            } else {
                userInfo.setId(10000L);
                userInfo.setOrgUnitId(oldOrgUnit.getId());
                userInfos.add(userInfo);
            }
        }

        for(UserInfo userInfo : orgUnitHashMap.values()){
            userInfo.setOrgUnitId(oldOrgUnit.getId());
            new UserInfoMapImpl().delete(userInfo);
        }

        for(UserInfo userInfo : userInfos){
            userInfo.setOrgUnitId(oldOrgUnit.getId());
            new UserInfoMapImpl().add(userInfo);
        }

        newOrgUnit.setDomainId(oldOrgUnit.getDomainId());
        newOrgUnit.setGroupId(1L);
        newOrgUnit.setId(oldOrgUnit.getId());
        newOrgUnit.setUserInfos(new ArrayList<>());
        orgUnitService.updateOrgUnit(newOrgUnit);


    }

    @Override
    public void add(OrgUnit orgUnit){
        System.out.println(orgUnit.toString());
        orgUnitService.addOrgUnit(orgUnit);
        OrgUnit updatedOrgUnit = orgUnitService.getByOrgUnitName(orgUnit.getOrgUnitName());
        if (orgUnit.getUserInfos()!= null){
            for(UserInfo userInfo : orgUnit.getUserInfos()){
                userInfo.setOrgUnitId(updatedOrgUnit.getId());
                new UserInfoMapImpl().add(userInfo);
            }
        }
    }

    @Override
    public void delete(OrgUnit orgUnit){
        for(UserInfo userInfo : orgUnit.getUserInfos()){
            new UserInfoMapImpl().delete(userInfo);
        }
        orgUnit.setUserInfos(new ArrayList<>());
        orgUnitService.deleteOrgUnit(orgUnit);
    }
}
