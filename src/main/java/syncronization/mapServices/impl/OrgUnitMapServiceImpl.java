package syncronization.mapServices.impl;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import syncronization.ApplicationConfig;
import syncronization.last_test.DomainHashMap;
import syncronization.last_test.OrgUnitHashMap;
import syncronization.mapServices.OrgUnitMapService;
import syncronization.model.OrgUnit;
import syncronization.model.OrgUnitService;
import syncronization.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class OrgUnitMapServiceImpl implements OrgUnitMapService {
    public static AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);

    public static void fullUpdateOrgUnit(OrgUnit orgUnit, DomainHashMap domainHashMap){
        OrgUnitService orgUnitService = ctx.getBean(OrgUnitService.class);
        List<OrgUnit> orgUnitList = new ArrayList<>();
        if (domainHashMap.containsKey(orgUnit.getOrgUnitName())){
            orgUnitList.add(domainHashMap.get(orgUnit.getOrgUnitName()).getOrgUnit());
        } else {
            orgUnit.setId(1000L); //fix it
            orgUnit.setDomainId(domainHashMap.getDomain().getId());
            orgUnit.setGroupId(1L);
            orgUnitService.addOrgUnit(orgUnit);

            OrgUnit updatedOrgUnit = orgUnitService.getByOrgUnitName(orgUnit.getOrgUnitName());
            for(UserInfo userInfo : orgUnit.getUserInfos()){
                userInfo.setOrgUnitId(updatedOrgUnit.getId());
                UserInfoMapServiceImpl.fullAddUserInfo(userInfo);
            }
        }

        for(OrgUnitHashMap orgUnit1 : domainHashMap.values()){
            fullDeleteOrgUnit(orgUnit1);
        }

        for(OrgUnit orgUnit1 : orgUnitList){

            orgUnit.setId(orgUnit1.getId());
            orgUnitService.updateOrgUnit(orgUnit);
            for(UserInfo userInfo : orgUnit.getUserInfos()){
                UserInfoMapServiceImpl.fullUpdateUserInfo(userInfo);
            }
        }
    }

    public static void fullDeleteOrgUnit(OrgUnitHashMap orgUnitHashMap) {
        OrgUnitService orgUnitMapService = ctx.getBean(OrgUnitService.class);
        if (orgUnitHashMap.getOrgUnit() != null && orgUnitHashMap.getOrgUnit().getUserInfos() != null) {
            for (UserInfo userInfo : orgUnitHashMap.getOrgUnit().getUserInfos()) {
               // UserInfoMapServiceImpl.fullDeleteUserInfo(userInfo);
            }
        }
        orgUnitMapService.deleteOrgUnit(orgUnitHashMap.getOrgUnit());
    }

    public static void fullAddOrgUnit(OrgUnit orgUnit){
        OrgUnitService orgUnitService = ctx.getBean(OrgUnitService.class);
        orgUnit.setId(100000L);//fix it
        orgUnitService.addOrgUnit(orgUnit);
        OrgUnit updatedOrgUnit = orgUnitService.getByOrgUnitName(orgUnit.getOrgUnitName());
        for(UserInfo userInfo : orgUnit.getUserInfos()){
            userInfo.setOrgUnitId(updatedOrgUnit.getId());
            UserInfoMapServiceImpl.fullAddUserInfo(userInfo);
        }


    }
}
