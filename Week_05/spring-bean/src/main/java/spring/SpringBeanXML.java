package spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.entity.Klass;
import spring.entity.Student;

public class SpringBeanXML {


	public static void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

		Student student1 = (Student) context.getBean("student1");
		System.out.println(student1.toString());

		Student student2 = (Student) context.getBean("student2");
		System.out.println(student2.toString());

		Klass class1 = (Klass) context.getBean("class1");
		System.out.println(class1.toString());

	}


}
