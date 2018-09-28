package syncronization.importFrom.authorization;

import org.springframework.ldap.core.LdapTemplate;
import syncronization.importFrom.interfaces.ILdapConnection;
import syncronization.importFrom.interfaces.IStructure;
import syncronization.model.Admin;
import syncronization.model.Domain;

import java.util.List;

public class LdapConnection implements ILdapConnection {
    private LdapTemplate rootTemplate;
    private Admin admin;
    private IStructure structure;

    public LdapConnection(LdapTemplate rootTemplate, Admin admin){
        System.out.println("create ldap connection");
        this.rootTemplate = rootTemplate;
        this.admin = admin;
        structure = new Structure(rootTemplate, admin);
        System.out.println("result og auth is " + ((Structure) structure).auth(rootTemplate, admin));
    }

    public List<Domain> getFullStructure(){
        List<Domain> rootDomains = structure.getListOfRootDomain();
        for(Domain d:rootDomains)
            System.out.println(d.toString());
        for(Domain treeRootDomain:rootDomains)
            structure.importThisTree(treeRootDomain, admin);
        return rootDomains;
    }

}
