import dao.AbstractDAO;
import dao.db.impl.ConnectionPoolImpl;
import dao.impl.RoleDAO;
import model.Entity;
import model.Role;

public class Main {

    public static void main(String[] args) {
        Entity role = new Role(1, "test5");
        AbstractDAO roleDAO = new RoleDAO();
        roleDAO.update(role);
        ConnectionPoolImpl.getInstance().shutdown();
    }
}
