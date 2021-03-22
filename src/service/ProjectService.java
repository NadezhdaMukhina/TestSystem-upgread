package service;

import dao.ProjectDAO;
import dataBase.Const;
import dataBase.DataBaseHandler;
import entity.Project;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProjectService extends DataBaseHandler implements ProjectDAO {

    @Override
    public void create() {// создание новой таблицы БД
        Connection co = getConnection(pathDb);
        Statement statement = null;
        String query = "CREATE TABLE IF NOT EXISTS " + Const.PROJECTS_TABLE +
                " ('" + Const.PROJECT_NAME + "' VARCHAR(50) NOT NULL);";
        try {
            statement = co.createStatement();
            statement.executeUpdate(query);
            System.out.println("Создана новая таблица Проекты.");
        } catch (SQLException e) {
            System.out.println("Ошибка создания таблицы Проекты!");
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
    public void add(Project project) {// добавление нового проекта
        Connection co = getConnection(pathDb);
        Statement statement = null;
        String query = "INSERT INTO " + Const.PROJECTS_TABLE + " ("
                + Const.PROJECT_NAME + ") VALUES ('" + project.getProject() + "');";

        try {
            statement = co.createStatement();
            statement.executeUpdate(query);
            System.out.println("Добавление нового проекта " + project.getProject() + " выполнено.");
        } catch (SQLException e) {
            System.out.println("Запрос на добавление нового проекта " + project.getProject() + " не выполнен!");
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
    public ArrayList<Project> getAll() {
        Connection co = getConnection(pathDb);
        ArrayList<Project> projectList = new ArrayList<>();
        String query = "SELECT * FROM " + Const.PROJECTS_TABLE + " ORDER BY " + Const.PROJECT_NAME;
        Statement statement = null;
        try {
            statement = co.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Project project = new Project();
                project.setProject(resultSet.getString(Const.PROJECT_NAME));

                projectList.add(project);
            }
        } catch (SQLException e) {
            System.out.println("Запрос на получение всех проектов не выполнен!");
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
        return projectList;
    }

    @Override
    public boolean exist(Project project) {// проверка на существование проекта
        Connection co = getConnection(pathDb);
        String query = "SELECT * FROM " + Const.PROJECTS_TABLE
                + " WHERE " + Const.PROJECT_NAME + "='" + project.getProject() + "';";
        Statement statement = null;
        boolean flag = false;
        try {
            statement = co.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                flag = true;
            }
        } catch (SQLException e) {
            System.out.println("Проверка на существование проекта " + project.getProject() + " не выполнена");
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
    public void remove(Project project) {
        Connection co = getConnection(pathDb);
        Statement statement = null;
        String query = "DELETE FROM " + Const.PROJECTS_TABLE + " WHERE " + Const.PROJECT_NAME + "='" + project.getProject() + "';";
        try {
            statement = co.createStatement();
            statement.executeUpdate(query);
            System.out.println("Проект " + project.getProject() + " удален.");
        } catch (SQLException e) {
            System.out.println("Запрос на удаление проекта " + project.getProject() + " не выполнен!");
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
