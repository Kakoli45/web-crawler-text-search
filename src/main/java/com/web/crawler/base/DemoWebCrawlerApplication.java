package com.web.crawler.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "com.web" })
@EnableConfigurationProperties
@SpringBootApplication
public class DemoWebCrawlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoWebCrawlerApplication.class, args);
	}

}
