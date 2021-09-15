package cool.sodo.mysql.starter.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * SpringBoot 自动开启事务管理，此处免去开启事务管理的配置
 * <br/>
 * 分页插件
 *
 * @author TimeChaser
 * @date 2020/11/6 11:19 上午
 */
@Configuration(proxyBeanMethods = false)
@MapperScan(value = {"cool.sodo.*.mapper", "cool.sodo.*.*.mapper"})
@Slf4j
public class MybatisConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return mybatisPlusInterceptor;
    }

    @PostConstruct
    public void log() {
        log.info("sodo-mybatis-starter 装载成功！");
    }
}
