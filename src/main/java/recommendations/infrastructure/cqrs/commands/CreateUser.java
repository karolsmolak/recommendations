package recommendations.infrastructure.cqrs.commands;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateUser implements ICommand {
    @Email
    private String email;

    @NotNull
    @Size(min = 3, max = 20)
    private String username;

    @NotNull
    @Size(min = 5, max = 20)
    private String password;

    public CreateUser() {}

    public CreateUser(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
}
