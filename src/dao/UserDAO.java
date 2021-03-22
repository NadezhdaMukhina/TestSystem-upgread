package dao;

import entity.User;

import java.util.ArrayList;

public interface UserDAO {
    //create
    void create();

    void add(User user);

    //read
    ArrayList<User> getAll();

    boolean exist(User user);

    //delete
    void remove(User user);
}
