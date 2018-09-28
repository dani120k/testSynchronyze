package syncronization.importFrom.mapper;

import org.springframework.ldap.core.AttributesMapper;
import syncronization.model.OrgUnit;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

public class OrgUnitAttributesMapper implements AttributesMapper<OrgUnit> {
    public OrgUnit mapFromAttributes(Attributes attrs) throws NamingException {
        OrgUnit orgUnit = new OrgUnit();
        try
        {
            orgUnit.setOrgUnitName((String)attrs.get("distinguishedName").get());
            orgUnit.setDistinguishedName((String)attrs.get("distinguishedName").get());
            orgUnit.setDescription((String)attrs.get("description").get());
        }
        catch(Exception ex) {
            return orgUnit;
        }
        return orgUnit;
    }
}
