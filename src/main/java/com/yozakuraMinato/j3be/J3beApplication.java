package com.yozakuraMinato.j3be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class J3beApplication {

	static void main(String[] args) {
		SpringApplication.run(J3beApplication.class, args);
		IO.println("""
				
				||==================================||
				|| APPLICATION STARTED SUCCESSFULLY ||
				||==================================||
				|| Application: J3BE                ||
				|| Running URL: localhost:8080      ||
				|| Environment: Development         ||
				||==================================||
				""");
	}

}
