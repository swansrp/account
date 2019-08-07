/**
 * Title: AccountStartApplication.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-27 21:07
 * @description Project Name: Grote
 * @Package: com.srct.service.account
 */
package com.srct.service.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@ComponentScan(basePackages = "com.srct.service")
@SpringBootApplication
@MapperScan("com.srct.service.**.mapper")
@ServletComponentScan
@EnableTransactionManagement
public class AccountStartApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountStartApplication.class, args);
    }
}
