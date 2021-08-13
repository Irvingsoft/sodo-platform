package cool.sodo.housekeeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = {"cool.sodo"})
@EnableEurekaClient
public class HousekeeperApplication {

    public static void main(String[] args) {
        SpringApplication.run(HousekeeperApplication.class, args);
    }
}
