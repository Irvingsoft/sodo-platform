package cool.sodo.mysql.starter.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import cool.sodo.catkin.starter.annotation.EnableCatkinClient;
import cool.sodo.mysql.starter.generator.MybatisKeyGenerator;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * SpringBoot 自动开启事务管理，此处免去开启事务管理的配置
 * <br/>
 * 分页插件
 *
 * @author TimeChaser
 * @date 2020/11/6 11:19 上午
 */
@EnableCatkinClient
@MapperScan(value = {"cool.sodo.*.mapper", "cool.sodo.*.*.mapper"})
public class MybatisConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return mybatisPlusInterceptor;
    }

    @Bean
    @Primary
    public IdentifierGenerator idGenerator() {
        return new MybatisKeyGenerator();
    }
}
