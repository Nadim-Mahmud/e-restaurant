package net.therap.estaurant.command;

import net.therap.estaurant.util.Encryption;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * @author nadimmahmud
 * @since 1/23/23
 */
public class Password {

    private String storedUserPassword;

    @NotNull(message = "{input.text}")
    @Size(min = 1, max = 45, message = "{input.text}")
    private String oldPassword;

    @NotNull(message = "{input.text}")
    @Size(min = 1, max = 45, message = "{input.text}")
    private String newPassword;

    @NotNull(message = "{input.text}")
    @Size(min = 1, max = 45, message = "{input.text}")
    private String confirmPassword;

    public String getStoredUserPassword() {
        return storedUserPassword;
    }

    public void setStoredUserPassword(String storedUserPassword) {
        this.storedUserPassword = storedUserPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.confirmPassword = confirmPassword;
    }
}
