package net.therap.estaurant.validator;

import net.therap.estaurant.command.Password;
import net.therap.estaurant.util.Encryption;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * @author nadimmahmud
 * @since 1/25/23
 */
public class PasswordValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Password.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Password password = (Password) target;

        if(!password.getNewPassword().equals(password.getConfirmPassword())){
            errors.rejectValue("newPassword", "password.different");
            errors.rejectValue("confirmPassword", "password.different");
        }

        try {
            if(!password.getStoredUserPassword().equals(Encryption.getPBKDF2(password.getOldPassword()))){
                errors.rejectValue("oldPassword", "password.incorrectOldPass");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
