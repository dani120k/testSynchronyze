package syncronization.mapServices.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import syncronization.ApplicationConfig;
import syncronization.hashMaps.DomainHashMap;
import syncronization.hashMaps.OrgUnitHashMap;
import syncronization.mapServices.ServiceMap;
import syncronization.model.Domain;
import syncronization.model.DomainService;
import syncronization.model.OrgUnit;
import syncronization.model.OrgUnitService;

import java.util.ArrayList;
import java.util.List;

public class DomainServiceMapImpl implements ServiceMap<Domain> {
    static DomainService domainService = new AnnotationConfigApplicationContext(ApplicationConfig.class).getBean(DomainService.class);
    private DomainHashMap domainHashMap;

    public DomainServiceMapImpl(DomainHashMap domainHashMap){
        this.domainHashMap = domainHashMap;
    }

    public DomainServiceMapImpl(){
    }

    @Override
    public void update(Domain newDomain){
        Domain oldDomain = domainHashMap.getDomain();
        List<OrgUnit> addOrgUnits = new ArrayList<>();
        System.out.println("new Domain is " + newDomain.getDomainName());
        if (newDomain.getOrgUnits()==null)
            System.out.println("orgUnits is null");
        for(OrgUnit orgUnit : newDomain.getOrgUnits()){
            if (domainHashMap.containsKey(orgUnit.getOrgUnitName())){
                System.out.println("Update " + orgUnit.toString());
                OrgUnitServiceMapImpl orgUnitServiceMap = new OrgUnitServiceMapImpl(domainHashMap.get(orgUnit.getOrgUnitName()));
                orgUnitServiceMap.update(orgUnit);
                domainHashMap.remove(orgUnit.getOrgUnitName());
            } else {
                orgUnit.setGroupId(1L);
                orgUnit.setDomainId(oldDomain.getId());
                addOrgUnits.add(orgUnit);
            }
        }

        for(OrgUnitHashMap orgUnitHashMap : domainHashMap.values()){
            OrgUnitServiceMapImpl orgUnitServiceMap = new OrgUnitServiceMapImpl(orgUnitHashMap);
            orgUnitServiceMap.delete(orgUnitHashMap.getOrgUnit());
        }
        domainHashMap.clear();

        for(OrgUnit orgUnit : addOrgUnits){
            orgUnit.setGroupId(1L);
            orgUnit.setDomainId(oldDomain.getId());
            orgUnit.setId(10000L);
            OrgUnitServiceMapImpl orgUnitServiceMap = new OrgUnitServiceMapImpl();
            orgUnitServiceMap.add(orgUnit);
        }

        newDomain.setId(oldDomain.getId());
        newDomain.setOrgUnits(new ArrayList<>());
        if (updateIsRequired(newDomain, oldDomain)) {
            loginfo("update domain", newDomain);
            domainService.updateDomain(newDomain);
        }
    }

    private void loginfo(String info, Object object){
        System.out.println(info);
        System.out.println(object.toString());
        try{
          //  Thread.sleep(1000);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public boolean updateIsRequired(Domain newDomain, Domain oldDomain){
        if (newDomain.equals(oldDomain)){
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void delete(Domain domain){
        for(OrgUnit orgUnit:domain.getOrgUnits()){
            OrgUnitServiceMapImpl orgUnitServiceMap = new OrgUnitServiceMapImpl();
            orgUnitServiceMap.delete(orgUnit);
        }
        domain.setOrgUnits(new ArrayList<>());
        loginfo("delete domain", domain);
        domainService.deleteDomain(domain);
    }

    @Override
    public void add(Domain domain){
        domain.setId(10000L);///fix it
        loginfo("add domain", domain);
        domainService.addDomain(domain);
        Domain updatedDomain = domainService.getByDomainName(domain.getDomainName());
        loginfo("getUpdated domain", updatedDomain);
        for(OrgUnit orgUnit:domain.getOrgUnits()){
            orgUnit.setGroupId(1L);
            orgUnit.setDomainId(updatedDomain.getId());
            orgUnit.setId(100000L);
            OrgUnitServiceMapImpl orgUnitServiceMap = new OrgUnitServiceMapImpl();
            orgUnitServiceMap.add(orgUnit);
        }

    }
}
