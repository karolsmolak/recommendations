package recommendations.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import recommendations.infrastructure.utils.MoviePopulator;
import recommendations.infrastructure.utils.UserPopulator;

@SpringBootApplication
@ComponentScan("recommendations")
public class Application {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = SpringApplication.run(Application.class, args);
        //context.getBean(MoviePopulator.class).populate();
        //context.getBean(UserPopulator.class).populate();
    }

}