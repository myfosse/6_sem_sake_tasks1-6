package dao.sql;

import model.Department;
import model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p><b>Class for working with sql table</b></p>
 *
 * @author Andrey "myfosse" Egorov
 * @version 1.0
 * @see server.ControllerServiceDatabaseImpl
 */

public class DatabaseHandler extends Configs {

    /**
     * Return {@link java.sql.Connection} for sql database
     *
     * @return {@link java.sql.Connection} object
     */
    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://"
                + dbHost + ":"
                + dbPort + "/"
                + dbName + "?"
                + "autoReconnect=true&useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

        Class.forName("com.mysql.cj.jdbc.Driver");

        return DriverManager.getConnection(connectionString, dbUser, dbPass);
    }

    /**
     * Add new {@link Student} in file
     *
     * @param student - object you want to insert into file
     * @throws SQLException if student with ID already exists
     */
    public void addStudent(Student student) {
        String insert = "INSERT INTO " + Const.STUDENTS_TABLE + "("
                + Const.STUDENTS_ID + ","
                + Const.STUDENTS_NAME + ","
                + Const.STUDENTS_SURNAME + ","
                + Const.STUDENTS_MARK1 + ","
                + Const.STUDENTS_MARK2 + ","
                + Const.STUDENTS_MARK3 + ","
                + Const.STUDENTS_MARK4 + ","
                + Const.STUDENTS_MARK5 + ","
                + Const.STUDENTS_DEPARTMENT_ID + ")"
                + "VALUES(?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1, Integer.toString(student.getId()));
            preparedStatement.setString(2, student.getName());
            preparedStatement.setString(3, student.getSurname());
            preparedStatement.setString(4, Integer.toString(student.getMark(0)));
            preparedStatement.setString(5, Integer.toString(student.getMark(1)));
            preparedStatement.setString(6, Integer.toString(student.getMark(2)));
            preparedStatement.setString(7, Integer.toString(student.getMark(3)));
            preparedStatement.setString(8, Integer.toString(student.getMark(4)));
            preparedStatement.setString(9, Integer.toString(student.getDepartment_id()));

            preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add new {@link Department} in file
     *
     * @param department - object you want to insert into file
     * @return {@link Department} that had been added
     * @throws SQLException if department with ID already exists
     */
    public void addDepartment(Department department) {
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement("INSERT INTO "
                    + Const.DEPARTMENTS_TABLE + "("
                    + Const.DEPARTMENTS_ID + ","
                    + Const.DEPARTMENTS_DEPARTMENT + ")"
                    + "VALUES(?,?)");
            preparedStatement.setString(1, Integer.toString(department.getDepartment_id()));
            preparedStatement.setString(2, department.getDepartmentName());
            preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update {@link Student} by ID number
     *
     * @param student - student you want to update
     * @throws SQLException if student with ID already exists
     */
    public void updateStudent(Student student) {
        try {
            Statement statement = getDbConnection().createStatement();
            statement.executeUpdate("UPDATE "
                    + Const.STUDENTS_TABLE
                    + " SET "
                    + Const.STUDENTS_NAME
                    + " = " + "'" + student.getName() + "'" + ","
                    + Const.STUDENTS_SURNAME
                    + " = " + "'" + student.getSurname() + "'" + ","
                    + Const.STUDENTS_MARK1
                    + " = " + student.getMark(0) + ","
                    + Const.STUDENTS_MARK2
                    + " = " + student.getMark(1) + ","
                    + Const.STUDENTS_MARK3
                    + " = " + student.getMark(2) + ","
                    + Const.STUDENTS_MARK4
                    + " = " + student.getMark(3) + ","
                    + Const.STUDENTS_MARK5
                    + " = " + student.getMark(4) + ","
                    + Const.STUDENTS_DEPARTMENT_ID
                    + " = " + student.getDepartment_id()
                    + " WHERE "
                    + Const.STUDENTS_ID
                    + " = " + student.getId());

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update {@link Department} by race number
     *
     * @param department - department you want to update
     * @throws SQLException if department with ID already exists
     */
    public void updateDepartment(Department department) {
        try {
            Statement statement = getDbConnection().createStatement();
            statement.executeUpdate("UPDATE "
                    + Const.DEPARTMENTS_TABLE
                    + " SET "
                    + Const.DEPARTMENTS_DEPARTMENT
                    + " = " + "'" + department.getDepartmentName() + "'"
                    + " WHERE "
                    + Const.DEPARTMENTS_ID
                    + " = " + department.getDepartment_id());

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete {@link Student} from file by ID
     *
     * @param student - train you want to delete
     */
    public void deleteStudent(Student student) {
        try {
            Statement statement = getDbConnection().createStatement();
            statement.executeUpdate("DELETE FROM "
                    + Const.STUDENTS_TABLE
                    + " WHERE "
                    + Const.STUDENTS_ID
                    + " = " + student.getId());

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete {@link Student} from file that learns bad
     *
     * @param badStudents - students to delete
     */
    public void deleteBadStudents(List<Student> badStudents) {
        if (badStudents.size() != 0) {
            try {
                Statement statement = getDbConnection().createStatement();
                for (Student student : badStudents) {
                    statement.executeUpdate("DELETE FROM "
                            + Const.STUDENTS_TABLE
                            + " WHERE id = " + student.getId());
                }

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Delete {@link Department} from file by ID
     *
     * @param department - department you want to delete
     */
    public void deleteDepartment(Department department) {
        try {
            Statement statement = getDbConnection().createStatement();
            statement.executeUpdate("DELETE FROM "
                    + Const.DEPARTMENTS_TABLE
                    + " WHERE "
                    + Const.DEPARTMENTS_ID
                    + " = " + department.getDepartment_id());

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get {@link Student} from file
     *
     * @return <pre>{@code  List<Student>}</pre>
     */
    public List<Student> getStudents() {

        List<Student> students = new ArrayList<>();
        ResultSet resultSetStudents;

        try {
            PreparedStatement preparedStatementStudents = getDbConnection().prepareStatement("SELECT "
                    + "students.id, "
                    + "students.name, "
                    + "students.surname, "
                    + "students.mark1, "
                    + "students.mark2, "
                    + "students.mark3, "
                    + "students.mark4, "
                    + "students.mark5, "
                    + "departments.department "
                    + "FROM " + Const.STUDENTS_TABLE
                    + " INNER JOIN " + Const.DEPARTMENTS_TABLE
                    + " ON students.department_id = departments.department_id ");
            resultSetStudents = preparedStatementStudents.executeQuery();

            while (resultSetStudents.next()) {
                Student student = new Student();
                student.setId(Integer.parseInt(resultSetStudents.getString("students.id")));
                student.setName(resultSetStudents.getString("students.name"));
                student.setSurname(resultSetStudents.getString("students.surname"));
                student.addMark(Integer.parseInt(resultSetStudents.getString("students.mark1")));
                student.addMark(Integer.parseInt(resultSetStudents.getString("students.mark2")));
                student.addMark(Integer.parseInt(resultSetStudents.getString("students.mark3")));
                student.addMark(Integer.parseInt(resultSetStudents.getString("students.mark4")));
                student.addMark(Integer.parseInt(resultSetStudents.getString("students.mark5")));
                student.setDepartmentName(resultSetStudents.getString("departments.department"));
                students.add(student);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return students;
    }

    /**
     * Get {@link Department} from file
     *
     * @return <pre>{@code  List<Department>}</pre>
     */
    public List<Department> getDepartments() {
        ResultSet resultSet;
        List<Department> departments = new ArrayList<Department>();

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement("Select * FROM "
                    + Const.DEPARTMENTS_TABLE);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Department department = new Department();
                department.setDepartment_id(Integer.parseInt(resultSet.getString(1)));
                department.setDepartmentName(resultSet.getString(2));
                departments.add(department);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return departments;
    }

}