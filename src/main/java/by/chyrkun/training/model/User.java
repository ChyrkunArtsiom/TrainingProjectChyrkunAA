package by.chyrkun.training.model;

import java.util.Objects;

public class User extends Entity {
    private int id;
    private String login;
    private String password;
    private String firstname;
    private String secondname;
    private Role role;

    public User(String login, String password, String firstname, String secondname, Role role) {
        setLogin(login);
        setPassword(password);
        setFirstname(firstname);
        setSecondname(secondname);
        setRole(role);
    }

    public User(int id, String login, String password, String firstname, String secondname, Role role) {
        setId(id);
        setLogin(login);
        setPassword(password);
        setFirstname(firstname);
        setSecondname(secondname);
        setRole(role);
    }

    public User(User user){
        setId(user.getId());
        setLogin(user.getLogin());
        setPassword(user.getPassword());
        setFirstname(user.getFirstname());
        setSecondname(user.getSecondname());
        setRole(user.getRole());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSecondname() {
        return secondname;
    }

    public void setSecondname(String secondname) {
        this.secondname = secondname;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = new Role(role);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        User user = (User) o;
        return id == user.id &&
                Objects.equals(login, user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login);
    }

    @Override
    public String toString() {
        return "User id: " + this.id + ", login: " + this.login + ", firstname: " +
                this.firstname + ", secondname: " + this.secondname + ", role: " + this.role.getName();
    }
}
