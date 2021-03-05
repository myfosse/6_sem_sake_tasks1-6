package dao.json;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.json.GsonJsonProvider;
import com.jayway.jsonpath.spi.mapper.GsonMappingProvider;
import model.Department;
import model.Student;

import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * @author Andrey "myfosse" Egorov
 * @version 1.0
 */

public class JSONHandler {

    private final String jsonStudentsFileName = "students";
    private final String jsonDepartmentsFileName = "departments";
    private DocumentContext documentContextStudents;
    private DocumentContext documentContextDepartments;

    public JSONHandler(){
        System.out.println("Connecting to " + getFilePath(jsonStudentsFileName));
        try (InputStream is = new FileInputStream(new File(getFilePath(jsonStudentsFileName)))) {
            documentContextStudents = JsonPath
                    .using(Configuration.builder().jsonProvider(new GsonJsonProvider()).mappingProvider(new GsonMappingProvider()).build())
                    .parse(is);
        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
        }
        System.out.println("Connecting to " + getFilePath(jsonDepartmentsFileName));
        try (InputStream is = new FileInputStream(new File(getFilePath(jsonDepartmentsFileName)))) {
            documentContextDepartments = JsonPath
                    .using(Configuration.builder().jsonProvider(new GsonJsonProvider()).mappingProvider(new GsonMappingProvider()).build())
                    .parse(is);
        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
        }

    }

    public void saveToStudents() {
        try (Writer writer = new FileWriter(new File(getFilePath(jsonStudentsFileName)))) {
            writer.write(documentContextStudents.jsonString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveToDepartments() {
        try (Writer writer = new FileWriter(new File(getFilePath(jsonDepartmentsFileName)))) {
            writer.write(documentContextDepartments.jsonString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFilePath(String jsonFileName) {
        return new File("src/main/resources/json/" + jsonFileName + ".json").getAbsolutePath();
    }

    public Student getStudent(int id) {
        System.out.println("get student with id = " + id);
        try {
            Student[] students = documentContextStudents.read("$.students.[?(@.id == " + id + ")]", Student[].class);
            if (students.length != 0) {
                return students[0];
            } else {
                return null;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public Department getDepartment(int id) {
        System.out.println("get department with id = " + id);
        try {
            Department[] departments = documentContextDepartments.read("$.departments.[?(@.department_id == " + id + ")]", Department[].class);
            if (departments.length != 0) {
                return departments[0];
            } else {
                return null;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addStudent(Student student) {
        System.out.println("add student " + student);
        try {
            Student fromDB = getStudent(student.getId());
            if (fromDB == null) {
                documentContextStudents.add(".students", student);
                saveToStudents();
            } else {
                throw new Exception("already exists student with id == " + student.getId());
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void updateStudent(Student student) {
        System.out.println("update student " + student);
        try {
            Student fromDB = getStudent(student.getId());
            if (fromDB != null) {
                documentContextStudents.delete("$.students.[?(@.id == " + fromDB.getId() + ")]");
                documentContextStudents.add(".students", student);
                saveToStudents();
            } else {
                throw new Exception("No student with id == " + student.getId());
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void deleteStudent(Student student) {
        System.out.println("delete student with id = " + student.getId());
        try {
            documentContextStudents.delete("$.students.[?(@.id == " + student.getId() + ")]");
            saveToStudents();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void deleteBadStudents(List<Student> studentsToDelete) {
        for (Student student : studentsToDelete) {
            documentContextStudents.delete("$.students.[?(@.id == " + student.getId() + ")]");
        }
        saveToStudents();
    }

    public List<Student> getStudents() {
        System.out.println("get all students");
        try {
            return Arrays.asList(documentContextStudents.read("$.students.*", Student[].class));
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addDepartment(Department department) {
        System.out.println("add department " + department);
        try {
            Department fromDB = getDepartment(department.getDepartment_id());
            if (fromDB == null) {
                documentContextDepartments.add(".departments", department);
                saveToDepartments();
            } else {
                throw new Exception("already exists department with id == " + department.getDepartment_id());
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void updateDepartment(Department department) {
        System.out.println("update department " + department);
        try {
            Department fromDB = getDepartment(department.getDepartment_id());
            if (fromDB != null) {
                documentContextDepartments.delete("$.departments.[?(@.department_id == " + fromDB.getDepartment_id() + ")]");
                documentContextDepartments.add(".departments", department);
                saveToDepartments();
            } else {
                throw new Exception("No department with id == " + department.getDepartment_id());
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void deleteDepartment(Department department) {
        System.out.println("delete department with id = " + department.getDepartment_id());
        try {
            documentContextDepartments.delete("$.departments.[?(@.department_id == " + department.getDepartment_id() + ")]");
            saveToDepartments();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public List<Department> getDepartments() {
        System.out.println("get all departments");
        try {
            return Arrays.asList(documentContextDepartments.read("$.departments.*", Department[].class));
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }
}