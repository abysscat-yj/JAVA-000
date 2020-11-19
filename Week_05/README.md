### 1. 写代码实现 Spring Bean 的装配，方式越多越好（XML、Annotation 都可以）
- 方式一：通过ClassPathXmlApplicationContext读取applicationContext.xml文件
- 方式二：通过AnnotationConfigApplicationContext扫描@Configuration类中的@Bean
- 方式三：通过AnnotationConfigApplicationContext扫描@Component

</br>

### 2. 给 Student/Klass/School 实现自动配置和 Starter
创建了两个module：boot-starter 和 springboot1；其中boot-starter是自定义starter，springboot1用来引入boot-starter进行跨项目自动配置加载
- 方式一：`spring.factories`
- 方式二：`@Import(value = {BeanConfig.class})`：在不同Application启动类目录下本身是扫描不到BeanConfig的，这时可以在BootStarterOutApplication加入@Import注解手动引入

</br>

### 3. 研究一下 JDBC 接口和数据库连接池，掌握它们的设计和用法：
- 1）使用 JDBC 原生接口，实现数据库的增删改查操作
- 2）使用事务，PrepareStatement 方式，批处理方式，改进上述操作
- 3）配置 Hikari 连接池，改进上述操作。提交代码到 Github
