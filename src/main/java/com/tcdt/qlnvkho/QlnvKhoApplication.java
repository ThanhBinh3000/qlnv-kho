package com.tcdt.qlnvkho;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EntityScan(basePackages = { "com.tcdt.qlnvkho.entities", "com.tcdt.qlnvkho.table" })
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.tcdt.qlnvkho.service.feign")
public class QlnvKhoApplication {
	public static void main(String[] args) {
		SpringApplication.run(QlnvKhoApplication.class, args);
	}

}
