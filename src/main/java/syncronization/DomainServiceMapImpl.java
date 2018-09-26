package syncronization;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import syncronization.ApplicationConfig;
import syncronization.last_test.DomainHashMap;
import syncronization.last_test.OrgUnitHashMap;
import syncronization.mapServices.DomainMapService;
import syncronization.model.Domain;
import syncronization.model.DomainService;
import syncronization.model.OrgUnit;
import syncronization.model.OrgUnitService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DomainServiceMapImpl implements DomainMapService {
    HashMap<String, DomainHashMap> domainHashMapHashMap;
    public static AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);

    public DomainServiceMapImpl(HashMap<String, DomainHashMap> domainHashMapHashMap){
        this.domainHashMapHashMap = domainHashMapHashMap;
        this.update();
    }

    private void update(){

    }

    public static void fullUpdateDomain(DomainHashMap domainHashMap, Domain newDomain){
        Domain oldDomain = domainHashMap.getDomain();
        DomainService domainService = ctx.getBean(DomainService.class);
        List<OrgUnit> addOrgUnits = new ArrayList<>();
        for(OrgUnit orgUnit : newDomain.getOrgUnits()){
            if (domainHashMap.containsKey(orgUnit.getOrgUnitName())){
                System.out.println("Update " + orgUnit.toString());
                OrgUnitServiceMapImpl.fullUpdate(domainHashMap.get(orgUnit.getOrgUnitName()), orgUnit);
                domainHashMap.remove(orgUnit.getOrgUnitName());
            } else {
                orgUnit.setGroupId(1L);
                orgUnit.setDomainId(oldDomain.getId());
                addOrgUnits.add(orgUnit);
            }
        }

        for(OrgUnitHashMap orgUnit : domainHashMap.values()){
            OrgUnitServiceMapImpl.fullDelete(orgUnit.getOrgUnit());
        }

        for(OrgUnit orgUnit : addOrgUnits){
            orgUnit.setGroupId(1L);
            orgUnit.setDomainId(oldDomain.getId());
            orgUnit.setId(10000L);
            OrgUnitServiceMapImpl.fullAdd(orgUnit);
        }

        newDomain.setId(oldDomain.getId());
        newDomain.setOrgUnits(new ArrayList<>());
        domainService.updateDomain(newDomain);
    }

    public static void fullDeleteDomain(Domain domain){
        DomainService domainService = ctx.getBean(DomainService.class);
        for(OrgUnit orgUnit:domain.getOrgUnits()){
            OrgUnitServiceMapImpl.fullDelete(orgUnit);
        }
        domain.setOrgUnits(new ArrayList<>());
        domainService.deleteDomain(domain);
    }

    public static void fullAddDomain(Domain domain){
        DomainService domainService = ctx.getBean(DomainService.class);
        domain.setId(10000L);///fix it
        domainService.addDomain(domain);
        Domain updatedDomain = domainService.getByDomainName(domain.getDomainName());
        for(OrgUnit orgUnit:domain.getOrgUnits()){
            OrgUnitServiceMapImpl orgUnitServiceMap = new OrgUnitServiceMapImpl();
            orgUnit.setGroupId(1L);
            orgUnit.setDomainId(updatedDomain.getId());
            orgUnit.setId(100000L);
            orgUnitServiceMap.fullAdd(orgUnit);
        }

    }
}
