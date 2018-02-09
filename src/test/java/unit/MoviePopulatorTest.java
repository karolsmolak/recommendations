package unit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import recommendations.api.Application;
import recommendations.infrastructure.utils.MoviePopulator;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class MoviePopulatorTest {

    @Autowired
    ApplicationContext context;

    @Test
    public void testPopulate() throws Exception {
        MoviePopulator populator = context.getBean(MoviePopulator.class);
        populator.populate();
    }
}
