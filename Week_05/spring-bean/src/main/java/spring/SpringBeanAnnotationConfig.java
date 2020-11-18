package spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.entity.Student;


public class SpringBeanAnnotationConfig {

	public static void main(String[] args) {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("spring");

		Student student3 = (Student) context.getBean("student3");
		System.out.println(student3);

	}



}
