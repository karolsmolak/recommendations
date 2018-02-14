package integration;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import recommendations.api.Controllers.LoginController;
import recommendations.infrastructure.cqrs.commands.Login;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LoginControllerTest extends ControllerTestBase {
    @Autowired
    private LoginController controller;

    @Test
    public void loginValidCredentials() throws Exception {
        Login login = new Login("user1", "user1");
        mockMvc.perform(post("/login/").contentType(contentType).content(json(login))).andExpect(status().isOk());
    }

    @Test
    public void loginInvalidCredentials() throws Exception {
        Login login = new Login("user1", "user2");
        mockMvc.perform(post("/login/").contentType(contentType).content(json(login))).andExpect(status().isUnauthorized());

        login = new Login("user2", "user1");
        mockMvc.perform(post("/login/").contentType(contentType).content(json(login))).andExpect(status().isUnauthorized());
    }

}
