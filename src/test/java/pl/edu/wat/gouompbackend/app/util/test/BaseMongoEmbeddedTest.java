package pl.edu.wat.gouompbackend.app.util.test;

import lombok.experimental.FieldDefaults;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static lombok.AccessLevel.PRIVATE;

@SpringBootTest
@RunWith(SpringRunner.class)
@FieldDefaults(level = PRIVATE)
public abstract class BaseMongoEmbeddedTest {

	@Autowired
	MongoTemplate mongoTemplate;
	@Autowired
	ApplicationContext springContext;
	@Autowired
	protected BCryptPasswordEncoder passwordEncoder;

	@Before
	public void clearDatabase() {
		mongoTemplate.getDb().drop();
	}

	protected Object getBean(Class<?> beanClass) {
		return springContext.getAutowireCapableBeanFactory().getBean(beanClass);
	}

}
