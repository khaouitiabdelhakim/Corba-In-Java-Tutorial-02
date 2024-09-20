/*
 * ABDELHAKIM KHAOUITI
 * 20 SEP 2024
 */

import org.omg.CORBA.ORB;
import org.omg.CORBA.UserException;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManager;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;


public class server {
    public static void main(String[] args) {
        Properties props = System.getProperties();
        int status = 0;
        ORB orb = null;

        try {
            orb = ORB.init(args, props);
            status = run(orb);
        } catch (Exception ex) {
            ex.printStackTrace();
            status = 1;
        }

        if (orb != null) {
            try {
                orb.destroy();
            } catch (Exception ex) {
                ex.printStackTrace();
                status = 1;
            }
        }
        System.exit(status);
    }

    static int run(ORB orb) throws UserException, IOException {
        POA rootPOA;
        POAManager manager;

        rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
        manager = rootPOA.the_POAManager();

        impl_compte_heritage compte_impl = new impl_compte_heritage(5000);
        Compte compte = compte_impl._this(orb);

        String ref = orb.object_to_string(compte);
        String refFile = "reference";
        try (FileOutputStream file = new FileOutputStream(refFile);
             PrintWriter out = new PrintWriter(file)) {
             out.println(ref);
            System.out.println("server en attente des clients\nRef: "+ref);
        } catch (Exception ignored) {}

        try {
            if (manager != null) {
                manager.activate();
            }
            orb.run();
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }

        return 0;
    }
}