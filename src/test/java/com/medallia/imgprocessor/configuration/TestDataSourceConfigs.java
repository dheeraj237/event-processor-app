package com.medallia.imgprocessor.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author: dheeraj.suthar
 */
@Configuration
public class TestDataSourceConfigs {


	@Bean
	@Qualifier("test1Prop")
	@ConfigurationProperties(prefix = "spring.datasource.test1")
	public DataSourceProperties test1Prop() {
		return new DataSourceProperties();
	}

	@Bean
	@Qualifier("test2Prop")
	@ConfigurationProperties(prefix = "spring.datasource.test2")
	public DataSourceProperties test2Prop() {
		return new DataSourceProperties();
	}

}
