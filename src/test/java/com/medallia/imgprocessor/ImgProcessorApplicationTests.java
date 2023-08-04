package com.medallia.imgprocessor;

import com.medallia.imgprocessor.configuration.ApplicationConfiguration;
import com.medallia.imgprocessor.configuration.DynamicDataSourceConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class ImgProcessorApplicationTests {

	@Autowired
	private DataSource dataSource;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	protected DataSourceProperties dataSourceProperties;
	@Autowired
	@Qualifier("altDataSourceProperties")
	protected DataSourceProperties altDataSourceProperties;

	@Autowired
	private ApplicationConfiguration applicationConfiguration;
	@Autowired
	private DynamicDataSourceConfig dynamicDataSourceConfig;

	@Test
	void contextLoads() {
		Assertions.assertNotNull(dataSource);
		Assertions.assertNotNull(restTemplate);
		Assertions.assertNotNull(jmsTemplate);
		Assertions.assertNotNull(dataSourceProperties);
		Assertions.assertNotNull(altDataSourceProperties);
	}

	@Test void configurationLoads() {
		Assertions.assertNotNull(applicationConfiguration);
		Assertions.assertNotNull(dynamicDataSourceConfig);
	}

}
