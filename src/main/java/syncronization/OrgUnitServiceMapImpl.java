package syncronization;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import syncronization.last_test.OrgUnitHashMap;
import syncronization.mapServices.impl.UserInfoMapServiceImpl;
import syncronization.model.OrgUnit;
import syncronization.model.OrgUnitService;
import syncronization.model.UserInfo;
import syncronization.model.UserInfoService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrgUnitServiceMapImpl  {
    public static AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);


    public OrgUnitServiceMapImpl(){

    }

    public static void fullUpdate(OrgUnitHashMap orgUnitHashMap, OrgUnit newOrgUnit){
        OrgUnitService orgUnitService = ctx.getBean(OrgUnitService.class);
        OrgUnit oldOrgUnit = orgUnitHashMap.getOrgUnit();
        List<UserInfo> userInfos = new ArrayList<>();
        for(UserInfo userInfo : newOrgUnit.getUserInfos()){
            if (orgUnitHashMap.containsKey(userInfo.getEmail())){
                userInfo.setOrgUnitId(oldOrgUnit.getId());
                userInfo.setId(orgUnitHashMap.get(userInfo.getEmail()).getId());
                UserInfoMapImpl.fullUpdateUserInfo(userInfo);
                orgUnitHashMap.remove(userInfo.getEmail());
            } else {
                userInfo.setId(10000L);
                userInfo.setOrgUnitId(oldOrgUnit.getId());
                userInfos.add(userInfo);
            }
        }

        for(UserInfo userInfo : orgUnitHashMap.values()){
            userInfo.setOrgUnitId(oldOrgUnit.getId());
            UserInfoMapImpl.deleteUserInfo(userInfo);
        }

        for(UserInfo userInfo : userInfos){
            userInfo.setOrgUnitId(oldOrgUnit.getId());
            UserInfoMapImpl.addUserInfo(userInfo);
        }

        newOrgUnit.setDomainId(oldOrgUnit.getDomainId());
        newOrgUnit.setGroupId(1L);
        newOrgUnit.setId(oldOrgUnit.getId());
        newOrgUnit.setUserInfos(new ArrayList<>());
        orgUnitService.updateOrgUnit(newOrgUnit);


    }

    public static void fullAdd(OrgUnit orgUnit){
        OrgUnitService orgUnitService = ctx.getBean(OrgUnitService.class);
        System.out.println(orgUnit.toString());
        orgUnitService.addOrgUnit(orgUnit);
        OrgUnit updatedOrgUnit = orgUnitService.getByOrgUnitName(orgUnit.getOrgUnitName());
        if (orgUnit.getUserInfos()!= null){
            for(UserInfo userInfo : orgUnit.getUserInfos()){
                userInfo.setOrgUnitId(updatedOrgUnit.getId());
                UserInfoMapImpl.addUserInfo(userInfo);
            }
        }
    }

    public static void fullDelete(OrgUnit orgUnit){
        OrgUnitService orgUnitService = ctx.getBean(OrgUnitService.class);
        for(UserInfo userInfo : orgUnit.getUserInfos()){
            UserInfoMapImpl.deleteUserInfo(userInfo);
        }
        orgUnit.setUserInfos(new ArrayList<>());
        orgUnitService.deleteOrgUnit(orgUnit);
    }
}
