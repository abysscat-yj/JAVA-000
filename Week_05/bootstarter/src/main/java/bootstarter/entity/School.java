package bootstarter.entity;

import lombok.Data;
import javax.annotation.Resource;

@Data
public class School {
    
    @Resource(name = "student1")
    Student student;
    
}
