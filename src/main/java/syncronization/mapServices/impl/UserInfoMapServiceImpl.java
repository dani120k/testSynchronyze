package syncronization.mapServices.impl;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import syncronization.ApplicationConfig;
import syncronization.last_test.OrgUnitHashMap;
import syncronization.mapServices.UserInfoMapService;
import syncronization.model.OrgUnit;
import syncronization.model.UserInfo;
import syncronization.model.UserInfoService;

public class UserInfoMapServiceImpl implements UserInfoMapService {
    public static AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);

    public static void fullUpdateUserInfo(UserInfo userInfo){

    }

    public static void addUserInfo(OrgUnitHashMap orgUnitHashMap, UserInfo userInfo){
        UserInfoService userInfoService = ctx.getBean(UserInfoService.class);
        userInfo.setOrgUnitId(orgUnitHashMap.getOrgUnit().getId());
        userInfoService.addUserInfo(userInfo);
    }

    public static void fullDeleteUserInfo(UserInfo userInfo){
        UserInfoService userInfoService = ctx.getBean(UserInfoService.class);
        userInfoService.deleteUserInfo(userInfo);
    }

    public static void fullAddUserInfo(UserInfo userInfo){
        UserInfoService userInfoService = ctx.getBean(UserInfoService.class);
        userInfo.setId(10000L); //fix it
        userInfoService.addUserInfo(userInfo);
    }
}
