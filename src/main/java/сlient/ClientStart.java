package сlient;

import dao.sql.DatabaseHandler;
import javafx.application.Application;
import javafx.application.Platform;
import service.DepartmentDAOControlService;
import service.StudentDAOControlService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Department;
import model.Student;

import javax.xml.bind.JAXBException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Andrey "myfosse" Egorov
 * @version 1.0
 */

public class ClientStart extends Application {

    private MenuBar menuBar;

    private Menu fileMenu;
    private Menu addMenu;
    private Menu updateMenu;
    private Menu deleteMenu;

    private MenuItem showStudentsItem;
    private MenuItem showDepartmentsItem;
    private MenuItem showStudentsToDeleteItem;
    private MenuItem exitItem;

    private MenuItem addStudentItem;
    private MenuItem addDepartmentItem;

    private MenuItem updateStudentItem;
    private MenuItem updateDepartmentItem;

    private MenuItem deleteDepartmentItem;
    private MenuItem deleteStudentItem;
    private MenuItem deleteBadStudentsItem;

    private ObservableList<Student> studentObservableList;
    private TableView<Student> studentTableView;

    StudentDAOControlService studentDAOControlService;
    DepartmentDAOControlService departmentDAOControlService;

    List<Student> studentsList;
    List<Student> studentsToDeleteList;
    List<Department> departmentsList;

    String localhost = "127.0.0.1";
    //String localhost = "192.168.100.52";
    //String localhost = "192.168.100.57";
    String RMI_HOSTNAME = "java.rmi.server.hostname";
    String SERVICE_PATH = "rmi://127.0.0.1/BDService";
    //String SERVICE_PATH = "rmi://192.168.100.57/BDService";

