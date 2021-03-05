package server;

import dao.xml.XMLHandler;
import model.Department;
import model.Student;
import service.DepartmentDAOControlService;
import service.StudentDAOControlService;

import javax.xml.bind.JAXBException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrey "myfosse" Egorov
 * @version 1.0
 */

public class ControllerServiceXMLImpl extends UnicastRemoteObject implements StudentDAOControlService, DepartmentDAOControlService {

    private XMLHandler xmlHandler;

    public ControllerServiceXMLImpl() throws RemoteException, JAXBException {
        super();
        xmlHandler = new XMLHandler();
    }

    @Override
    public void addStudent(Student student) throws RemoteException, JAXBException {
        xmlHandler.addStudent(student);
    }

    @Override
    public void updateStudent(Student student) throws RemoteException, JAXBException {
        xmlHandler.updateStudent(student);
    }

    @Override
    public void deleteStudent(Student student) throws RemoteException, JAXBException {
        xmlHandler.deleteStudent(student);
    }

    @Override
    public void deleteBadStudents(List<Student> studentsToDelete) throws RemoteException, JAXBException {
        xmlHandler.deleteBadStudents(studentsToDelete);
    }

    @Override
    public List<Student> updateStudents() throws RemoteException, JAXBException {
        return xmlHandler.getStudents();
    }

    @Override
    public List<Student> updateBadStudents(List<Student> students) throws RemoteException {
        List<Student> studentsToDelete = new ArrayList<>();
        int delete;
        for (Student student : students) {
            delete = 0;
            for (Integer mark : student.getMarks()) {
                if (mark < 4) delete++;
            }
            if (delete >= 3) studentsToDelete.add(student);
        }
        return studentsToDelete;
    }

    @Override
    public void addDepartment(Department department) throws RemoteException, JAXBException {
        xmlHandler.addDepartment(department);
    }

    @Override
    public void updateDepartment(Department department) throws RemoteException, JAXBException {
        xmlHandler.updateDepartment(department);
    }

    @Override
    public void deleteDepartment(Department department) throws RemoteException, JAXBException {
        xmlHandler.deleteDepartment(department);
    }

    @Override
    public List<Department> updateDepartments() throws RemoteException, JAXBException {
        return xmlHandler.getDepartments();
    }
}
