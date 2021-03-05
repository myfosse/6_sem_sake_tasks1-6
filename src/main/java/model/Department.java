package model;

import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * <p><b>Class department</b></p>
 * @author Andrey "myfosse" Egorov
 * @version 1.0
 */

@XmlType(propOrder = {"department_id", "departmentName"}, name = "department")
public class Department implements Serializable {

    private int department_id;
    private String departmentName;

    public Department() {
    }

    public Department(int department_id, String departmentName) {
        this.department_id = department_id;
        this.departmentName = departmentName;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(department_id).append("\t")
                .append(departmentName).append("\n")
                .toString();
    }
}
