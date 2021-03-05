package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author Andrey "myfosse" Egorov
 * @version 1.0
 */

public class ServerStart {

    public static void main(String[] args) throws Exception {
        String localhost = "127.0.0.1";
        //String localhost = "192.168.100.57";
        String RMI_HOSTNAME = "java.rmi.server.hostname";
        try {
            System.setProperty(RMI_HOSTNAME, localhost);
            //MySQL
            ControllerServiceDatabaseImpl serviceDatabase = new ControllerServiceDatabaseImpl();
            //JSON
            //ControllerServiceJSONImpl controllerServiceJSON = new ControllerServiceJSONImpl();
            //XML
            //ControllerServiceXMLImpl controllerServiceXML = new ControllerServiceXMLImpl();
            String serviceName = "BDService";
            System.out.println("Initializing " + serviceName);

            Registry registry = LocateRegistry.createRegistry(1099);
            //MySQL
            registry.rebind(serviceName, serviceDatabase);
            //JSON
            //registry.rebind(serviceName, controllerServiceJSON);
            //XML
            //registry.rebind(serviceName, controllerServiceXML);

            System.out.println("ServerStart " + serviceName);
        } catch (RemoteException e) {
            System.err.println("RemoteException : " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Exception : " + e.getMessage());
            System.exit(2);
        }
    }

}
