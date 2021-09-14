package cool.sodo.catkin.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = {"cool.sodo"})
@EnableEurekaClient
public class CatkinApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatkinApplication.class, args);
    }
}
