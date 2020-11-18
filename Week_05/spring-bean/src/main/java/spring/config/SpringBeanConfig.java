package spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.entity.Student;

@Configuration
public class SpringBeanConfig {

	@Bean(name = "student3")
	public Student getStudentBean() {
		Student student = new Student();
		student.setId(3);
		student.setName("yj3");
		return student;
	}


}
