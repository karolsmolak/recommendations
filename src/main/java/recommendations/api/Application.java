package recommendations.api;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import recommendations.core.repositories.IMovieRepository;
import recommendations.core.repositories.IUserRepository;
import recommendations.infrastructure.repositories.InMemoryMovieRepository;
import recommendations.infrastructure.repositories.InMemoryUserRepository;
import recommendations.infrastructure.utils.MoviePopulator;
import recommendations.infrastructure.utils.UserPopulator;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
@PropertySource("classpath:application.properties")
@ComponentScan("recommendations")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("GithubLookup-");
        executor.initialize();
        return executor;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public IMovieRepository movieRepository() throws Exception {
        IMovieRepository bean = new InMemoryMovieRepository();
        new MoviePopulator(bean).populate();
        return bean;
    }

    @Bean
    public IUserRepository userRepository(IMovieRepository movieRepository) throws Exception {
        IUserRepository userRepository = new InMemoryUserRepository();

        if(movieRepository == null) System.out.println("DDS");
        new UserPopulator(userRepository, movieRepository).populate();

        return userRepository;
    }


}