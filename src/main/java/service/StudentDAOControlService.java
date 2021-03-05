package service;

import model.Student;

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

public interface StudentDAOControlService extends Remote {
    void addStudent(Student student) throws RemoteException, JAXBException;
    void updateStudent(Student student) throws RemoteException, JAXBException;
    void deleteStudent(Student student) throws RemoteException, JAXBException;
    void deleteBadStudents(List<Student> studentsToDelete) throws RemoteException, JAXBException;
    List<Student> updateStudents() throws RemoteException, JAXBException;
    List<Student> updateBadStudents(List<Student> students) throws RemoteException;
}