package syncronization;

import syncronization.model.Domain;
import syncronization.model.OrgUnit;
import syncronization.model.UserInfo;
import syncronization.test.ImportHashTable;
import syncronization.update.StructureCreating;
import syncronization.update.UpdateUserInfo;

import java.util.HashMap;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        HashMap<String, UserInfo> currOrgUnit = new HashMap<>();
        ProgrammConfig programmConfig = new ProgrammConfig();
        programmConfig.setServerHost("127.0.0.1");
        programmConfig.setServerPort("8089");
        try {
            List<OrgUnit> domains = StructureCreating.getListOfOrgUnits(programmConfig);
            for(OrgUnit domain : domains) {
                System.out.println(domain.toString());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
