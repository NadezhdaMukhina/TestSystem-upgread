package controllers;

import entity.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.ShowWindow;
import service.TaskService;
import service.UserService;

import java.sql.SQLException;

public class UserController {

    @FXML
    private TextField newUserText;

    @FXML
    private Button addUserButton;

    @FXML
    void initialize() {
        addUserButton.setOnAction(event -> {
            if (newUserText.getText().equals("")) {
                ShowWindow sw = new ShowWindow();
                sw.alertWindow("Пользователь не указан",
                        "Название пользователя не может быть пустым.\n Укажите пользователя для добавления.");
            } else {
                try {
                    addUser(newUserText.getText());
                } catch (SQLException e) {
                    System.out.println("Добавление пользователя не выполнено!");
                }

            }
        });
    }

    private void addUser(String user) throws SQLException {// если пользователя еще нет в таблице, то добавляем
        UserService userService = new UserService();
        User add_user = new User();
        add_user.setUser(user);
        if (!userService.exist(add_user)) {// проверка на существование такого пользователя
            userService.add(add_user);
            Stage stage = (Stage) addUserButton.getScene().getWindow();
            stage.close();
        } else {
            ShowWindow sw = new ShowWindow();
            sw.alertWindow("Ошибка добавления пользователя",
                    "Пользователь " + user + " уже существует.");
            System.out.println("Пользователь " + user + " не добавлен, т.к. указанный пользователь уже существует.");
        }
    }

    public boolean removeUser(User user) {
        if (!checkUser(user.getUser())) {
            removeUserFromDB(user);
            return true;
        } else {
            ShowWindow sw = new ShowWindow();
            sw.alertWindow("Ошибка удаления пользователя",
                    "Пользователь " + user.getUser() + " не может быть удален, т.к. на него назначена задача.");
            System.out.println("Пользователь " + user.getUser() + " не удален, т.к. на него назначена задача.");
            return false;
        }
    }

    private void removeUserFromDB(User user) {// если задач для пользователя нет, то удаляем его
        UserService userService = new UserService();
        userService.remove(user);
    }

    boolean checkUser(String user) {// проверка на наличие задач по пользователю
        boolean flag = false;
        TaskService taskService = new TaskService();
        if (taskService.getByUser(user).size() > 0) {
            flag = true;
        }
        return flag;
    }
}

