package syncronization.importFrom.mapper;

import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DistinguishedName;
import syncronization.model.Domain;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

public class DomainAttributesMapper implements AttributesMapper<Domain> {
    private String parseDNtoCN(String input){
        DistinguishedName dn = new DistinguishedName(input);
        String answer = "";
        answer += dn.get(dn.size()-1).split("=")[1];
        for(int i = dn.size()-2; i >= 0; i--)
            answer+= "." + dn.get(i).split("=")[1];
        return answer;
    }

    private String parseCNtoDN(String input){
        DistinguishedName name = new DistinguishedName();
        String[] array = input.split("\\.");
        for(int i = array.length-1; i >= 0; i--)
            name.add("DC", array[i]);
        return name.toString();
    }

    public Domain mapFromAttributes(Attributes attrs) throws NamingException {
        Domain domain = new Domain();
        try {
            domain.setCN((String) attrs.get("cn").get());
            domain.setDistName(parseCNtoDN(domain.getCN()));
        }catch (Exception ex)
        {
            if (domain.getCN()==null)
            {
                domain.setDistName((String) attrs.get("distinguishedName").get());
                domain.setCN(parseDNtoCN(domain.getDistName()));
            }
        }
        return domain;
    }
}
