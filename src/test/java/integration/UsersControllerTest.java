package integration;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import recommendations.api.Controllers.UsersController;
import recommendations.infrastructure.command.CreateUser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UsersControllerTest extends ControllerTestBase {

    @Autowired
    UsersController controller;

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void givenValidUsernameShouldExist() throws Exception {
        mockMvc.perform(get("/users/user1")).andExpect(status().isOk());
    }

    @Test
    public void givenNonExistingUsernameShouldntFindUser() throws Exception {
        mockMvc.perform(get("/users/nonexistinguser")).andExpect(status().isNotFound());
    }

    @Test
    public void registeringExistingUserShouldThrowException() throws Exception {
        CreateUser createUser = new CreateUser("user1@mail.com", "user1", "user1");
        this.mockMvc.perform(post("/users/").contentType(contentType).content(json(createUser))).andExpect(status().isConflict());
    }

    @Test
    public void registeringNonExistingUserShouldSucceed() throws Exception {
        CreateUser createUser = new CreateUser("nonexisting1@mail.com", "nonexisting", "user1");
        this.mockMvc.perform(post("/users/").contentType(contentType).content(json(createUser))).andExpect(status().isCreated());
    }

}
