package cn.tedu.sp08;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@EnableHystrixDashboard //Hystrix dashboard 仪表盘
@EnableDiscoveryClient //让注册中心发现
@SpringBootApplication

//访问url： http://localhost:4001/hystrix
public class Sp08HystrixDashboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sp08HystrixDashboardApplication.class, args);
    }

}
