package syncronization.mapServices.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import syncronization.ApplicationConfig;
import syncronization.mapServices.ServiceMap;
import syncronization.model.UserInfo;
import syncronization.model.UserInfoService;

public class UserInfoMapImpl implements ServiceMap<UserInfo> {
    @Autowired
    UserInfoService userInfoService = new AnnotationConfigApplicationContext(ApplicationConfig.class).getBean(UserInfoService.class);

    public UserInfoMapImpl(){

    }

    @Override
    public void add(UserInfo userInfo){
        userInfo.setId(100000L);
        userInfoService.addUserInfo(userInfo);
    }

    @Override
    public void delete(UserInfo userInfo){
        userInfoService.deleteUserInfo(userInfo);
    }

    @Override
    public void update(UserInfo userInfo){
        System.out.println(userInfo.toString());
        if (userInfoService==null)
            System.out.println("fuck");
        try{
            //Thread.sleep(10000);
        } catch (Exception ex){
            ex.printStackTrace();
        }

        userInfoService.updateUserInfo(userInfo);
    }
}
