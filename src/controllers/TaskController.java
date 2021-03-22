package controllers;

import entity.Project;
import entity.Task;
import entity.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.ShowWindow;
import service.ProjectService;
import service.TaskService;
import service.UserService;

public class TaskController {

    @FXML
    private TextField projectTaskText;

    @FXML
    private TextField topicTaskText;

    @FXML
    private TextField typeTaskText;

    @FXML
    private TextField priorityTaskText;

    @FXML
    private TextField userTaskText;

    @FXML
    private TextArea descriptionTaskText;

    @FXML
    private Button addTaskButton;

    @FXML
    void initialize() {
        addTaskButton.setOnAction(event -> {
            if (userTaskText.getText().equals("") && !projectTaskText.getText().equals("")) {
                ShowWindow sw = new ShowWindow();
                sw.alertWindow("Проект и исполнитель не заполнены",
                        "Для добавления задачи необходимо указать название проекта и исполнителя");
            } else {
                addNewTask(projectTaskText.getText(),
                        topicTaskText.getText(),
                        typeTaskText.getText(),
                        priorityTaskText.getText(),
                        userTaskText.getText(),
                        descriptionTaskText.getText());

            }
        });
    }

    private void addNewTask(String project, String topic, String type, String priority, String user, String description) {
        TaskService taskService = new TaskService();
        Task add_task = new Task();
        add_task.setProject(project);
        add_task.setTopic(topic);
        add_task.setType(type);
        add_task.setUser(user);
        add_task.setDescription(description);
        if (!priority.equals("")) {
            add_task.setPriority(Integer.parseInt(priority));
        }
        if (checkTask(add_task)) {
            if (priority.equals("")) {
                add_task.setPriority(taskService.getMaxPriority(project) + 1);
                System.out.println("установлен приоритет: " + add_task.getPriority());
            }
            taskService.add(add_task);
            Stage stage = (Stage) addTaskButton.getScene().getWindow();
            stage.close();
        }
    }

    private boolean checkTask(Task task) {
        boolean flag = true;
        User user_exists = new User();
        user_exists.setUser(task.getUser());
        Project project_exists = new Project();
        project_exists.setProject(task.getProject());

        TaskService taskService = new TaskService();
        UserService userService = new UserService();
        ProjectService projectService = new ProjectService();
        if (taskService.existTask(task)) { // проверка на существование указанной задачи
            flag = false;
            ShowWindow sw = new ShowWindow();
            sw.alertWindow("Ошибка добавления задачи",
                    "Указанная задача уже существует.");
            System.out.println("Задача не добавлена, т.к. указанная задача уже существует.");
        } else {
            if (!userService.exist(user_exists)) {      // проверка на существование указанного пользователя
                flag = false;
                ShowWindow sw = new ShowWindow();
                sw.alertWindow("Ошибка добавления задачи",
                        "Невозможно добавить указанную задачу, т.к. пользователя " + task.getUser() + " не существует.");
                System.out.println("Задача не добавлена, т.к. пользователя " + task.getUser() + " не существует");
            }
            if (!projectService.exist(project_exists)) {  // проверка на существование указанного проекта
                flag = false;
                ShowWindow sw = new ShowWindow();
                sw.alertWindow("Ошибка добавления задачи",
                        "Невозможно добавить указанную задачу, т.к. проекта " + task.getProject() + " не существует.");
                System.out.println("Задача не добавлена, т.к. проекта " + task.getProject() + " не существует");
            }
            if (taskService.existPriority(task.getProject(), task.getPriority())) {  // проверка на существование приоритера по проекту
                flag = false;
                ShowWindow sw = new ShowWindow();
                sw.alertWindow("Ошибка добавления задачи",
                        "Невозможно добавить указанную задачу, т.к. уже существует задача с приоритетом " + task.getPriority() +
                                " по проекту " + task.getProject());
                System.out.println("Задача не добавлена, т.к. уже существует задача с приоритетом " + task.getPriority()
                        + " по проекту " + task.getProject());
            }
        }
        return flag;
    }


    public boolean removeTask(Task task) {
        if (!task.equals(null)) {
            TaskService taskService = new TaskService();
            taskService.remove(task);
            return true;
        } else
            return false;
    }

}