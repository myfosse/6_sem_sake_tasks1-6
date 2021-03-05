package model;

import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p><b>Class student</b></p>
 *
 * @author Andrey "myfosse" Egorov
 * @version 1.0
 */

@XmlType(propOrder = {"id", "name", "surname", "marks", "department_id", "departmentName"}, name = "student")
public class Student implements Serializable {

    private int id;
    private String name;
    private String surname;
    private List<Integer> marks;
    private int department_id;
    private String departmentName;

    /**
     * Constructor for create a new object student
     *
     * @see Student#Student(int, String, String, List, int)
     * @see Student#Student(int, String, String, List, String)
     * @see Student#Student(int, String, String, List, int, String)
     */
    public Student() {
        marks = new ArrayList<>();
    }

    /**
     * Constructor for create a new object student
     *
     * @param id             - id student
     * @param name           - student name
     * @param surname        - student surname
     * @param marks          - student marks
     * @param departmentName - department name
     * @see Student#Student()
     * @see Student#Student(int, String, String, List, int)
     * @see Student#Student(int, String, String, List, int, String)
     */
    public Student(int id, String name, String surname, List<Integer> marks, String departmentName) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.marks = marks;
        this.departmentName = departmentName;
    }

    /**
     * Constructor for create a new object student
     *
     * @param id            - id student
     * @param name          - student name
     * @param surname       - student surname
     * @param marks         - student marks
     * @param department_id - department id
     * @see Student#Student()
     * @see Student#Student(int, String, String, List, String)
     * @see Student#Student(int, String, String, List, int, String)
     */
    public Student(int id, String name, String surname, List<Integer> marks, int department_id) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.marks = marks;
        this.department_id = department_id;
    }

    /**
     * Constructor for create a new object student
     *
     * @param id             - id student
     * @param name           - student name
     * @param surname        - student surname
     * @param marks          - student marks
     * @param department_id  - department id
     * @param departmentName - department name
     * @see Student#Student()
     * @see Student#Student(int, String, String, List, String)
     * @see Student#Student(int, String, String, List, int)
     */
    public Student(int id, String name, String surname, List<Integer> marks, int department_id, String departmentName) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.marks = marks;
        this.department_id = department_id;
        this.departmentName = departmentName;
    }

    /**
     * Convert student into {@link java.lang.String} object
     *
     * @return {@link java.lang.String} object
     */
    @Override
    public String toString() {
        return new StringBuilder()
                .append(id).append("\t")
                .append(name).append("\t")
                .append(surname).append("\t")
                .append(departmentName).append("\n")
                .toString();
    }

    /**
     * Get student id {@link java.lang.Integer} object
     *
     * @return {@link java.lang.Integer} object
     */
    public int getId() {
        return id;
    }

    /**
     * Set student id {@link java.lang.Integer} object
     *
     * @param id - object you want to insert into student
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get student name {@link java.lang.String} object
     *
     * @return {@link java.lang.String} object
     */
    public String getName() {
        return name;
    }

    /**
     * Set student name {@link java.lang.String} object
     *
     * @param name - object you want to insert into student
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get student surname {@link java.lang.String} object
     *
     * @return {@link java.lang.String} object
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Set student surname {@link java.lang.String} object
     *
     * @param surname - object you want to insert into student
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Get student marks {@link List<Integer>} object
     *
     * @return <pre>{@code  List<Integer>}</pre> object
     */
    public List<Integer> getMarks() {
        return marks;
    }

    /**
     * Set student surname {@link List<Integer>} object
     *
     * @param marks - object you want to insert into student
     */
    public void setMarks(List<Integer> marks) {
        this.marks = marks;
    }

    /**
     * Get student mark by ID {@link java.lang.Integer} object
     *
     * @param index mark index
     * @return {@link java.lang.Integer} object
     */
    public int getMark(int index) {
        return this.marks.get(index);
    }

    /**
     * Set student mark by ID {@link java.lang.Integer} object
     *
     * @param index mark index
     */
    public void setMark(int index, int mark) {
        this.marks.set(index, mark);
    }

    /**
     * Add student mark {@link java.lang.Integer} object
     *
     * @param mark mark that you want to add
     */
    public void addMark(int mark) {
        this.marks.add(mark);
    }

    /**
     * Get student department ID {@link java.lang.Integer} object
     *
     * @return {@link  java.lang.Integer} object
     */
    public int getDepartment_id() {
        return department_id;
    }

    /**
     * Set student department ID {@link java.lang.Integer} object
     *
     * @param department_id - object you want to insert into student
     */
    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    /**
     * Get student department name {@link java.lang.String} object
     *
     * @return {@link  java.lang.String} object
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * Set student department ID {@link java.lang.String} object
     *
     * @param departmentName - object you want to insert into student
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}