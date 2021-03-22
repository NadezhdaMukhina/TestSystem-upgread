package controllers;

import dataBase.DataBaseHandler;
import entity.Project;
import entity.Task;
import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import sample.ShowWindow;
import sample.fileReader;
import service.ProjectService;
import service.TaskService;
import service.UserService;

import java.util.ArrayList;


public class Controller {

    @FXML
    private Button fileReadButton;

    @FXML
    private AnchorPane PaneLocal;

    @FXML
    private Button selectButton;

    @FXML
    private ComboBox<String> comboSelect;

    @FXML
    private ComboBox<String> comboUserProject;

    @FXML
    private Button createNewDateBaseButton;

    @FXML
    private Button addUserButton;

    @FXML
    private Button addProjectButton;

    @FXML
    private Button addTaskButton;

    @FXML
    private Button deleteUserButton;

    @FXML
    private Button deleteProjectButton;

    @FXML
    private Button deleteTaskButton;

    @FXML
    private TableView<User> tableUser;

    @FXML
    private TableColumn<User, String> columnUser;

    @FXML
    private TableView<Project> tableProject;

    @FXML
    private TableColumn<Project, String> columnProject;

    @FXML
    private TableView<Task> tableTask;

    @FXML
    private TableColumn<Task, String> columnProjectInTask;

    @FXML
    private TableColumn<Task, String> columnTopic;

    @FXML
    private TableColumn<Task, String> columnType;

    @FXML
    private TableColumn<Task, Integer> columnPriority;

    @FXML
    private TableColumn<Task, String> columnUserInTask;

    @FXML
    private TableColumn<Task, String> columnDescription;

    @FXML
    private Label filePath;

    @FXML
    private Label resultSize;

    @FXML
    void select() {
        if (comboSelect.getSelectionModel().getSelectedIndex() == 2) {//Получить список всех задач в проекте
            comboUserProject.setDisable(false);
            comboUserProject.getItems().clear();
            ObservableList<Project> projectList = selectAllProject();
            for (Project project : projectList) {
                comboUserProject.getItems().add(project.getProject());
            }
            if (projectList.size()>0){
                comboUserProject.setValue(projectList.get(0).getProject());
            }
        }

        if (comboSelect.getSelectionModel().getSelectedIndex() == 3) {//Получить список всех задач, назначенных на исполнителя
            comboUserProject.setDisable(false);
            comboUserProject.getItems().clear();
            ObservableList<User> userList = selectAllUser();
            for (User user : userList) {
                comboUserProject.getItems().add(user.getUser());
            }
            if (userList.size()>0) {
                comboUserProject.setValue(userList.get(0).getUser());
            }
        }
        if (comboSelect.getSelectionModel().getSelectedIndex() < 2) {
            comboUserProject.setDisable(true);
            comboUserProject.getItems().clear();
        }
    }

    @FXML
    void initialize() {
        ObservableList<String> selectList = FXCollections.observableArrayList("Получить список всех пользователей",
                "Получить список всех проектов",
                "Получить список всех задач в проекте",
                "Получить список всех задач, назначенных на исполнителя");
        comboSelect.setItems(selectList);
        comboSelect.setValue(selectList.get(0));
        comboUserProject.setDisable(true);

        fileReadButton.setOnAction(this::fileReadButtonAction);
        addUserButton.setOnAction(this::addUserButtonAction);
        addProjectButton.setOnAction(this::addProjectButtonAction);
        addTaskButton.setOnAction(this::addTaskButtonAction);
        selectButton.setOnAction(this::selectButtonAction);
        createNewDateBaseButton.setOnAction(this::createNewDateBase);
        deleteUserButton.setOnAction(this::deleteUser);
        deleteProjectButton.setOnAction(this::deleteProject);
        deleteTaskButton.setOnAction(this::deleteTask);
    }


    private void selectButtonAction(ActionEvent actionEvent) {
        switch (comboSelect.getSelectionModel().getSelectedIndex()) {
            case (0): // получить список всех пользователей
                ObservableList<User> userData = selectAllUser();
                resultSize.setText("Количество записей: " + userData.size());
                columnUser.setCellValueFactory(new PropertyValueFactory<>("User"));
                tableUser.setItems(userData);
                break;

            case (1): // получить список всех проектов
                ObservableList<Project> projectData = selectAllProject();
                resultSize.setText("Количество записей: " + projectData.size());
                columnProject.setCellValueFactory(new PropertyValueFactory<>("Project"));
                tableProject.setItems(projectData);
                break;

            case (2):// получить список всех задач в проекте
                selectAllTaskForProject();
                break;

            case (3): // получить список всех задач, назначенных на исполнителя
                selectAllTaskForUser();
                break;
        }
    }

    private ObservableList<User> selectAllUser() {
        UserService userService = new UserService();
        ObservableList<User> userData = FXCollections.observableArrayList();
        ArrayList<User> userList = userService.getAll();
        userData.addAll(userList);
        return userData;
    }

    private ObservableList<Project> selectAllProject() {
        ProjectService projectService = new ProjectService();
        ObservableList<Project> projectData = FXCollections.observableArrayList();
        ArrayList<Project> projectList = projectService.getAll();
        projectData.addAll(projectList);
        return projectData;
    }

