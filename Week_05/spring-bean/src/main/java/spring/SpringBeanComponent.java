package spring;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.entity.Student;

public class SpringBeanComponent {


	public static void main(String[] args) {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("spring");

		Student student4 = (Student) context.getBean("student4");
		System.out.println(student4);
	}




}
