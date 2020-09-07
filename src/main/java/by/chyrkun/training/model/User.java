package by.chyrkun.training.model;

import java.util.Objects;

/**
 * Class for user entity.
 */
public class User extends Entity {
    /** An id. */
    private int id;

    /** A string of login. */
    private String login;

    /** An array of bytes of password. */
    private byte[] password;

    /** A string of first name. */
    private String firstname;

    /** A string of second name. */
    private String secondname;

    /** A {@link Role} object of role. */
    private Role role;

    /**
     * Constructor with all fields.
     *
     * @param id         the id
     * @param login      the string of login
     * @param password   the array of bytes of password
     * @param firstname  the string of first name
     * @param secondname the string of second name
     * @param role       the {@link Role} object of role
     */
    public User(int id, String login, byte[] password, String firstname, String secondname, Role role) {
        setId(id);
        setLogin(login);
        setPassword(password);
        setFirstname(firstname);
        setSecondname(secondname);
        setRole(role);
    }

    /**
     * Constructor without id.
     *
     * @param login      the string of login
     * @param password   the string of password
     * @param firstname  the string of first name
     * @param secondname the string of second name
     * @param role       the {@link Role} object of role
     */
    public User(String login, byte[] password, String firstname, String secondname, Role role) {
        setLogin(login);
        setPassword(password);
        setFirstname(firstname);
        setSecondname(secondname);
        setRole(role);
    }

    /**
     * Constructor without id and role.
     *
     * @param login      the string of login
     * @param password   the string of password
     * @param firstname  the string of first name
     * @param secondname the string of second name
     */
    public User(String login, byte[] password, String firstname, String secondname) {
        setLogin(login);
        setPassword(password);
        setFirstname(firstname);
        setSecondname(secondname);
    }

    /**
     * Constructor password.
     *
     * @param id         the id
     * @param login      the string of login
     * @param firstname  the string of first name
     * @param secondname the string of second name
     * @param role       the {@link Role} object of role
     */
    public User(int id, String login, String firstname, String secondname, Role role) {
        setId(id);
        setLogin(login);
        setFirstname(firstname);
        setSecondname(secondname);
        setRole(role);
    }

    /**
     * Constructor without password and role
     *
     * @param id         the id
     * @param firstname  the string of first name
     * @param secondname the string of second name
     */
    public User(int id, String firstname, String secondname) {
        setId(id);
        setFirstname(firstname);
        setSecondname(secondname);
    }

    /**
     * Constructor for creating a clone of other user.
     *
     * @param user the {@link User} object of user
     */
    public User(User user) {
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

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
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
        if (this == o) {
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
                this.firstname + ", secondname: " + this.secondname + (this.role != null ? ", role: " + this.role.toString() : "");
    }
}
