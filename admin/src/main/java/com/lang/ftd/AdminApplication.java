package com.lang.ftd;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.servlet.MultipartConfigElement;
import java.util.Arrays;

//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@ServletComponentScan
@EnableTransactionManagement
@SpringBootApplication
@ComponentScan("com.lang.ftd")
@Slf4j
public class AdminApplication {

	public static void main(String[] args) {

        SpringApplication.run(AdminApplication.class, args);
    } 

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            log.debug("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
            	log.debug(beanName);
            }
        };
    }
    
    @Bean
    public MultipartConfigElement multipartConfigElement(){
	    MultipartConfigFactory config = new MultipartConfigFactory();
	    config.setMaxFileSize("80MB");
	    config.setMaxRequestSize("100MB");
	    return config.createMultipartConfig();
    } 

}
