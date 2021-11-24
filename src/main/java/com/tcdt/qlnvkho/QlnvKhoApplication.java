package com.tcdt.qlnvkho;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EntityScan(basePackages = { "com.tcdt.qlnvkho.entities", "com.tcdt.qlnvkho.table" })
public class QlnvKhoApplication {
	public static void main(String[] args) {
		SpringApplication.run(QlnvKhoApplication.class, args);
	}

}
