package model;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrey "myfosse" Egorov
 * @version 1.0
 */

@XmlType(propOrder = { }, name = "students")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Students {

    public List<Student> student = new ArrayList<>();

    public List<Student> getStudents() {
        return student;
    }

    public void setStudents(List<Student> students) {
        this.student = students;
    }
}