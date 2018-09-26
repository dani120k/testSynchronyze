package syncronization;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import syncronization.hashMaps.DomainHashMap;
import syncronization.hashMaps.OrgUnitHashMap;
import syncronization.mapServices.impl.DomainServiceMapImpl;
import syncronization.model.*;
import syncronization.update.StructureCreating;

import java.util.*;

/**
 * Hello world!
 *
 */
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

    public static void main( String[] args )
    {
        ProgrammConfig programmConfig = new ProgrammConfig();
        programmConfig.setServerHost("127.0.0.1");
        programmConfig.setServerPort("8089");

        List<Domain> newDomains = new ArrayList<>();
        newDomains.add(createFirstTestDomain());
        newDomains.add(createSecondTestDomain());

        HashMap<String, DomainHashMap> domainHashMap = new HashMap<>();
        try {
            List<Domain> domains = StructureCreating.getListOfDomain(programmConfig);
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
