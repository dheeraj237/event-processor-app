package com.medallia.imgprocessor.repository;

import com.medallia.imgprocessor.entity.ImageFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author: dheeraj.suthar
 */
@Repository
public interface ImageFileRepository extends JpaRepository<ImageFile, Long> {

	Page<ImageFile> findAll(Pageable pageable);

	@Query(value = "select " +
			"imf.id, imf.image_path, imf.created, idm.domain " +
			"from image_file imf join image_domain idm on imf.image_domain_id = idm.id where idm.domain = :domain", nativeQuery = true)
	Page<Object[]> getImageFileByDomain(String domain, Pageable pageable);

	@Query(value = "select " +
			"imf.id, imf.image_path, imf.created, idm.domain " +
			"from image_file imf join image_domain idm on imf.image_domain_id = idm.id", nativeQuery = true)
	Page<Object[]> getAllImageFile(Pageable pageable);
}
