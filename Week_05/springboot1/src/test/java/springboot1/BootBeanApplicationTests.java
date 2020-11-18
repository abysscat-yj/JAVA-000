package springboot1;

import bootstarter.entity.School;
import bootstarter.entity.Student;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BootBeanApplicationTests {

	@Autowired
	@Qualifier("school1")
	private School school;

	@Autowired
	@Qualifier("student1")
	private Student student;

	@Test
	public void contextLoads() {
		System.out.println(school);
		System.out.println(student);
	}

}
