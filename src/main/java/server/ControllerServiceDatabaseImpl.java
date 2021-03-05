package server;

import service.DepartmentDAOControlService;
import service.StudentDAOControlService;
import dao.sql.DatabaseHandler;
import model.Department;
import model.Student;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrey "myfosse"Egorov
 * @version 1.0
 * @see service.DepartmentDAOControlService
 * @see service.StudentDAOControlService
 */

public class ControllerServiceDatabaseImpl extends UnicastRemoteObject implements StudentDAOControlService, DepartmentDAOControlService {

    private DatabaseHandler dataBaseHandler;

    public ControllerServiceDatabaseImpl() throws RemoteException {
        super();
        dataBaseHandler = new DatabaseHandler();
    }

    @Override
    public void addStudent(Student student) throws RemoteException {
        dataBaseHandler.addStudent(student);
    }

    @Override
    public void addDepartment(Department department) throws RemoteException {
        dataBaseHandler.addDepartment(department);
    }

    @Override
    public void updateStudent(Student student) throws RemoteException {
        dataBaseHandler.updateStudent(student);
    }

    @Override
    public void updateDepartment(Department department) throws RemoteException {
        dataBaseHandler.updateDepartment(department);
    }

    @Override
    public void deleteStudent(Student student) throws RemoteException {
        dataBaseHandler.deleteStudent(student);
    }

    @Override
    public void deleteBadStudents(List<Student> studentsToDelete) throws RemoteException {
        dataBaseHandler.deleteBadStudents(studentsToDelete);
    }

    @Override
    public void deleteDepartment(Department department) throws RemoteException {
        dataBaseHandler.deleteDepartment(department);
    }

    @Override
    public List<Student> updateStudents() throws RemoteException {
        return dataBaseHandler.getStudents();
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
    public List<Department> updateDepartments() throws RemoteException {
        return dataBaseHandler.getDepartments();
    }

}