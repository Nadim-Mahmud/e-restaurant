package net.therap.estaurant.command;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author nadimmahmud
 * @since 1/26/23
 */
public class Credentials {

    @NotNull(message = "{input.text}")
    @Size(min = 1, max = 50, message = "{input.text}")
    @Email
    private String email;

    @NotNull(message = "{input.text}")
    @Size(min = 1, max = 50, message = "{input.text}")
    private String password;

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
        this.password = password;
    }
}