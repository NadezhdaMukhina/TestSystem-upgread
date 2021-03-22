package dao;

import entity.Task;

import java.util.ArrayList;

public interface TaskDAO {
    //create
    void create();

    void add(Task task);

    //read

    ArrayList<Task> getByProject(String project);

    ArrayList<Task> getByUser(String user);

    boolean existTask(Task task);

    int getMaxPriority(String project);

    boolean existPriority(String project, int priority);

    //delete
    void remove(Task task);
}
