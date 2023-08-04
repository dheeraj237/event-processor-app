package com.medallia.imgprocessor.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Set;

/**
 * @author: dheeraj.suthar
 */
@Entity
@Data
@Table( name = "image_domain")
public class ImageDomain implements Serializable {

	private static final long serialVersionUID = -8445396541930075175L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "domain", nullable = false, unique = true)
	private String domain;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "image_domain_id")
	private Set<ImageFile> images;

	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime created;


	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime updated;

	@PrePersist
	public void onInsert() {
		created = LocalDateTime.now();
		updated = created;
	}

	@PostUpdate
	public void onUpdate() {
		updated = LocalDateTime.now();
	}
}
