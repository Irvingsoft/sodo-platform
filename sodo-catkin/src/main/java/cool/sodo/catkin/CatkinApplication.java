package cool.sodo.catkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = {"cool.sodo"})
@EnableEurekaClient
public class CatkinApplication {

    // TODO
    //  1. 重复注解分布式锁测试
    //  2. Catkin 开发
    public static void main(String[] args) {
        SpringApplication.run(CatkinApplication.class, args);
    }
}
