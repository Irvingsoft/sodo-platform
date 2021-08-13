package cool.sodo.zuul.config;

import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置全局的接口文档
 *
 * @author TimeChaser
 * @date 2021/5/26 17:50
 */
@Configuration
@Primary
public class DocumentationConfig implements SwaggerResourcesProvider {

    private final RouteLocator routeLocator;

    public DocumentationConfig(RouteLocator routeLocator) {
        this.routeLocator = routeLocator;
    }

    /**
     * 根据配置文件中的服务列表，添加其它服务的接口文档资源
     *
     * @return java.util.List<springfox.documentation.swagger.web.SwaggerResource>
     */
    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        List<Route> routes = routeLocator.getRoutes();
        routes.forEach(route -> resources.add(swaggerResource(route.getId()
                , route.getFullPath().replace("**", "v2/api-docs")
                , "2.0")));
        return resources;
    }

    /**
     * 根据其他服务的信息生成对应的接口文档信息
     *
     * @param name     服务的 ID
     * @param location 服务的路径
     * @param version  服务的接口文档版本
     * @return springfox.documentation.swagger.web.SwaggerResource
     */
    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }
}
