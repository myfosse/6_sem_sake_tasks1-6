package dao.xml;

import model.Department;
import model.Departments;
import model.Student;
import model.Students;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

/**
 * @author Andrey "myfosse" Egorov
 * @version 1.0
 */

public class XMLHandler {

    private JAXBContext jaxbContextStudents;
    private JAXBContext jaxbContextDepartments;
    private Marshaller marshallerStudents;
    private Marshaller marshallerDepartments;
    private Unmarshaller unmarshallerStudents;
    private Unmarshaller unmarshallerDepartments;
    private Departments departments;
    private Students students;
    private File studentsFile;
    private File departmentsFile;

    public XMLHandler() throws JAXBException {
        jaxbContextStudents = JAXBContext.newInstance(Students.class);
        jaxbContextDepartments = JAXBContext.newInstance(Departments.class);
        marshallerStudents = jaxbContextStudents.createMarshaller();
        marshallerDepartments = jaxbContextDepartments.createMarshaller();
        marshallerStudents.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshallerDepartments.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        unmarshallerStudents = jaxbContextStudents.createUnmarshaller();
        unmarshallerDepartments = jaxbContextDepartments.createUnmarshaller();
        studentsFile = new File("src/main/resources/xml/students.xml");
        departmentsFile = new File("src/main/resources/xml/departments.xml");
    }

    public void saveToStudents() throws JAXBException {
        marshallerStudents.marshal(students, studentsFile);
    }

    public void saveToDepartments() throws JAXBException {
        marshallerDepartments.marshal(departments, departmentsFile);
    }

    public void addStudent(Student student) throws JAXBException {
        getStudents();
        students.getStudents().add(student);
        saveToStudents();
    }

    public void updateStudent(Student student) throws JAXBException {
        getStudents();
        for (int i = 0; i < students.getStudents().size(); i++) {
            if (student.getId() == students.getStudents().get(i).getId()) {
                students.getStudents().remove(i);
                students.getStudents().add(student);
                saveToStudents();
                return;
            }
        }
    }

    public void deleteStudent(Student student) throws JAXBException {
        getStudents();
        for (int i = 0; i < students.getStudents().size(); i++) {
            if (student.getId() == students.getStudents().get(i).getId()) {
                students.getStudents().remove(i);
                saveToStudents();
                return;
            }
        }
    }

    public void deleteBadStudents(List<Student> studentsToDelete) throws JAXBException {
        getStudents();
        for (Student student : studentsToDelete) {
            deleteStudent(student);
        }
    }

    public List<Student> getStudents() throws JAXBException {
        students = (Students) unmarshallerStudents.unmarshal(studentsFile);
        return students.getStudents();
    }

    public void addDepartment(Department department) throws JAXBException {
        getDepartments();
        departments.getDepartments().add(department);
        saveToDepartments();
    }

    public void updateDepartment(Department department) throws JAXBException {
        getDepartments();
        for (int i = 0; i < departments.getDepartments().size(); i++) {
            if (department.getDepartment_id() == departments.getDepartments().get(i).getDepartment_id()) {
                departments.getDepartments().remove(i);
                departments.getDepartments().add(department);
                saveToDepartments();
                return;
            }
        }
    }

    public void deleteDepartment(Department department) throws JAXBException {
        getDepartments();
        for (int i = 0; i < departments.getDepartments().size(); i++) {
            if (department.getDepartment_id() == departments.getDepartments().get(i).getDepartment_id()) {
                departments.getDepartments().remove(i);
                saveToDepartments();
                return;
            }
        }
    }

    public List<Department> getDepartments() throws JAXBException {
        departments = (Departments) unmarshallerDepartments.unmarshal(departmentsFile);
        return departments.getDepartments();
    }
}