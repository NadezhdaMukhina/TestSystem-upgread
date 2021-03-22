package controllers;

import entity.Project;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.ShowWindow;
import service.ProjectService;
import service.TaskService;

public class ProjectController {

    @FXML
    private TextField newProjectText;

    @FXML
    private Button addProjectButton;

    @FXML
    void initialize() {
        addProjectButton.setOnAction(event -> {// добавляем проект
            if (newProjectText.getText().equals("")) {
                ShowWindow sw = new ShowWindow();
                sw.alertWindow("Проект не указан",
                        "Название проекта не может быть пустым.\n Укажите проект для добавления.");
            } else {
                addProject(newProjectText.getText());
            }
        });
    }

    private void addProject(String project) {// добавляем проект
        ProjectService projectService = new ProjectService();
        Project add_project = new Project();
        add_project.setProject(project);
        if (!projectService.exist(add_project)) {// проверка на существование такого проекта
            projectService.add(add_project);
            Stage stage = (Stage) addProjectButton.getScene().getWindow();
            stage.close();
        } else {
            ShowWindow sw = new ShowWindow();
            sw.alertWindow("Ошибка добавления проекта",
                    "Проект " + project + " уже существует.");
            System.out.println("Проект " + project + " не добавлен, т.к. указанный проект уже существует.");
        }
    }

    public boolean removeProject(Project project) {
        if (!checkProject(project.getProject())) {
            removeProjectFromDB(project);
            return true;
        } else {
            ShowWindow sw = new ShowWindow();
            sw.alertWindow("Ошибка удаления проекта",
                    "Проект " + project.getProject() + " не может быть удален, т.к. по данному проекту существую задачи.");
            System.out.println("Проект " + project.getProject() + " не удален, т.к. по данному проекту существую задачи");
            return false;
        }
    }

    private void removeProjectFromDB(Project project) {// если задач по проекту нет, то удаляем проект
        ProjectService projectService = new ProjectService();
        projectService.remove(project);
    }

    private boolean checkProject(String project) {// проверка на наличие задач по проекту
        boolean flag = false;
        TaskService taskService = new TaskService();
        if (taskService.getByProject(project).size() > 0) {
            flag = true;
        }
        return flag;
    }
}
