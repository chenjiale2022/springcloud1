package cn.tedu.sp11;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * zuul API网关，为微服务应用提供统一的对外访问接口
 * zuul还提供了过滤器，对所有微服务提供统一的请求校验
 *
 * zuul集成了ribbon，默认已经实现了负载均衡
 * 添加retry依赖，配置参数，实现ribbon重试
 *
 * */
@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class Sp11ZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sp11ZuulApplication.class, args);
    }

}
