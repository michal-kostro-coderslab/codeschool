package pl.coderslab.workshop2.model;

import pl.coderslab.workshop2.bcrypt.BCrypt;

public class User {
    private int id;
    private String userName;
    private String email;
    private String password;
    private Group group;

    public User() {}

    public User(String userName, String email, String password, Group group) {
        this.userName = userName;
        this.email = email;
        this.hashPassword(password);
        this.group = group;
    }

    public void hashPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.hashPassword(password);
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}

