package integration;

import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MovieControllerTest extends ControllerTestBase {

    @Test
    public void givenValidIdMovieShouldExist() throws Exception {
        mockMvc.perform(get("/movies/1")).andExpect(status().isOk());
    }

    @Test
    public void givenInvalidIdShouldReturnNoContent() throws Exception {
        mockMvc.perform(get("/movies/3")).andExpect(status().isNotFound());
    }
}
