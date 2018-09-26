package syncronization.mapServices.impl;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import syncronization.ApplicationConfig;
import syncronization.last_test.DomainHashMap;
import syncronization.last_test.OrgUnitHashMap;
import syncronization.mapServices.DomainMapService;
import syncronization.mapServices.OrgUnitMapService;
import syncronization.model.Domain;
import syncronization.model.DomainService;
import syncronization.model.OrgUnit;

import java.util.HashMap;

public class DomainMapServiceImpl implements DomainMapService {
    public static AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);

    public static void fullUpdateDomain(DomainHashMap domainHashMap, Domain updatedDomain){
        DomainService domainService = ctx.getBean(DomainService.class);
        updatedDomain.setId(domainHashMap.getDomain().getId());
        domainService.updateDomain(updatedDomain);

        for(OrgUnit orgUnit : updatedDomain.getOrgUnits()){
            OrgUnitMapServiceImpl.fullUpdateOrgUnit(orgUnit, domainHashMap);
        }
    }

    public static void fullDeleteDomain(DomainHashMap domainHashMap){
        DomainService domainService = ctx.getBean(DomainService.class);
        for(OrgUnit orgUnit : domainHashMap.getDomain().getOrgUnits()){
            OrgUnitMapServiceImpl.fullDeleteOrgUnit(domainHashMap.get(domainHashMap.getDomain().getDomainName()));
            domainHashMap.remove(orgUnit.getOrgUnitName());
        }
        domainService.deleteDomain(domainHashMap.getDomain());
    }

    public static void fullAddDomain(Domain newDomain){
        DomainService domainService = ctx.getBean(DomainService.class);
        newDomain.setId(10000L);//fix it
        domainService.addDomain(newDomain);
        Domain updatedDomain = domainService.getByDomainName(newDomain.getDomainName());
        for(OrgUnit orgUnit : newDomain.getOrgUnits()){
            orgUnit.setGroupId(1L);
            orgUnit.setDomainId(updatedDomain.getId());
            OrgUnitMapServiceImpl.fullAddOrgUnit(orgUnit);
        }
    }

}
