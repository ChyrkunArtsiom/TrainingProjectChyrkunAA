package by.chyrkun.training.service.util;

import by.chyrkun.training.service.exception.UncheckedServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ResourceBundle;

/**
 * This class creates hashcode for String-type passwords.
 */
public class PasswordHasher {
    /** Field for logging. */
    private final static Logger LOGGER = LogManager.getLogger(PasswordHasher.class);

    /**
     * Get hash byte [ ].
     *
     * @param password the password string
     * @return the hashcode of password
     */
    public static byte[] getHash(String password) {
        byte[] hash;
        byte[] salt = getSalt();
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);

        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hash = factory.generateSecret(spec).getEncoded();
            return hash;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            LOGGER.log(Level.FATAL, "Exception in hash method");
            throw new UncheckedServiceException("Exception in hash method", ex);
        }
    }

    /**
     * Returns salt for creating hashcode from property file.
     *
     * @return the salt
     */
    private static byte[] getSalt(){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("application");
        return resourceBundle.getString("salt").getBytes();
    }
}
