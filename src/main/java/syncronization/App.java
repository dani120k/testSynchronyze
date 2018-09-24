package syncronization;

import org.springframework.beans.factory.annotation.Autowired;
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
    public static AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);

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

    public static void fullDomainDelete(DomainHashMap domain){
        DomainService domainService = ctx.getBean(DomainService.class);
        OrgUnitService orgUnitService = ctx.getBean(OrgUnitService.class);
        UserInfoService userInfoService = ctx.getBean(UserInfoService.class);

        if (domain.values().size() == 0){
            domainService.deleteDomain(domain.getDomain());
        }
        else
        {
            Collection<OrgUnitHashMap> orgUnitHashMaps = domain.values();
            for(OrgUnitHashMap orgUnitHashMap : orgUnitHashMaps){
                if (orgUnitHashMap.values().size() == 0){
                    System.out.println(orgUnitHashMap.getOrgUnit().toString());
                    orgUnitService.deleteOrgUnit(orgUnitHashMap.getOrgUnit());
                }
                else
                {
                    Collection<UserInfo> userInfos = orgUnitHashMap.values();
                    for(UserInfo userInfo : userInfos){
                        userInfoService.deleteUserInfo(userInfo);
                    }
                }
            }
        }
    }

    public static void fullDomainAdd(Domain domain){
        DomainService domainService = ctx.getBean(DomainService.class);

        domainService.addDomain(domain);
        Domain updatedDomain = domainService.getByDomainName(domain.getDomainName());
        for(OrgUnit orgUnit : domain.getOrgUnits()){
            orgUnit.setGroupId(1L);
            orgUnit.setDomainId(domain.getId());
            fullOrgUnitAdd(orgUnit);
        }
    }

    public static void fullOrgUnitAdd(OrgUnit orgUnit){
        OrgUnitService orgUnitService = ctx.getBean(OrgUnitService.class);
        orgUnitService.addOrgUnit(orgUnit);
        OrgUnit updatedOrgUnit = orgUnitService.getByDomainName(orgUnit.getOrgUnitName());
        if (orgUnit.getUserInfos().size()!=0)
        {
            for(UserInfo userInfo : orgUnit.getUserInfos()) {
                userInfo.setOrgUnitId(updatedOrgUnit.getId());
                userInfoAdd(userInfo);
            }
        }
    }

    public static void userInfoAdd(UserInfo userInfo){
        UserInfoService userInfoService = ctx.getBean(UserInfoService.class);
        userInfoService.addUserInfo(userInfo);
    }

    public static void fullUpdate(HashMap<String, DomainHashMap> domainHashMapHashMap, Domain domain){
        DomainService domainService = ctx.getBean(DomainService.class);
        domain.setId(domainHashMapHashMap.get(domain.getDomainName()).getDomain().getId());
        domainService.updateDomain(domain);
        domainHashMapHashMap.remove(domain.getDomainName());
        for(OrgUnit orgUnit : domain.getOrgUnits()) {
            fullUpdateOrgUnits(orgUnit, domainHashMapHashMap.get(domain.getDomainName()));
        }
    }

    public static void fullUpdateOrgUnits(OrgUnit orgUnit, DomainHashMap domainHashMap){
        if (domainHashMap.containsKey(orgUnit.getOrgUnitName())){
            //fix it later
        }
        else{

        }
        //fullOrgUnitDelete(domainHashMap.get;
    }

    public static void update(List<Domain> domains, HashMap<String, DomainHashMap> domainHashMapHashMap){
        for(Domain domain :domains){
            if (domainHashMapHashMap.containsKey(domain.getDomainName())){
                fullUpdate(domainHashMapHashMap, domain);
            }
            else
            {
                fullDomainAdd(domain);
            }
        }
        Collection<DomainHashMap> domainCollection = domainHashMapHashMap.values();
        for(DomainHashMap domainHashMap : domainCollection){
            fullDomainDelete(domainHashMap);
        }
    }

    public static void main( String[] args )
    {

        ProgrammConfig programmConfig = new ProgrammConfig();
        programmConfig.setServerHost("127.0.0.1");
        programmConfig.setServerPort("8089");
        Domain newdomain = new Domain();
        newdomain.setDomainName("domain_name1");
        newdomain.setDomainType(1L);
        newdomain.setOrgUnits(new ArrayList<>());
        OrgUnit newOrgUnit = new OrgUnit();
        newOrgUnit.setOrgUnitName("newOrgUnitName");
        newOrgUnit.setTotalDevices(10L);
        newOrgUnit.setDescription("desc");

        UserInfo userInfo1 = new UserInfo();
        userInfo1.setFirstName("fName1");
        userInfo1.setLastName("lName1");
        userInfo1.setEmail("email1@gmail.com");
        userInfo1.setPhones("88005553535");
        userInfo1.setPosition("position");

        UserInfo userInfo2 = new UserInfo();
        userInfo2.setFirstName("fName2");
        userInfo2.setLastName("lName2");
        userInfo2.setEmail("email2@gmail.com");
        userInfo2.setPhones("880053535");
        userInfo2.setPosition("position");

        List<UserInfo> userInfos = new ArrayList<>();
        userInfos.add(userInfo1);
        userInfos.add(userInfo2);
        newOrgUnit.setUserInfos(userInfos);

        List<OrgUnit> orgUnits = new ArrayList<>();
        orgUnits.add(newOrgUnit);
        newdomain.setOrgUnits(orgUnits);

        HashMap<String, DomainHashMap> domainHashMap = new HashMap<>();
        try {
            List<Domain> domains = StructureCreating.getListOfDomain(programmConfig);
            for(Domain domain : domains){
                domainHashMap.put(domain.getDomainName(), new DomainHashMap(domain));
            }

        } catch(Exception ex){
            ex.printStackTrace();
        }
        List<Domain> newDomains = new ArrayList<>();
        newDomains.add(newdomain);

        Domain newdomain1 = new Domain();
        newdomain1.setDomainName("domain_name2");
        newdomain1.setDomainType(0L);
        newdomain1.setOrgUnits(new ArrayList<>());
        OrgUnit newOrgUnit1 = new OrgUnit();
        newOrgUnit1.setOrgUnitName("newOrgUnitName2");
        newOrgUnit1.setTotalDevices(10L);
        newOrgUnit1.setDescription("desc");

        UserInfo userInfo3 = new UserInfo();
        userInfo3.setFirstName("fName3");
        userInfo3.setLastName("lName1");
        userInfo3.setEmail("email3@gmail.com");
        userInfo3.setPhones("880053553535");
        userInfo3.setPosition("position");

        UserInfo userInfo4 = new UserInfo();
        userInfo4.setFirstName("fName4");
        userInfo4.setLastName("lName2");
        userInfo4.setEmail("email4@gmail.com");
        userInfo4.setPhones("8800543535");
        userInfo4.setPosition("position");

        List<UserInfo> userInfos1 = new ArrayList<>();
        userInfos1.add(userInfo3);
        userInfos1.add(userInfo4);
        newOrgUnit1.setUserInfos(userInfos1);

        List<OrgUnit> orgUnits1 = new ArrayList<>();
        orgUnits1.add(newOrgUnit1);
        newdomain1.setOrgUnits(orgUnits1);

        newDomains.add(newdomain1);

        update(newDomains, domainHashMap);

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
