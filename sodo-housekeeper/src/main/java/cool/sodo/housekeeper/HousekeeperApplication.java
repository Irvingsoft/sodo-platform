package cool.sodo.housekeeper;

import cool.sodo.catkin.starter.annotation.EnableCatkinClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = {"cool.sodo"})
@EnableEurekaClient
@EnableCatkinClient
public class HousekeeperApplication {

    public static void main(String[] args) {
        SpringApplication.run(HousekeeperApplication.class, args);
    }
}
