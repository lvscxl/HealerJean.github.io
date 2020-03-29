package com.healerjean.proj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class _05_ShardingTable_ReadWrite_Application {

    public static void main(String[] args) {
        SpringApplication.run(_05_ShardingTable_ReadWrite_Application.class, args);
    }

}