    private void selectAllTaskForProject() {
        TaskService taskService1 = new TaskService();
        ObservableList<Task> taskData1 = FXCollections.observableArrayList();
        ArrayList<Task> taskList1 = taskService1.getByProject(comboUserProject.getSelectionModel().getSelectedItem());
        taskData1.addAll(taskList1);
        resultSize.setText("Количество записей: " + taskList1.size());
        columnProjectInTask.setCellValueFactory(new PropertyValueFactory<>("Project"));
        columnTopic.setCellValueFactory(new PropertyValueFactory<>("Topic"));
        columnType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        columnPriority.setCellValueFactory(new PropertyValueFactory<>("Priority"));
        columnUserInTask.setCellValueFactory(new PropertyValueFactory<>("User"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        tableTask.setItems(taskData1);
    }

    private void selectAllTaskForUser() {
        TaskService taskService2 = new TaskService();
        ObservableList<Task> taskData2 = FXCollections.observableArrayList();
        ArrayList<Task> taskList2 = taskService2.getByUser(comboUserProject.getSelectionModel().getSelectedItem());
        taskData2.addAll(taskList2);
        resultSize.setText("Количество записей: " + taskList2.size());
        columnProjectInTask.setCellValueFactory(new PropertyValueFactory<>("Project"));
        columnTopic.setCellValueFactory(new PropertyValueFactory<>("Topic"));
        columnType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        columnPriority.setCellValueFactory(new PropertyValueFactory<>("Priority"));
        columnUserInTask.setCellValueFactory(new PropertyValueFactory<>("User"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        tableTask.setItems(taskData2);
    }


    private void addUserButtonAction(ActionEvent event) {// кнопка "Создать/удалить пользователя"
        ShowWindow sw = new ShowWindow();
        sw.showWindow("/view/addUser.fxml");// отображаем окно для ввода данных

    }

    private void addProjectButtonAction(ActionEvent event) {// кнопка "Создать/удалить проект// "
        ShowWindow sw = new ShowWindow();
        sw.showWindow("/view/addProject.fxml");// отображаем окно для ввода данных

    }

    private void addTaskButtonAction(ActionEvent event) {// кнопка "Создать/удалить задачу"
        ShowWindow sw = new ShowWindow();
        sw.showWindow("/view/addTask.fxml");// отображаем окно для ввода данных
    }


    private void fileReadButtonAction(ActionEvent event) { // нажатие на кнопку "Выбрать файл для загрузки данных"
        fileReader flRW = new fileReader();
        DataBaseHandler dbHandler = new DataBaseHandler();
        try {
            String path = flRW.fileRead(event);
            dbHandler.setPathDb(path);
            filePath.setText("Выбран файл для загрузки данных: " + path);
            PaneLocal.setDisable(false); // делаем видимой рабочую область
        } catch (NullPointerException e) {
            System.out.println("Файл для загрузки данных не выбран!");
        }
    }

    private void createNewDateBase(ActionEvent event) {// инициализация новой БД
        fileReader fr = new fileReader();
        DataBaseHandler dbHandler = new DataBaseHandler();
        try {
            // создание файла БД
            String path = fr.fileCreate(event);
            dbHandler.setPathDb(path);
            filePath.setText("Выбран файл для загрузки данных: " + path);
            PaneLocal.setDisable(false); // делаем видимой рабочую область
            dbHandler.createTables(); // создание таблиц БД
        } catch (NullPointerException e) {
            System.out.println("Файл БД не создан!");
        }
    }

    public void deleteUser(ActionEvent actionEvent) {// кнопка удаления выбранного пользователя
        int selectedIndex = tableUser.getSelectionModel().getSelectedIndex();
        User selectedUser = tableUser.getSelectionModel().getSelectedItem();
        try {
            UserController userController = new UserController();
            if (userController.removeUser(selectedUser)) {
                tableUser.getItems().remove(selectedIndex);
            }
        } catch (Exception e) {
            System.out.println("Ошибка удаления пользователя!");
        }
    }

    private void deleteProject(ActionEvent actionEvent) {// кнопка удаления выбранного проекта
        int selectedIndex = tableProject.getSelectionModel().getSelectedIndex();
        Project selectedProject = tableProject.getSelectionModel().getSelectedItem();
        try {
            ProjectController projectController = new ProjectController();
            if (projectController.removeProject(selectedProject)) {
                tableProject.getItems().remove(selectedIndex);
            }
        } catch (Exception e) {
            System.out.println("Ошибка удаления проекта!");
        }
    }

    private void deleteTask(ActionEvent actionEvent) {// кнопка удаления выбранной задачи
        int selectedIndex = tableTask.getSelectionModel().getSelectedIndex();
        Task selectedTask = tableTask.getSelectionModel().getSelectedItem();
        try {
            TaskController taskController = new TaskController();
            if (taskController.removeTask(selectedTask)) {
                tableTask.getItems().remove(selectedIndex);
            }
        } catch (Exception e) {
            System.out.println("Ошибка удаления проекта!");
        }
    }
}

