package config;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import recommendations.api.Application;
import recommendations.core.repositories.IMovieRepository;
import recommendations.core.repositories.IUserRepository;
import recommendations.infrastructure.repositories.InMemoryMovieRepository;
import recommendations.infrastructure.repositories.InMemoryUserRepository;

@SpringBootConfiguration
@ComponentScan(basePackages = "recommendations")
@Profile("test")
public class TestConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public Logger logger(){
        return LogManager.getLogger(Application.class);
    }

    @Bean
    public IMovieRepository movieRepository() {
        return new InMemoryMovieRepository();
    }

    @Bean
    public IUserRepository userRepository() {
        return new InMemoryUserRepository();
    }

}