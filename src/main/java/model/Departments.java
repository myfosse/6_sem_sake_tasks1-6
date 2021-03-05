package model;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrey "myfosse" Egorov
 * @version 1.0
 */

@XmlType(propOrder = {}, name = "departments")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Departments {
    public List<Department> department = new ArrayList<>();

    public List<Department> getDepartments() {
        return department;
    }

    public void setDepartments(List<Department> departments) {
        this.department = departments;
    }
}
