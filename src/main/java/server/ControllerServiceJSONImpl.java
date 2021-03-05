package server;

import dao.json.JSONHandler;
import model.Department;
import model.Student;
import service.DepartmentDAOControlService;
import service.StudentDAOControlService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrey "myfosse" Egorov
 * @version 1.0
 */

public class ControllerServiceJSONImpl extends UnicastRemoteObject implements StudentDAOControlService, DepartmentDAOControlService {

    private JSONHandler jsonHandler;

    public ControllerServiceJSONImpl() throws RemoteException {
        super();
        jsonHandler = new JSONHandler();
    }

    @Override
    public void addStudent(Student student) throws RemoteException {
        jsonHandler.addStudent(student);
    }

    @Override
    public void updateStudent(Student student) throws RemoteException {
        jsonHandler.updateStudent(student);
    }

    @Override
    public void deleteStudent(Student student) throws RemoteException {
        jsonHandler.deleteStudent(student);
    }

    @Override
    public void deleteBadStudents(List<Student> studentsToDelete) throws RemoteException {
        jsonHandler.deleteBadStudents(studentsToDelete);
    }

    @Override
    public List<Student> updateStudents() throws RemoteException {
        return jsonHandler.getStudents();
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
    public void addDepartment(Department department) throws RemoteException {
        jsonHandler.addDepartment(department);
    }

    @Override
    public void updateDepartment(Department department) throws RemoteException {
        jsonHandler.updateDepartment(department);
    }

    @Override
    public void deleteDepartment(Department department) throws RemoteException {
        jsonHandler.deleteDepartment(department);
    }

    @Override
    public List<Department> updateDepartments() throws RemoteException {
        return jsonHandler.getDepartments();
    }
}
