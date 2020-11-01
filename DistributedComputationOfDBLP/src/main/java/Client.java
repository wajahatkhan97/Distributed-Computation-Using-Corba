import dataprocessing.dblp_Data;
import dataprocessing.dblp_DataHelper;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//okay all these string should come from typesafe config libraries
public class Client {

    public static void main(String args[]) {
        String datacentersConfig1 = ApplicationConfig.DBLP.getString(Applicationconst.INITIAL_REFERENCE); //typesafe config
        String datacentersConfig2 = ApplicationConfig.DBLP.getString(Applicationconst.RESOLVE_STR); //typesafe config
        final Logger LOGGER = LoggerFactory.getLogger(Server.class);

        try {
            // create and initialize the ORB
            ORB orb = ORB.init(args, null); //initialize object_request_broker
            org.omg.CORBA.Object objRef = orb.resolve_initial_references(datacentersConfig1); //creating an object
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            dblp_Data href = dblp_DataHelper.narrow(ncRef.resolve_str(datacentersConfig2));
            LOGGER.info("Client connected and requested to execute first task: ");
              href.sayhello();
            LOGGER.info("GENERATED NEW  CSV FILE: ------");


        } catch (InvalidName invalidName) {
            invalidName.printStackTrace();
        } catch (CannotProceed cannotProceed) {
            cannotProceed.printStackTrace();
        } catch (org.omg.CosNaming.NamingContextPackage.InvalidName invalidName) {
            invalidName.printStackTrace();
        } catch (NotFound notFound) {
            notFound.printStackTrace();
        }

    }

}