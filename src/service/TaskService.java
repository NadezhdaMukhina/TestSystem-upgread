package service;

import dao.TaskDAO;
import dataBase.Const;
import dataBase.DataBaseHandler;
import entity.Task;

import java.sql.*;
import java.util.ArrayList;

public class TaskService extends DataBaseHandler implements TaskDAO {

    @Override
    public void create() {
        Connection co = getConnection(pathDb);
        Statement statement = null;
        String query = "CREATE TABLE IF NOT EXISTS " + Const.TASK_TABLE + " ('"
                + Const.TASK_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '"
                + Const.PROJECT_NAME + "' VARCHAR(50), '"
                + Const.TOPIC + "' VARCHAR(50), '"
                + Const.TYPE + "' VARCHAR(50), '"
                + Const.PRIORITY + "' INTEGER, '"
                + Const.USER_NAME + "' VARCHAR(50), '"
                + Const.DESCRIPTION + "' TEXT);";
        try {
            statement = co.createStatement();
            statement.executeUpdate(query);
            System.out.println("Создана новая таблица Задачи.");
        } catch (SQLException e) {
            System.out.println("Ошибка создания таблицы Задачи!");
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
    public void add(Task task) { // добавление (Запись) задачи в БД
        Connection co = getConnection(pathDb);
        PreparedStatement preparedStatement = null;
        String query = "INSERT INTO " + Const.TASK_TABLE + " ("
                + Const.PROJECT_NAME + ", "
                + Const.TOPIC + ", "
                + Const.TYPE + ", "
                + Const.PRIORITY + ", "
                + Const.USER_NAME + ", "
                + Const.DESCRIPTION + ") VALUES(?, ?, ?, ?, ?, ?);";
        try {
            preparedStatement = co.prepareStatement(query);
            preparedStatement.setString(1, task.getProject());
            preparedStatement.setString(2, task.getTopic());
            preparedStatement.setString(3, task.getType());
            preparedStatement.setInt(4, task.getPriority());
            preparedStatement.setString(5, task.getUser());
            preparedStatement.setString(6, task.getDescription());
            preparedStatement.executeUpdate();
            System.out.println("Добавление новой задачи по проекту " + task.getProject()
                    + ", назначенной  на пользователя " + task.getUser() + " выполнено.");
        } catch (SQLException e) {
            System.out.println("Запрос на добавление новой задачи не выполнен!");
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
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
    public ArrayList<Task> getByProject(String project) {// получить список всех задач в проекте
        return getByUserOrProject(Const.PROJECT_NAME, project);
    }


    @Override
    public ArrayList<Task> getByUser(String user) {// получить список всех задач, назначенных на конкретного исполнителя
        return getByUserOrProject(Const.USER_NAME, user);
    }

    @Override
    public boolean existTask(Task task) {
        Connection co = getConnection(pathDb);
        PreparedStatement preparedStatement = null;
        boolean flag = false;
        String query = "SELECT * FROM " + Const.TASK_TABLE + " WHERE "
                + Const.PROJECT_NAME + "=? AND "
                + Const.TOPIC + "=? AND "
                + Const.TYPE + "=? AND "
                + Const.USER_NAME + "=? AND "
                + Const.DESCRIPTION + "=?;";
        try {
            preparedStatement = co.prepareStatement(query);
            preparedStatement.setString(1, task.getProject());
            preparedStatement.setString(2, task.getTopic());
            preparedStatement.setString(3, task.getType());
            preparedStatement.setString(4, task.getUser());
            preparedStatement.setString(5, task.getDescription());

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                flag = true;
            }
        } catch (SQLException e) {
            System.out.println("Проверка на существование задачи не выполнена");
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
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
    public int getMaxPriority(String project) {// поиск максимального значения приоритета по проекту
        Connection co = getConnection(pathDb);
        int max = 0;
        String query = "SELECT MAX (" + Const.PRIORITY + ") FROM " + Const.TASK_TABLE
                + " WHERE " + Const.PROJECT_NAME + "='" + project + "';";

        Statement statement = null;
        try {
            statement = co.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            max = resultSet.getInt(1);
        } catch (SQLException e) {
            System.out.println("Поиск максимального значения приоритета по проекту не выполнен!");
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
        return max;
    }

    @Override
    public boolean existPriority(String project, int priority) {// поиск приоритета по проекту
        Connection co = getConnection(pathDb);
        boolean flag = false;
        String query = "SELECT * FROM " + Const.TASK_TABLE + " WHERE "
                + Const.PROJECT_NAME + "='" + project + "' AND "
                + Const.PRIORITY + "='" + priority + "';";

        Statement statement = null;
        try {
            statement = co.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                flag = true;
            }
        } catch (SQLException e) {
            System.out.println("Проверка на существование приоритета по проекту " + project + " не выполнена");
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

    public ArrayList<Task> getByUserOrProject(String column, String value) {
        Connection co = getConnection(pathDb);
        PreparedStatement preparedStatement = null;
        ArrayList<Task> taskList = new ArrayList<>();
        String query = "SELECT * FROM " + Const.TASK_TABLE + " WHERE "
                + column + "=?  ORDER BY " + Const.PRIORITY + ";";
        try {
            preparedStatement = co.prepareStatement(query);
            preparedStatement.setString(1, value);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getInt(Const.TASK_ID));
                task.setProject(resultSet.getString(Const.PROJECT_NAME));
                task.setTopic(resultSet.getString(Const.TOPIC));
                task.setType(resultSet.getString(Const.TYPE));
                task.setPriority(resultSet.getInt(Const.PRIORITY));
                task.setUser(resultSet.getString(Const.USER_NAME));
                task.setDescription(resultSet.getString(Const.DESCRIPTION));

                taskList.add(task);
            }

        } catch (SQLException e) {
            System.out.println("Запрос на получение списка задач не выполнен!");
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (co != null) {
                    co.close();
                }
            } catch (SQLException e) {
                System.out.println("Ошибка закрытия базы данных!");
            }
        }
        return taskList;
    }

    @Override
    public void remove(Task task) {// удалить задачу
        Connection co = getConnection(pathDb);
        PreparedStatement preparedStatement = null;
        String query = "DELETE FROM " + Const.TASK_TABLE + " WHERE "
                + Const.PROJECT_NAME + "=? AND "
                + Const.TOPIC + "=? AND "
                + Const.TYPE + "=? AND "
                + Const.PRIORITY + "=? AND "
                + Const.USER_NAME + "=? AND "
                + Const.DESCRIPTION + "=?;";
        try {
            preparedStatement = co.prepareStatement(query);
            preparedStatement.setString(1, task.getProject());
            preparedStatement.setString(2, task.getTopic());
            preparedStatement.setString(3, task.getType());
            preparedStatement.setInt(4, task.getPriority());
            preparedStatement.setString(5, task.getUser());
            preparedStatement.setString(6, task.getDescription());

            preparedStatement.executeUpdate();
            System.out.println("Задача по проекту " + task.getProject()
                    + ", назначенная  на пользователя " + task.getUser() + ", удалена.");
        } catch (SQLException e) {
            System.out.println("Удаление задачи по проекту " + task.getProject()
                    + ", назначенной  на пользователя " + task.getUser() + " не выполнено!");
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
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
