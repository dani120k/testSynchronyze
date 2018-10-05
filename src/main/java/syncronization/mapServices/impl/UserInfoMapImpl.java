package syncronization.mapServices.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import syncronization.ApplicationConfig;
import syncronization.hashMaps.OrgUnitHashMap;
import syncronization.mapServices.ServiceMap;
import syncronization.model.UserInfo;
import syncronization.model.UserInfoService;

import javax.swing.*;


public class UserInfoMapImpl implements ServiceMap<UserInfo> {
    static UserInfoService userInfoService = new AnnotationConfigApplicationContext(ApplicationConfig.class).getBean(UserInfoService.class);
    UserInfo oldUserInfo;

    public UserInfoMapImpl(UserInfo oldUserInfo){
        this.oldUserInfo = oldUserInfo;
    }

    @Override
    public void add(UserInfo userInfo){
        userInfo.setId(100000L);
        System.out.println(userInfo.toString());
        userInfo.setAfterProperties();
        loginfo("addUserInfo", userInfo, null);
        System.out.println(userInfo.toString());
        userInfoService.addUserInfo(userInfo);
    }

    @Override
    public void delete(UserInfo userInfo){
        loginfo("delete userInfo", userInfo, null);
        userInfoService.deleteUserInfo(userInfo);
    }

    @Override
    public void update(UserInfo newuserInfo){
        System.out.println(newuserInfo.toString());

        newuserInfo.setAfterProperties();
        if (updateIsRequired(newuserInfo, oldUserInfo)) {
            loginfo("update user info", newuserInfo, oldUserInfo);
            userInfoService.updateUserInfo(newuserInfo);
        }
    }

    private void loginfo(String info, Object object, Object updatedObject){
        System.out.println(info);
        System.out.println(object.toString());
        if (updatedObject!= null){
            System.out.println(updatedObject.toString());
            System.out.println(object.equals(updatedObject));
        }
        try{
         //   Thread.sleep(100);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private boolean updateIsRequired(UserInfo newUserInfo, UserInfo oldUserInfo){
        if (newUserInfo.equals(oldUserInfo))
            return false;
        else
            return true;
    }
}
