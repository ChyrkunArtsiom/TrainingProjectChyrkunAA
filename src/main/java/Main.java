import dao.AbstractDAO;
import dao.impl.RoleDAO;
import model.Entity;
import model.Role;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    public static void main(String[] args) {
        Logger logger = LogManager.getLogger(Main.class);
        logger.log(Level.ERROR, "testlog");
        Entity role = new Role("test");
        AbstractDAO roleDAO = new RoleDAO();
        roleDAO.create(role);
    }
}
