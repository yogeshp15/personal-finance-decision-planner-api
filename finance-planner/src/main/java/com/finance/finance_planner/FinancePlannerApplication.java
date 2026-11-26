package com.finance.finance_planner;

import org.springframework.context.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FinancePlannerApplication {

	public static void main(String[] args) {
		ApplicationContext context =  SpringApplication.run(FinancePlannerApplication.class, args);
		Test test = context.getBean(Test.class);
		System.out.println("Done");
		test.printMessage();
	}
}
