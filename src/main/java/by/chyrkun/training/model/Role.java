package by.chyrkun.training.model;

import java.util.Objects;

/**
 * Class for role entity.
 */
public class Role extends Entity {
    /** An id. */
    private int id;

    /** A string of name. */
    private String name;

    /**
     * Constructor without and id.
     *
     * @param name the string of name
     */
    public Role(String name){
        this.name = name;
    }

    /**
     * Constructor with all fields.
     *
     * @param id   the id
     * @param name the string of name
     */
    public Role(int id, String name) {
        setId(id);
        setName(name);
    }

    /**
     * Constructor for creating a clone of other role.
     *
     * @param role the {@link Role} object of role
     */
    public Role(Role role) {
        setId(role.getId());
        setName(role.getName());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Role role = (Role) o;
        return id == role.id &&
                Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Role id: " + this.id + ", name: " + this.name;
    }
}
