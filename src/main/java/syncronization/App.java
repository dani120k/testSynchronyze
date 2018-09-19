package syncronization;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.core.userdetails.User;
import syncronization.model.*;
import syncronization.update.StructureCreating;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static List<Domain> getNewInfo(){
        return new ArrayList<>();
    }

    public static void updateDomain(Domain domain){
    }

    public static void createDomain(Domain domain){
    }

    public static void updateOrgUnit(OrgUnit orgUnit){
    }

    public static void createOrgUnit(OrgUnit orgUnit){
    }

    public static List<UserInfo> getUserInfo(){
        return new ArrayList<>();
    }

    public static void main( String[] args )
    {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        UserInfoService userInfoService = ctx.getBean(UserInfoService.class);

        UserInfo userInfo1 = new UserInfo();
        userInfo1.setFirstName("fName4");
        userInfo1.setLastName("lName4");
        userInfo1.setEmail("email@gmail.com");
        userInfo1.setOrgUnitId(1L);
        userInfo1.setPhones("8555353535");
        userInfo1.setPosition("position");
        //userInfoService.addUserInfo(userInfo1);
        ProgrammConfig programmConfig = new ProgrammConfig();
        programmConfig.setServerHost("127.0.0.1");
        programmConfig.setServerPort("8089");

        HashMap<String, UserInfo> userInfoHashMap = new HashMap<>();
        try {
            //Thread.sleep(10000);
            List<UserInfo> userInfos = StructureCreating.getListOfUserInfo(programmConfig);
            for(UserInfo userInfo : userInfos) {
                userInfoHashMap.put(userInfo.getEmail(), userInfo);
            }

            List<UserInfo> updatedInfo = new ArrayList<>();
            updatedInfo.add(userInfo1);

            for(UserInfo userInfo : updatedInfo) {
                if (userInfoHashMap.containsKey(userInfo.getEmail())){
                   // userInfoService.updateUserInfo(userInfo);
                    userInfoHashMap.remove(userInfo.getEmail());
                }
                else
                {
                    userInfoService.addUserInfo(userInfo);
                }
            }
            for(UserInfo userInfo : userInfoHashMap.values())
            {
                System.out.println("delete " + userInfo.toString());
                userInfoService.deleteUserInfo(userInfo);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


/*

        HashMap<String, UserInfo> currOrgUnit = new HashMap<>();
        ProgrammConfig programmConfig = new ProgrammConfig();
        programmConfig.setServerHost("127.0.0.1");
        programmConfig.setServerPort("8089");
        Domain domain = new Domain();
        domain.setDomainType(0L);
        domain.setDomainName("check");
        domain.setId(2L);

        OrgUnit orgUnit = new OrgUnit();
        orgUnit.setOrgUnitName("TEST_org_unit_name_5");
        orgUnit.setId(4L);
        orgUnit.setGroupId(1L);
        orgUnit.setDescription("desc");
        orgUnit.setDomainId(2L);
        orgUnit.setUserInfos(new ArrayList<>());
        ArrayList<OrgUnit> orgUnits = new ArrayList<>();
        orgUnits.add(orgUnit);
        domain.setOrgUnits(orgUnits);

        DomainHashTable hashTable = new DomainHashTable();



        try {
            StructureCreating.updateDomain(programmConfig, domain);
            List<Domain> domains = StructureCreating.getListOfDomain(programmConfig);
            hashTable.importing(domains);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println(hashTable.keySet().toString());*/
    }
}
