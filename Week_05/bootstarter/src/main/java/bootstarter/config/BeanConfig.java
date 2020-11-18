package bootstarter.config;

import bootstarter.entity.School;
import bootstarter.entity.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

	@Bean(name = "student1")
	public Student student() {
		return new Student(1,"yj1");
	}

	@Bean(name = "school1")
	public School school() {
		School school = new School();
		school.setStudent(student());
		return school;
	}

}
