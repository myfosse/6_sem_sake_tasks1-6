package service;

import model.Department;

import javax.xml.bind.JAXBException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * @author Andrey "myfosse" Egorov
 * @version 1.0
 * @see server.ControllerServiceDatabaseImpl
 * @see server.ControllerServiceJSONImpl
 * @see server.ControllerServiceXMLImpl
 */

public interface DepartmentDAOControlService extends Remote {
    void addDepartment(Department department) throws RemoteException, JAXBException;
    void updateDepartment(Department department) throws RemoteException, JAXBException;
    void deleteDepartment(Department department) throws RemoteException, JAXBException;
    List<Department> updateDepartments() throws RemoteException, JAXBException;
}