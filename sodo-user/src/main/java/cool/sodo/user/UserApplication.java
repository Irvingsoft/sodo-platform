package cool.sodo.user;

import cool.sodo.catkin.starter.annotation.EnableCatkinClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = {"cool.sodo"})
@EnableEurekaClient
@EnableCatkinClient
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
