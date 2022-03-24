package cn.tedu.sp10;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * hystrix dashboard 一次只能监控一个服务实例，
 * 使用 turbine 可以汇集监控信息，
 * 将聚合后的信息提供给 hystrix dashboard 来集中展示和监控
 * */

@EnableTurbine
@EnableDiscoveryClient
@SpringBootApplication
public class Sp10TurbineApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sp10TurbineApplication.class, args);
    }

}
