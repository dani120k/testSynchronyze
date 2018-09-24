package syncronization;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import syncronization.last_test.DomainHashMap;
import syncronization.last_test.OrgUnitHashMap;
import syncronization.model.*;
import syncronization.update.StructureCreating;

import java.util.*;

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

    public static  void createStruct(ProgrammConfig programmConfig){
        OrgUnit orgUnit = new OrgUnit();
        orgUnit.setUserInfos(new ArrayList<>());
        orgUnit.setDomainId(3L);
        orgUnit.setGroupId(1L);
        orgUnit.setTotalDevices(10L);
        orgUnit.setDescription("desc");
        orgUnit.setOrgUnitName("org_unit_name");
        orgUnit.setId(100L);
        try {
            StructureCreating.createOrgUnit(programmConfig, orgUnit);
            List<OrgUnit> orgUnits = StructureCreating.getListOfOrgUnits(programmConfig);
            OrgUnit curr = new OrgUnit();
            for(OrgUnit orgUnit1:orgUnits){
                if (orgUnit.getOrgUnitName().equals(orgUnit1.getOrgUnitName()))
                    curr = orgUnit1;
            }

            UserInfo userInfo = new UserInfo();
            userInfo.setPosition("position");
            userInfo.setId(1000L);
            userInfo.setPhones("88005553535");
            userInfo.setEmail("ololo1@gmail.com");
            userInfo.setFirstName("fName");
            userInfo.setLastName("lName");
            userInfo.setMidleName("mName");
            userInfo.setRequestCreationTimestamps(new ArrayList<>());
            userInfo.setOrgUnitId(curr.getId());
            //StructureCreating.createUserInfo(programmConfig, userInfo);
            //Thread.sleep(10000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main( String[] args )
    {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        ProgrammConfig programmConfig = new ProgrammConfig();
        programmConfig.setServerHost("127.0.0.1");
        programmConfig.setServerPort("8089");


        HashMap<String, DomainHashMap> domainHashMap = new HashMap<>();
        try {
            List<Domain> domains = StructureCreating.getListOfDomain(programmConfig);
            for(Domain domain : domains){
                domainHashMap.put(domain.getDomainName(), new DomainHashMap(domain));
            }

        } catch(Exception ex){
            ex.printStackTrace();
        }
        System.out.println("end og");
        Collection<DomainHashMap> domainHashMaps = domainHashMap.values();
        for(DomainHashMap dhm : domainHashMaps){
            System.out.println("domain is " + dhm.getDomain().toString());
            System.out.println("orgUnits is " + dhm.getAllOrgUnits().size());
            Collection<OrgUnitHashMap> orgUnitHashMaps = dhm.values();
            for(OrgUnitHashMap orgUnite : orgUnitHashMaps) {
                Collection<UserInfo> userInfos = orgUnite.values();
                for(UserInfo userInfo : userInfos){
                    System.out.println(userInfo);
                }
            }
        }

        /*DomainService domainService = ctx.getBean(DomainService.class);


        try {
            List<Domain> domains = domainService.getAllDomains();
            for(Domain domain : domains) {
                System.out.println(domain.toString());
            }
            //Thread.sleep(10000);
        } catch (Exception ex){
            ex.printStackTrace();
        }

        OrgUnitService orgUnitService = ctx.getBean(OrgUnitService.class);
        List<OrgUnit> orgUnits = orgUnitService.getByDomainId(3L);
        System.out.println("size is " + orgUnits.size());
        for(OrgUnit orgUnit : orgUnits){
            System.out.println(orgUnit.toString());
            if (orgUnit.getUserInfos()!=null){
                System.out.println(orgUnit.getUserInfos().size());
                for(UserInfo userInfo : orgUnit.getUserInfos()){
                    System.out.println(userInfo.toString());
                }
            }
            else
                System.out.println(0);
        }

        try {
            List<Domain> domains = StructureCreating.getListOfDomain(programmConfig);
            HashMap<String, DomainHashMap> domainHashMapHashMap = new HashMap<>();
            for(Domain domain : domains){
                domainHashMapHashMap.put(domain.getDomainName(), new DomainHashMap(domain));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        /*HashMap<String, Domain> domainHashMap = new HashMap<>();
        HashMap<String, OrgUnit> orgUnitHashMap = new HashMap<>();
        HashMap<String, UserInfo> userInfoHashMap = new HashMap<>();
        try {
            List<Domain> domains = StructureCreating.getListOfDomain(programmConfig);
            List<OrgUnit> orgUnits = StructureCreating.getListOfOrgUnits(programmConfig);
            List<UserInfo> userInfos = StructureCreating.getListOfUserInfo(programmConfig);

            for(Domain domain : domains) {
                domainHashMap.put(domain.getDomainName(), domain);
            }

            for(OrgUnit orgUnit : orgUnits) {
                orgUnitHashMap.put(orgUnit.getOrgUnitName(), orgUnit);
            }

            for(UserInfo userInfo : userInfos) {
                userInfoHashMap.put(userInfo.getEmail(), userInfo);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        createStruct(programmConfig);



        //createStruct(programmConfig);
        /*Domain firstDomain = new Domain();
        firstDomain.setOrgUnits(new ArrayList<>());
        firstDomain.setDomainName("test_domain_2");
        firstDomain.setDomainType(1L);

        Domain secondDomain = new Domain();
        secondDomain.setOrgUnits(new ArrayList<>());
        secondDomain.setDomainName("check_2");
        secondDomain.setDomainType(1L);

        List<Domain> newDomains = new ArrayList<>();
        newDomains.add(firstDomain);
        newDomains.add(secondDomain);

        DomainSync domainSync = new DomainSync();

       /* UserInfoService userInfoService = ctx.getBean(UserInfoService.class);
        try {
            for (UserInfo domain : StructureCreating.getListOfUserInfo(programmConfig)) {
                userInfoService.deleteUserInfo(domain);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }*/

        //domainSync.syncDomains(newDomains);
       /* UserInfoService userInfoService = ctx.getBean(UserInfoService.class);

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
