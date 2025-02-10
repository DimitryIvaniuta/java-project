package com.javaproject.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.javaproject.server")
@EnableTransactionManagement
public class JavaProjectServerApplication {

    /**
     * JavaProject Server Application.
     * @param args command line arguments.
     */
    public static void main(final String[] args) {
        SpringApplication.run(com.javaproject.server.JavaProjectServerApplication.class, args);
    }

}
