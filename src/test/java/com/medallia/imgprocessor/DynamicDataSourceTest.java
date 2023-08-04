package com.medallia.imgprocessor;

import com.medallia.imgprocessor.configuration.DynamicDataSourceConfig;
import com.medallia.imgprocessor.exception.DataSourceNotFoundException;
import com.medallia.imgprocessor.model.ExternalDataSourceDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
public class DynamicDataSourceTest {

	public static final String CREATE_TEST_TABLE = "CREATE TABLE test_table (id INT PRIMARY KEY, name VARCHAR(255))";
	public static final String SELECT_COUNT_ROW = "SELECT COUNT(*) FROM test_table";
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private DynamicDataSourceConfig dynamicDataSourceConfig;

	@Autowired
	@Qualifier("test1Prop")
	private DataSourceProperties test1Prop;

	@Autowired
	@Qualifier("test2Prop")
	private DataSourceProperties test2Prop;

	@Test
	public void testDynamicDataSource() throws SQLException, DataSourceNotFoundException {
		// Set the data source key to "test1"
		ExternalDataSourceDTO mockSource = new ExternalDataSourceDTO();
		mockSource.setDriverClass(test1Prop.getDriverClassName());
		mockSource.setHost(test1Prop.getUrl());
		mockSource.setUser(test1Prop.getUsername());
		mockSource.setPassword(test1Prop.getPassword());
		dynamicDataSourceConfig.setCurrentDataSource(mockSource);
		// Perform database operations on the first test datasource
		jdbcTemplate.execute(CREATE_TEST_TABLE);
		jdbcTemplate.execute("INSERT INTO test_table (id, name) VALUES (1, 'Test Data')");
		// Clear the data source key to avoid affecting other tests
		dynamicDataSourceConfig.clearDataSource();

		// Set the data source key to "test2"
		ExternalDataSourceDTO mockSource1 = new ExternalDataSourceDTO();
		mockSource1.setDriverClass(test2Prop.getDriverClassName());
		mockSource1.setHost(test2Prop.getUrl());
		mockSource1.setUser(test2Prop.getUsername());
		mockSource1.setPassword(test2Prop.getPassword());
		dynamicDataSourceConfig.setCurrentDataSource(mockSource1);
		// Perform database operations on the second test datasource
		jdbcTemplate.execute(CREATE_TEST_TABLE);
		jdbcTemplate.execute("INSERT INTO test_table (id, name) VALUES (1, 'Test Data')");
		jdbcTemplate.execute("INSERT INTO test_table (id, name) VALUES (2, 'Test Data')");
		dynamicDataSourceConfig.clearDataSource();

		// Verify that the data has been saved to the second test datasource
		dynamicDataSourceConfig.setCurrentDataSource(mockSource);
		int rowCount = jdbcTemplate.queryForObject(SELECT_COUNT_ROW, Integer.class);
		// in test 1 we have inserted two rows | test 2 has 1 row only
		assertThat(rowCount).isEqualTo(1);
	}
}
