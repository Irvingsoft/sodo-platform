package cool.sodo.housekeeper;

import cool.sodo.catkin.starter.annotation.EnableCatkinClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"cool.sodo"})
@EnableEurekaClient
@EnableCatkinClient
@EnableScheduling
public class HousekeeperApplication {

    public static void main(String[] args) {
        SpringApplication.run(HousekeeperApplication.class, args);
    }
}
