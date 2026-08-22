package com.yozakuraMinato.j3be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class J3beApplication {

	static void main(String[] args) {
		SpringApplication.run(J3beApplication.class, args);
		IO.println("""
				
				||=======================================||
				|| J3BE APPLICATION STARTED SUCCESSFULLY ||
				||=======================================||
				|| Running Mode: Development             ||
				|| API Base URL: http://localhost:8080   ||
				||=======================================||
				""");
	}

}
