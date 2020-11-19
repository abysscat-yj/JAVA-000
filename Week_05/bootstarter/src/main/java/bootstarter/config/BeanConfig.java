package bootstarter.config;

import bootstarter.entity.School;
import bootstarter.entity.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class BeanConfig {

	@Bean(name = "student1")
	public Student student() {
		log.info("BeanConfig：初始化Bean:student1");
		return new Student(1,"yj1");
	}

	@Bean(name = "school1")
	public School school() {
		log.info("BeanConfig：初始化Bean:school1");
		School school = new School();
		school.setStudent(student());
		return school;
	}

}
