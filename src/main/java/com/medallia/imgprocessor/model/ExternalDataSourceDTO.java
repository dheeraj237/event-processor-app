package com.medallia.imgprocessor.model;

import com.medallia.imgprocessor.entity.ExternalDataSource;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author: dheeraj.suthar
 */
@Data
public class ExternalDataSourceDTO implements Serializable {

	private static final long serialVersionUID = 3081673910167486544L;

	/**
	 * [{id: 1,host: "localhost",schema: "schema1",user: "user1",password: "password"},
	 */
	private Long id;
	private String host;
	private String schema;
	private String user;
	private String password;
	private String driverClass;

	public ExternalDataSourceDTO() {
	}

	public ExternalDataSourceDTO(ExternalDataSource externalDataSource) {
		this.host = externalDataSource.getHost();
		this.id = externalDataSource.getId();
		this.schema = externalDataSource.getDbSchema();
		this.user = externalDataSource.getUserName();
		this.password = externalDataSource.getPassword();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ExternalDataSourceDTO that = (ExternalDataSourceDTO) o;
		return getHost().equals(that.getHost()) && Objects.equals(getSchema(), that.getSchema()) && getUser().equals(that.getUser()) && Objects.equals(getPassword(), that.getPassword());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getHost(), getSchema(), getUser(), getPassword());
	}
}
