package syncronization;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import syncronization.hashMaps.DomainHashMap;
import syncronization.importFrom.authorization.impl.LdapConnection;
import syncronization.importFrom.authorization.ILdapConnection;
import syncronization.mapServices.impl.DomainServiceMapImpl;
import syncronization.model.*;

import java.util.*;

public class App
{
    public static Domain createFirstTestDomain(){
        Domain newdomain = new Domain();
        newdomain.setId(10000L);
        newdomain.setDomainName("domain_name30000");
        newdomain.setDomainType(1L);
        newdomain.setOrgUnits(new ArrayList<>());
        OrgUnit newOrgUnit = new OrgUnit();
        newOrgUnit.setOrgUnitName("newOrgUnitName20");
        newOrgUnit.setTotalDevices(10L);
        newOrgUnit.setDescription("desc");

        UserInfo userInfo1 = new UserInfo();
        userInfo1.setFirstName("newUserWithNewName");
        userInfo1.setLastName("lName1012");
        userInfo1.setEmail("email1001@gmail.com");
        userInfo1.setPhones("88005553535001");
        userInfo1.setPosition("position");

        UserInfo userInfo2 = new UserInfo();
        userInfo2.setFirstName("fName201");
        userInfo2.setLastName("lName2012");
        userInfo2.setEmail("email2000@gmail.com");
        userInfo2.setPhones("88005353500");
        userInfo2.setPosition("position");

        List<UserInfo> userInfos = new ArrayList<>();
        userInfos.add(userInfo1);
        userInfos.add(userInfo2);
        newOrgUnit.setUserInfos(userInfos);

        List<OrgUnit> orgUnits = new ArrayList<>();
        orgUnits.add(newOrgUnit);
        newdomain.setOrgUnits(orgUnits);
        return newdomain;
    }

    public static Domain createSecondTestDomain(){
        Domain newdomain1 = new Domain();
        newdomain1.setId(10000L);
        newdomain1.setDomainName("domain_name20000");
        newdomain1.setDomainType(0L);
        newdomain1.setOrgUnits(new ArrayList<>());
        OrgUnit newOrgUnit1 = new OrgUnit();
        newOrgUnit1.setOrgUnitName("newOrgUnitName321");
        newOrgUnit1.setTotalDevices(10L);
        newOrgUnit1.setDescription("desc");

        UserInfo userInfo3 = new UserInfo();
        userInfo3.setFirstName("fName3010");
        userInfo3.setLastName("lName1012");
        userInfo3.setEmail("email300@gmail.com");
        userInfo3.setPhones("880053553535000000");
        userInfo3.setPosition("position");

        UserInfo userInfo4 = new UserInfo();
        userInfo4.setFirstName("fName40");
        userInfo4.setLastName("lName2012");
        userInfo4.setEmail("email4000@gmail.com");
        userInfo4.setPhones("880054350035");
        userInfo4.setPosition("position");

        List<UserInfo> userInfos1 = new ArrayList<>();
        userInfos1.add(userInfo3);
        userInfos1.add(userInfo4);
        newOrgUnit1.setUserInfos(userInfos1);

        List<OrgUnit> orgUnits1 = new ArrayList<>();
        orgUnits1.add(newOrgUnit1);
        newdomain1.setOrgUnits(orgUnits1);
        return newdomain1;
    }

    public static List<Domain> importFullStructure(){
        String userPrincipalName = "ROOTDOMAIN\\Administrator";
        String password = "123Onebos50321";
        String url = "rootDomain.com";
        String base = "dc=rootDomain,dc=com";
        String dn = "ROOTDOMAIN\\Administrator";
        LdapContextSource ldapContextSource = new LdapContextSource();
        ldapContextSource.setUrl("ldap://" + url);
        ldapContextSource.setUserDn(dn);
        ldapContextSource.setBase(base);
        ldapContextSource.setPassword(password);
        //ldapContextSource.setReferral("follow");
        ldapContextSource.afterPropertiesSet();
        LdapTemplate template = new LdapTemplate(ldapContextSource);
        template.setIgnorePartialResultException(true);
        Admin admin = new Admin(userPrincipalName, password);
        ILdapConnection connection = new LdapConnection(template, admin);
        //try{Thread.sleep(10000);}catch (Exception ex){}
        System.out.println("getFullStruct");
        List<Domain> domains = connection.getFullStructure();
        for(Domain domain : domains){
            System.out.println(domain.toString());
        }
        System.out.println("size is " + domains.size());
        List<Domain> resultDomains = new ArrayList<>();
        for(Domain domain : domains){
            resultDomains.add(domain);
            if (domain.getReferences()!=null)
                for(Domain child : domain.getReferences()){
                    System.out.println("plus child");
                    resultDomains.add(child);
                }
        }

/*
        List<Domain> resultDomains = new ArrayList<>();
        for(Domain domain : domains){
            resultDomains.addAll(getAllReferences(domain));
        }
        for(Domain domain:resultDomains) {
            //
            domain.setReferences(new ArrayList<>());
            System.out.println(domain.toString());
            System.out.println("size of childs is " + domain.getR);
        }*/




        //try{Thread.sleep(100000);}catch (Exception ex){}
        return resultDomains;
    }

    public static void main( String[] args )
    {

        ProgrammConfig programmConfig = new ProgrammConfig();
        programmConfig.setServerHost("127.0.0.1");
        programmConfig.setServerPort("8089");

        /*Domain domain_test = new Domain();
        domain_test.setDomainName("domain_name");
        domain_test.setDomainType(0L);

        Domain domain_test_1 = new Domain();
        domain_test_1.setDomainType(0L);
        domain_test_1.setDomainName("domain_name");
        System.out.println(domain_test.equals(domain_test_1));
        try{Thread.sleep(10000);}catch (Exception ex){}
*/
        /*List<Domain> newDomains = new ArrayList<>();
        newDomains.add(createFirstTestDomain());
        newDomains.add(createSecondTestDomain());*/
        System.out.println("start");
        List<Domain> newDomains = importFullStructure();
        DomainService domainService = new AnnotationConfigApplicationContext(ApplicationConfig.class).getBean(DomainService.class);

        HashMap<String, DomainHashMap> domainHashMap = new HashMap<>();
        try {
            List<Domain> domains = domainService.getAllDomains();
            for(Domain domain : domains){
                domainHashMap.put(domain.getDomainName(), new DomainHashMap(domain));
            }
        } catch(Exception ex){
            ex.printStackTrace();
        }

        List<Domain> domainsToAdd = new ArrayList<>();
        for(Domain domain : newDomains){
            if (domainHashMap.containsKey(domain.getDomainName())){
                new DomainServiceMapImpl(domainHashMap.get(domain.getDomainName())).update(domain);
                domainHashMap.remove(domain.getDomainName());
            } else {
                domainsToAdd.add(domain);
            }
        }

        for(DomainHashMap domainHashMapa : domainHashMap.values()){
            new DomainServiceMapImpl().delete(domainHashMapa.getDomain());
        }

        for(Domain domain : domainsToAdd){
            new DomainServiceMapImpl().add(domain);
        }
    }
}
