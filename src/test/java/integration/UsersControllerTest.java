package integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import recommendations.api.Application;
import recommendations.api.Controllers.UsersController;
import recommendations.core.domain.User;
import recommendations.infrastructure.services.IUserService;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class UsersControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private UsersController controller;

    @Autowired
    private IUserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void givenValidEmailUserShouldExist() throws Exception {
        mockMvc.perform(get("/users/user1@mail")).andExpect(status().isOk());
    }

    @Test
    public void givenNonExistingEmailShouldntFindUser() throws Exception {
        mockMvc.perform(get("/users/badaddress@mail")).andExpect(status().isNotFound());
    }

    @Test
    public void registeringExistingUserShouldThrowException() throws Exception {
        User user = new User("user1@mail", "asd", "sad", 40);
        this.mockMvc.perform(post("/users/").contentType(contentType).content(json(user))).andExpect(status().isConflict());
    }

    @Test
    public void registeringNonExistingUserShouldSucceed() throws Exception {
        User user = new User("nonexisting@mail", "asd", "sad", 40);
        this.mockMvc.perform(post("/users/").contentType(contentType).content(json(user))).andExpect(status().isCreated());
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

}
