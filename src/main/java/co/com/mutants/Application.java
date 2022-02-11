package co.com.mutants;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan("co.com.mutants")
public class Application {

    @Value("${config.timezone:GMT-5}")
    private String timezone;

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone(timezone));
    }

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public OpenAPI customOpenAPI(@Value("${project.version}") String appVersion) {
        return new OpenAPI().components(new Components())
                .info(new Info().title("Mutants Rest Client").description("Mutants Rest Client").version(appVersion));
    }

}
