package dao;

import entity.Project;

import java.util.ArrayList;

public interface ProjectDAO {
    //create
    void create();

    void add(Project project);

    //read
    ArrayList<Project> getAll();

    boolean exist(Project project);

    //delete
    void remove(Project project);
}