    @Override
    public void start(Stage primaryStage) {
        try {

            System.setProperty(RMI_HOSTNAME, localhost);
            String objectName = SERVICE_PATH;
            studentDAOControlService = (StudentDAOControlService) Naming.lookup(objectName);
            departmentDAOControlService = (DepartmentDAOControlService) Naming.lookup(objectName);

            departmentsList = new ArrayList<>();
            studentsList = new ArrayList<>();
            studentsToDeleteList = new ArrayList<>();

            departmentsList = departmentDAOControlService.updateDepartments();
            studentsList = studentDAOControlService.updateStudents();
            studentsToDeleteList = studentDAOControlService.updateBadStudents(studentsList);

            BorderPane root = new BorderPane();
            Scene scene = new Scene(root, 700, 500);
            root.setCenter(createTableView());
            root.setTop(createMenuBar());

            primaryStage.setScene(scene);
            primaryStage.setTitle("Egorov \"myfosse\" Andrey");
            primaryStage.show();

            showStudentsItem.setOnAction(e -> {
                studentObservableList.clear();
                studentObservableList.addAll(studentsList);
                studentTableView.refresh();
            });

            showStudentsToDeleteItem.setOnAction(e -> {
                studentObservableList.clear();
                studentObservableList.addAll(studentsToDeleteList);
                studentTableView.refresh();
            });

            showDepartmentsItem.setOnAction(e -> {
                showDepartmentsDialog();
            });

            addStudentItem.setOnAction(e -> {
                showAddStudentDialog();
                try {
                    studentsList = studentDAOControlService.updateStudents();
                    studentsToDeleteList = studentDAOControlService.updateBadStudents(studentsList);
                } catch (RemoteException | JAXBException ex) {
                    ex.printStackTrace();
                }

                studentObservableList.clear();
                studentObservableList.addAll(studentsList);
                studentTableView.refresh();
            });

            addDepartmentItem.setOnAction(event -> {
                showAddDepartmentDialog();
                try {
                    studentsList = studentDAOControlService.updateStudents();
                    studentsToDeleteList = studentDAOControlService.updateBadStudents(studentsList);
                    departmentsList = departmentDAOControlService.updateDepartments();
                } catch (RemoteException | JAXBException ex) {
                    ex.printStackTrace();
                }
            });

            updateStudentItem.setOnAction(e -> {
                showUpdateStudentDialog();
                try {
                    studentsList = studentDAOControlService.updateStudents();
                    studentsToDeleteList = studentDAOControlService.updateBadStudents(studentsList);
                } catch (RemoteException | JAXBException ex) {
                    ex.printStackTrace();
                }

                studentObservableList.clear();
                studentObservableList.addAll(studentsList);
                studentTableView.refresh();
            });

            updateDepartmentItem.setOnAction(e -> {
                showUpdateDepartmentDialog();
                try {
                    studentsList = studentDAOControlService.updateStudents();
                    studentsToDeleteList = studentDAOControlService.updateBadStudents(studentsList);
                    departmentsList = departmentDAOControlService.updateDepartments();
                } catch (RemoteException | JAXBException ex) {
                    ex.printStackTrace();
                }

                studentObservableList.clear();
                studentObservableList.addAll(studentsList);
                studentTableView.refresh();
            });

            deleteStudentItem.setOnAction(e -> {
                showDeleteStudentDialog();
                try {
                    studentsList = studentDAOControlService.updateStudents();
                    studentsToDeleteList = studentDAOControlService.updateBadStudents(studentsList);
                } catch (RemoteException | JAXBException ex) {
                    ex.printStackTrace();
                }

                studentObservableList.clear();
                studentObservableList.addAll(studentsList);
                studentTableView.refresh();
            });

            deleteBadStudentsItem.setOnAction(e -> {
                try {
                    studentDAOControlService.deleteBadStudents(studentsToDeleteList);
                    studentsList = studentDAOControlService.updateStudents();
                    studentsToDeleteList = studentDAOControlService.updateBadStudents(studentsList);
                } catch (RemoteException | JAXBException ex) {
                    ex.printStackTrace();
                }

                studentObservableList.clear();
                studentObservableList.addAll(studentsList);
                studentTableView.refresh();
            });

            deleteDepartmentItem.setOnAction(e -> {
                showDeleteDepartmentDialog();
                try {
                    departmentsList = departmentDAOControlService.updateDepartments();
                } catch (RemoteException | JAXBException ex) {
                    ex.printStackTrace();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private MenuBar createMenuBar() {
        menuBar = new MenuBar();

        fileMenu = new Menu("File");
        addMenu = new Menu("Add");
        updateMenu = new Menu("Update");
        deleteMenu = new Menu("Delete");

        showStudentsItem = new MenuItem("Show students");
        showDepartmentsItem = new MenuItem("Show departments");
        showStudentsToDeleteItem = new MenuItem("Show students to delete");
        exitItem = new MenuItem("Exit");

        addStudentItem = new MenuItem("Add student");
        addDepartmentItem = new MenuItem("Add department");

        updateStudentItem = new MenuItem("Update student");
        updateDepartmentItem = new MenuItem("Update department");

        deleteStudentItem = new MenuItem("Delete student");
        deleteDepartmentItem = new MenuItem("Delete department");
        deleteBadStudentsItem = new MenuItem("Delete bad students");

        fileMenu.getItems().addAll(showStudentsItem, showDepartmentsItem, showStudentsToDeleteItem, exitItem);
        addMenu.getItems().addAll(addStudentItem, addDepartmentItem);
        updateMenu.getItems().addAll(updateStudentItem, updateDepartmentItem);
        deleteMenu.getItems().addAll(deleteStudentItem, deleteDepartmentItem, deleteBadStudentsItem);

        menuBar.getMenus().addAll(fileMenu, addMenu, updateMenu, deleteMenu);

        return menuBar;
    }

    private TableView<Student> createTableView() {
        studentObservableList = FXCollections.observableArrayList(studentsList);
        studentTableView = new TableView<>(studentObservableList);

        TableColumn<Student, Integer> studentIDColumn = new TableColumn<Student, Integer>("ID");
        studentIDColumn.setCellValueFactory(new PropertyValueFactory<Student, Integer>("id"));

        TableColumn<Student, String> studentNameColumn = new TableColumn<Student, String>("Name");
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("Name"));

        TableColumn<Student, String> studentSurnameColumn = new TableColumn<Student, String>("Surname");
        studentSurnameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("surname"));

        TableColumn<Student, ArrayList<Integer>> studentMarksColumn = new TableColumn<Student, ArrayList<Integer>>("Marks");
        studentMarksColumn.setCellValueFactory(new PropertyValueFactory<Student, ArrayList<Integer>>("marks"));

        TableColumn<Student, String> studentDepartmentColumn = new TableColumn<Student, String>("Department");
        studentDepartmentColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("departmentName"));

        studentTableView.getColumns().addAll(studentIDColumn, studentNameColumn, studentSurnameColumn,
                studentMarksColumn,
                studentDepartmentColumn);

        studentTableView.setItems(studentObservableList);
        return studentTableView;
    }

    private void showDepartmentsDialog() {
        ObservableList<Department> departmentObservableList = FXCollections.observableArrayList(departmentsList);
        TableView<Department> departmentTableView = new TableView<>(departmentObservableList);

        TableColumn<Department, Integer> departmentIDColumn = new TableColumn<Department, Integer>("ID");
        departmentIDColumn.setCellValueFactory(new PropertyValueFactory<Department, Integer>("department_id"));
        TableColumn<Department, String> departmentNameColumn = new TableColumn<Department, String>("Name");
        departmentNameColumn.setCellValueFactory(new PropertyValueFactory<Department, String>("departmentName"));

        departmentTableView.getColumns().addAll(departmentIDColumn, departmentNameColumn);
        departmentTableView.setItems(departmentObservableList);

        Dialog<Student> dialog = new Dialog<>();
        dialog.setTitle("Departments");
        dialog.setHeaderText("Info Dialog");
        dialog.getDialogPane().setContent(departmentTableView);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE);
        dialog.showAndWait();
    }

    private void showAddStudentDialog() {
        Dialog<Student> dialog = new Dialog<>();
        dialog.setTitle("Student Dialog");
        dialog.setHeaderText("Add Dialog");
        ButtonType okButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField idTextField = new TextField();
        TextField nameTextField = new TextField();
        TextField surnameTextField = new TextField();
        TextField mark1TextField = new TextField();
        TextField mark2TextField = new TextField();
        TextField mark3TextField = new TextField();
        TextField mark4TextField = new TextField();
        TextField mark5TextField = new TextField();

        ComboBox<Department> comboBox = new ComboBox<>();

        ObservableList<Department> list = FXCollections.observableArrayList(departmentsList);
        comboBox.setItems(list);
        comboBox.getSelectionModel().select(0);

        grid.add(new Label("ID:"), 0, 0);
        grid.add(idTextField, 1, 0);
        grid.add(new Label("Name:"), 0, 1);
        grid.add(nameTextField, 1, 1);
        grid.add(new Label("Surname:"), 0, 2);
        grid.add(surnameTextField, 1, 2);
        grid.add(new Label("Mark 1:"), 0, 3);
        grid.add(mark1TextField, 1, 3);
        grid.add(new Label("Mark 2:"), 0, 4);
        grid.add(mark2TextField, 1, 4);
        grid.add(new Label("Mark 3:"), 0, 5);
        grid.add(mark3TextField, 1, 5);
        grid.add(new Label("Mark 4:"), 0, 6);
        grid.add(mark4TextField, 1, 6);
        grid.add(new Label("Mark 5:"), 0, 7);
        grid.add(mark5TextField, 1, 7);
        grid.add(new Label("Department:"), 0, 8);
        grid.add(comboBox, 1, 8);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButton) {
                Student student = new Student();
                student.setId(Integer.parseInt(idTextField.getText()));
                student.setName(nameTextField.getText());
                student.setSurname(surnameTextField.getText());
                student.addMark(Integer.parseInt(mark1TextField.getText()));
                student.addMark(Integer.parseInt(mark2TextField.getText()));
                student.addMark(Integer.parseInt(mark3TextField.getText()));
                student.addMark(Integer.parseInt(mark4TextField.getText()));
                student.addMark(Integer.parseInt(mark5TextField.getText()));
                student.setDepartmentName(comboBox.getValue().getDepartmentName());
                student.setDepartment_id(comboBox.getValue().getDepartment_id());
                try {
                    studentDAOControlService.addStudent(student);
                } catch (RemoteException | JAXBException e) {
                    e.printStackTrace();
                }
                return student;
            }
            return null;
        });
        dialog.showAndWait();
    }

    private void showUpdateStudentDialog() {
        Dialog<Student> dialog = new Dialog<>();
        dialog.setTitle("Student Dialog");
        dialog.setHeaderText("Update Dialog");
        ButtonType updateButton = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(updateButton, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nameTextField = new TextField();
        TextField surnameTextField = new TextField();
        TextField mark1TextField = new TextField();
        TextField mark2TextField = new TextField();
        TextField mark3TextField = new TextField();
        TextField mark4TextField = new TextField();
        TextField mark5TextField = new TextField();
        Button fillButton = new Button("Fill");

        ComboBox<Student> comboBox = new ComboBox<>();
        ComboBox<Department> comboBoxD = new ComboBox<>();

        ObservableList<Student> list = FXCollections.observableArrayList(studentsList);
        ObservableList<Department> listD = FXCollections.observableArrayList(departmentsList);
        comboBox.setItems(list);
        comboBoxD.setItems(listD);
        comboBox.getSelectionModel().select(0);
        comboBox.getSelectionModel().select(0);

        grid.add(new Label("Student:"), 0, 0);
        grid.add(comboBox, 1, 0);
        grid.add(new Label("Fill fields:"), 0, 1);
        grid.add(fillButton, 1, 1);
        grid.add(new Label("Name:"), 0, 2);
        grid.add(nameTextField, 1, 2);
        grid.add(new Label("Surname:"), 0, 3);
        grid.add(surnameTextField, 1, 3);
        grid.add(new Label("Mark 1:"), 0, 4);
        grid.add(mark1TextField, 1, 4);
        grid.add(new Label("Mark 2:"), 0, 5);
        grid.add(mark2TextField, 1, 5);
        grid.add(new Label("Mark 3:"), 0, 6);
        grid.add(mark3TextField, 1, 6);
        grid.add(new Label("Mark 4:"), 0, 7);
        grid.add(mark4TextField, 1, 7);
        grid.add(new Label("Mark 5:"), 0, 8);
        grid.add(mark5TextField, 1, 8);
        grid.add(new Label("Department:"), 0, 9);
        grid.add(comboBoxD, 1, 9);

        dialog.getDialogPane().setContent(grid);
        fillButton.setOnAction(event -> {
            nameTextField.setText(comboBox.getValue().getName());
            surnameTextField.setText(comboBox.getValue().getSurname());
            mark1TextField.setText(String.valueOf(comboBox.getValue().getMark(0)));
            mark2TextField.setText(String.valueOf(comboBox.getValue().getMark(1)));
            mark3TextField.setText(String.valueOf(comboBox.getValue().getMark(2)));
            mark4TextField.setText(String.valueOf(comboBox.getValue().getMark(3)));
            mark5TextField.setText(String.valueOf(comboBox.getValue().getMark(4)));
            comboBoxD.getSelectionModel().select(0);
        });

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == updateButton) {

                Student student = new Student();
                student.setId(comboBox.getValue().getId());
                student.setName(nameTextField.getText());
                student.setSurname(surnameTextField.getText());
                student.addMark(Integer.parseInt(mark1TextField.getText()));
                student.addMark(Integer.parseInt(mark2TextField.getText()));
                student.addMark(Integer.parseInt(mark3TextField.getText()));
                student.addMark(Integer.parseInt(mark4TextField.getText()));
                student.addMark(Integer.parseInt(mark5TextField.getText()));
                student.setDepartmentName(comboBoxD.getValue().getDepartmentName());
                student.setDepartment_id(comboBoxD.getValue().getDepartment_id());

                try {
                    studentDAOControlService.updateStudent(student);
                } catch (RemoteException | JAXBException e) {
                    e.printStackTrace();
                }
                return student;
            }
            return null;
        });
        dialog.showAndWait();
    }

    private void showDeleteStudentDialog() {
        Dialog<Student> dialog = new Dialog<>();
        dialog.setTitle("Student Dialog");
        dialog.setHeaderText("Delete Dialog");
        ButtonType okButton = new ButtonType("Delete", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        ComboBox<Student> comboBox = new ComboBox<>();

        ObservableList<Student> list = FXCollections.observableArrayList(studentsList);
        comboBox.setItems(list);
        comboBox.getSelectionModel().select(0);

        grid.add(new Label("Department:"), 0, 0);
        grid.add(comboBox, 1, 0);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButton) {
                Student student = new Student();
                student.setId(comboBox.getValue().getId());
                student.setName(comboBox.getValue().getName());
                student.setSurname(comboBox.getValue().getSurname());
                student.addMark(comboBox.getValue().getMark(0));
                student.addMark(comboBox.getValue().getMark(1));
                student.addMark(comboBox.getValue().getMark(2));
                student.addMark(comboBox.getValue().getMark(3));
                student.addMark(comboBox.getValue().getMark(4));
                student.setDepartment_id(comboBox.getValue().getDepartment_id());

                try {
                    studentDAOControlService.deleteStudent(student);
                } catch (RemoteException | JAXBException e) {
                    e.printStackTrace();
                }
                return student;
            }
            return null;
        });
        Optional<Student> result = dialog.showAndWait();
        result.ifPresent(usernamePassword -> {
        });
    }

    private void showAddDepartmentDialog() {
        Dialog<Department> dialog = new Dialog<>();
        dialog.setTitle("Department Dialog");
        dialog.setHeaderText("Add Dialog");
        ButtonType loginButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField idTextField = new TextField();
        idTextField.setPromptText("ID");
        TextField departmentTextField = new TextField();
        departmentTextField.setPromptText("Department");

        grid.add(new Label("ID:"), 0, 0);
        grid.add(idTextField, 1, 0);
        grid.add(new Label("Department:"), 0, 1);
        grid.add(departmentTextField, 1, 1);

        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        idTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(idTextField::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                Department department = new Department(Integer.parseInt(idTextField.getText()), departmentTextField.getText());
                try {
                    departmentDAOControlService.addDepartment(department);
                } catch (RemoteException | JAXBException e) {
                    e.printStackTrace();
                }
                return department;
            }
            return null;
        });

        Optional<Department> result = dialog.showAndWait();
        result.ifPresent(usernamePassword -> {
        });
    }

    private void showUpdateDepartmentDialog() {
        Dialog<Department> dialog = new Dialog<>();
        dialog.setTitle("Department Dialog");
        dialog.setHeaderText("Update Dialog");
        ButtonType okButton = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField departmentNameTextField = new TextField();
        departmentNameTextField.setPromptText("Department name");

        ComboBox<Department> comboBox = new ComboBox<>();

        ObservableList<Department> list = FXCollections.observableArrayList(departmentsList);
        comboBox.setItems(list);
        comboBox.getSelectionModel().select(0);

        grid.add(new Label("Department:"), 0, 0);
        grid.add(comboBox, 1, 0);
        grid.add(new Label("Department:"), 0, 1);
        grid.add(departmentNameTextField, 1, 1);

        departmentNameTextField.setText(comboBox.getValue().getDepartmentName());

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButton) {
                Department department = new Department(
                        comboBox.getValue().getDepartment_id(),
                        departmentNameTextField.getText()
                );
                try {
                    departmentDAOControlService.updateDepartment(department);
                } catch (RemoteException | JAXBException e) {
                    e.printStackTrace();
                }
                return department;
            }
            return null;
        });
        Optional<Department> result = dialog.showAndWait();
        result.ifPresent(usernamePassword -> {
        });
    }

    private void showDeleteDepartmentDialog() {
        Dialog<Department> dialog = new Dialog<>();
        dialog.setTitle("Department Dialog");
        dialog.setHeaderText("Delete Dialog");
        ButtonType okButton = new ButtonType("Delete", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField departmentIDTextField = new TextField();
        departmentIDTextField.setPromptText("Department ID");
        TextField departmentNameTextField = new TextField();
        departmentNameTextField.setPromptText("Department name");

        ComboBox<Department> comboBox = new ComboBox<>();

        ObservableList<Department> list = FXCollections.observableArrayList(departmentsList);
        comboBox.setItems(list);
        comboBox.getSelectionModel().select(0);

        grid.add(new Label("Department:"), 0, 0);
        grid.add(comboBox, 1, 0);

        Node loginButton = dialog.getDialogPane().lookupButton(okButton);

        departmentIDTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(departmentIDTextField::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButton) {
                Department department = new Department(
                        comboBox.getValue().getDepartment_id(),
                        comboBox.getValue().getDepartmentName()
                );

                try {
                    departmentDAOControlService.deleteDepartment(department);
                } catch (RemoteException | JAXBException e) {
                    e.printStackTrace();
                }

                return department;
            }
            return null;
        });
        Optional<Department> result = dialog.showAndWait();
        result.ifPresent(usernamePassword -> {
        });
    }

}