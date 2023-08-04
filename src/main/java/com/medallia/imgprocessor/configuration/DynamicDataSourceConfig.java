package com.medallia.imgprocessor.configuration;

import com.medallia.imgprocessor.exception.DataSourceNotFoundException;
import com.medallia.imgprocessor.exception.DataSourceResolvingException;
import com.medallia.imgprocessor.model.ExternalDataSourceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.String.format;

/**
 * Custom dynamic data source router with current data source context holder.
 * @author: dheeraj.suthar
 */
// OSCAR: Other ways to load and save data into databases. (load entity manage and transaction manage and jdbc connection)
@Configuration
@Slf4j
public class DynamicDataSourceConfig {


	private final ThreadLocal<ExternalDataSourceDTO> currentSource = new ThreadLocal<>();
	private final Map<Object, Object> dataSourcesTarget = new ConcurrentHashMap<>();

	private AbstractRoutingDataSource dynamicDataSourceRouter;

	public DynamicDataSourceConfig() {}

	@Bean
	@Primary
	public DataSource dataSource() {
		dynamicDataSourceRouter = new AbstractRoutingDataSource() {
			@Override
			protected Object determineCurrentLookupKey() {
				return currentSource.get();
			}
		};
		dynamicDataSourceRouter.setTargetDataSources(dataSourcesTarget);
		dynamicDataSourceRouter.setDefaultTargetDataSource(defaultDataSource());
		dynamicDataSourceRouter.afterPropertiesSet();
		return dynamicDataSourceRouter;
	}

	public void setCurrentDataSource(ExternalDataSourceDTO externalDataSourceDTO) throws SQLException, DataSourceNotFoundException, DataSourceResolvingException {
		if (!isDataSourceExist(externalDataSourceDTO)) {
			if (externalDataSourceDTO != null) {
				addDataSource(externalDataSourceDTO);
			} else {
				throw new DataSourceNotFoundException(format("Datasource %s not found!", externalDataSourceDTO));
			}
		}
		this.currentSource.set(externalDataSourceDTO);
		log.debug("[d] Data source '{}' set as current.", externalDataSourceDTO);
	}

	/**
	 * take care of adding new data source to target source in routing data.
	 * @param externalDataSourceDTO
	 * @throws SQLException
	 */
	public void addDataSource(ExternalDataSourceDTO externalDataSourceDTO) throws SQLException {
		if (!isDataSourceExist(externalDataSourceDTO)) {
			DataSource dataSource = DataSourceBuilder.create()
					.driverClassName(externalDataSourceDTO.getDriverClass()) // using mysql remote external db only for now
					.url(externalDataSourceDTO.getHost())
					.username(externalDataSourceDTO.getUser())
					.password(externalDataSourceDTO.getPassword())
					.build();

			// Check that new connection is 'live'. If not - throw exception
			try(Connection c = dataSource.getConnection()) {
				dataSourcesTarget.put(externalDataSourceDTO, dataSource);
				dynamicDataSourceRouter.afterPropertiesSet();
			}
		}

	}

	public boolean isDataSourceExist(ExternalDataSourceDTO externalDataSourceDTO) {
		return this.dataSourcesTarget.containsKey(externalDataSourceDTO);
	}

	@Bean
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSourceProperties defaultDataSourceProperties() {
		// default data source props will be provided
		return new DataSourceProperties();
	}


	@Bean
	@Qualifier("altDataSourceProperties")
	@ConfigurationProperties(prefix = "spring.alt-datasource")
	public DataSourceProperties altDataSourceProperties() {
		// alternate data source props will be provided
		return new DataSourceProperties();
	}

	private DataSource defaultDataSource() {
		return defaultDataSourceProperties().initializeDataSourceBuilder().build();
	}

	public void clearDataSource() {
		this.currentSource.remove();
	}
}
