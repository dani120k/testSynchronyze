package syncronization;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import syncronization.model.UserInfo;
import syncronization.model.UserInfoService;

public class UserInfoMapImpl {
    public static AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);

    public static void addUserInfo(UserInfo userInfo){
        System.out.println("add user " + userInfo.toString());
        try{
            Thread.sleep(10000);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        userInfo.setId(100000L);
        UserInfoService userInfoService = ctx.getBean(UserInfoService.class);
        userInfoService.addUserInfo(userInfo);
    }

    public static void deleteUserInfo(UserInfo userInfo){
        System.out.println("delete user info " + userInfo.toString());
        try{
            Thread.sleep(10000);
        } catch(Exception ex){
            ex.printStackTrace();
        }
        UserInfoService userInfoService = ctx.getBean(UserInfoService.class);
        userInfoService.deleteUserInfo(userInfo);
    }

    public static void fullUpdateUserInfo(UserInfo userInfo){
        UserInfoService userInfoService = ctx.getBean(UserInfoService.class);
        System.out.println("Update userInfo " + userInfo.toString());
        try{
            Thread.sleep(10000);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        userInfoService.updateUserInfo(userInfo);
    }
}
