package service;

import dao.UserDAO;
import dataBase.Const;
import dataBase.DataBaseHandler;
import entity.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserService extends DataBaseHandler implements UserDAO {

    @Override
    public void create() {
        Connection co = getConnection(pathDb);
        Statement statement = null;
        String query = "CREATE TABLE IF NOT EXISTS " + Const.USER_TABLE +
                " ('" + Const.USER_NAME + "' VARCHAR(50) NOT NULL);";
        try {
            statement = co.createStatement();
            statement.executeUpdate(query);
            System.out.println("Создана новая таблица Пользователи.");
        } catch (SQLException e) {
            System.out.println("Ошибка создания таблицы Пользователи!");
        } finally {
            try {
            if (statement != null) {
                statement.close();
            }
            if (co != null) {
                co.close();
            }
            } catch (SQLException e) {
                System.out.println("Ошибка закрытия базы данных!");
            }
        }
    }

    @Override
    public void add(User user) {// добавление нового пользователя
        Connection co = getConnection(pathDb);
        Statement statement = null;
        String query = "INSERT INTO " + Const.USER_TABLE + " (" + Const.USER_NAME
                + ") VALUES ('" + user.getUser() + "');";
        try {
            statement = co.createStatement();
            statement.executeUpdate(query);
            System.out.println("Добавление нового пользователя " + user.getUser() + " выполнено.");
        } catch (SQLException e) {
            System.out.println("Запрос на добавление нового пользователя " + user.getUser() + " не выполнен!");
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (co != null) {
                    co.close();
                }
            } catch (SQLException e) {
                System.out.println("Ошибка закрытия базы данных!");
            }
        }
    }

    @Override
    public ArrayList<User> getAll() {//вывод всех пользователей
        Connection co = getConnection(pathDb);
        ArrayList<User> userList = new ArrayList<>();
        String query = "SELECT * FROM " + Const.USER_TABLE + " ORDER BY " + Const.USER_NAME;
        Statement statement = null;
        try {
            statement = co.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                User user = new User();
                user.setUser(resultSet.getString(Const.USER_NAME));

                userList.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Запрос на получение всех пользователей не выполнен!");
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (co != null) {
                    co.close();
                }
            } catch (SQLException e) {
                System.out.println("Ошибка закрытия базы данных!");
            }
        }
        return userList;
    }

    @Override
    public boolean exist(User user) {// проверка на существование пользователя
        Connection co = getConnection(pathDb);
        String query = "SELECT * FROM " + Const.USER_TABLE
                + " WHERE " + Const.USER_NAME + "='" + user.getUser() + "';";
        Statement statement = null;
        boolean flag = false;
        try {
            statement = co.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                flag = true;
            }
        } catch (SQLException e) {
            System.out.println("Проверка на существование пользователя "+ user.getUser() + " не выполнена");
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (co != null) {
                    co.close();
                }
            } catch (SQLException e) {
                System.out.println("Ошибка закрытия базы данных!");
            }
        }
        return flag;
    }

    @Override
    public void remove(User user) {//удаление пользователя
        Connection co = getConnection(pathDb);
        Statement statement = null;
        String query = "DELETE FROM " + Const.USER_TABLE
                + " WHERE " + Const.USER_NAME + "='" + user.getUser() + "';";
        try {
            statement = co.createStatement();
            statement.executeUpdate(query);
            System.out.println("Пользователь " + user.getUser() + " удален.");
        } catch (SQLException e) {
            System.out.println("Запрос на удаление пользователя " + user.getUser() + " не выполнен!");
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (co != null) {
                    co.close();
                }
            } catch (SQLException e) {
                System.out.println("Ошибка закрытия базы данных!");
            }
        }
    }
}
