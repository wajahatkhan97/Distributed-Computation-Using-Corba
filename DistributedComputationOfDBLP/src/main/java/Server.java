import dataprocessing.dblp_Data;
import dataprocessing.dblp_DataHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {
    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args)
    {
        String datacentersConfig = ApplicationConfig.DBLP.getString(Applicationconst.POA_SERVER); //typesafe config
        String datacentersConfig1 = ApplicationConfig.DBLP.getString(Applicationconst.INITIAL_REFERENCE); //typesafe config
        String datacentersConfig2 = ApplicationConfig.DBLP.getString(Applicationconst.RESOLVE_STR); //typesafe config

        try
        {
            //implement the Server here
            ORB orb = ORB.init(args, null);

            // get reference to rootpoa & activate the POAManager
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references(datacentersConfig));
            rootpoa.the_POAManager().activate();

            // create servant
            Logic server = new Logic();
            // get object reference from the servant
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(server);
            dblp_Data href = dblp_DataHelper.narrow(ref);
            org.omg.CORBA.Object objRef =  orb.resolve_initial_references(datacentersConfig1);
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            NameComponent path[] = ncRef.to_name( datacentersConfig2 );
            ncRef.rebind(path, href);
            LOGGER.info("Server ready and waiting ...");
            // wait for invocations from clients
            orb.run();
        }
        catch(Exception e)
        {

            System.out.println(e.getStackTrace());
        }
    }

}
