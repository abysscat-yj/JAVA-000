package outbootstarter;

import bootstarter.config.BeanConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(value = {BeanConfig.class})
public class BootStarterOutApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootStarterOutApplication.class, args);
    }

}
