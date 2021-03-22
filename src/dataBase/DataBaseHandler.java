package dataBase;

import service.ProjectService;
import service.TaskService;
import service.UserService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseHandler {

    protected static String pathDb;

    public Connection getConnection(String pathDb) {
        Connection co = null;
        try {
            Class.forName("org.sqlite.JDBC");
            co = DriverManager.getConnection("jdbc:sqlite:" + pathDb);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Connection ERROR");
        }
        return co;
    }

    public void setPathDb(String path) {
        pathDb = path;
    }

    public void createTables(){
        UserService userService = new UserService();
        ProjectService projectService = new ProjectService();
        TaskService taskService = new TaskService();

            userService.create();
            projectService.create();
            taskService.create();

    }
}
