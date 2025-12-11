package com.zjgsu.hx.quizserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class QuizServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizServerApplication.class, args);
	}

}
