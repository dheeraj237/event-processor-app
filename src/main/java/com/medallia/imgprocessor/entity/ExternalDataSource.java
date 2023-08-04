package com.medallia.imgprocessor.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @author: dheeraj.suthar
 */
@Entity
@Data
@Table(name = "external_data_source")
public class ExternalDataSource implements Serializable {

	private static final long serialVersionUID = 7218610789022459827L;
	/**
	 * [{id: 1,host: "localhost",schema: "schema1",user: "user1",password: "password"},
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String host;
	private String dbSchema;
	private String userName;
	private String password;
	private boolean isLive;
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime lastUpdated;
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime created;


	@PrePersist
	public void onInsert() {
		created = LocalDateTime.now();
		lastUpdated = created;
	}

	@PreUpdate
	public void onUpdate() {
		lastUpdated = LocalDateTime.now();
	}
}
