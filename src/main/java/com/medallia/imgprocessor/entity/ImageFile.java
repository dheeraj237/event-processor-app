package com.medallia.imgprocessor.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * @author: dheeraj.suthar
 */
@Entity
@Data
@Table(name = "image_file")
public class ImageFile implements Serializable {

	private static final long serialVersionUID = -7516028042298608830L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String imagePath;

	@CreationTimestamp(source = SourceType.DB)
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime created;

//	@ManyToOne(fetch = FetchType.LAZY)
//	ImageDomain imageDomain;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ImageFile imageFile = (ImageFile) o;
		return Objects.equals(getImagePath(), imageFile.getImagePath());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getImagePath());
	}

	@PrePersist
	public void onInsert() {
		created = LocalDateTime.now();
	}
}
