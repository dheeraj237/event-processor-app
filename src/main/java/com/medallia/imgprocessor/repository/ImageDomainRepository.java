package com.medallia.imgprocessor.repository;

import com.medallia.imgprocessor.entity.ImageDomain;
import com.medallia.imgprocessor.entity.ImageFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author: dheeraj.suthar
 */
@Repository
public interface ImageDomainRepository extends JpaRepository<ImageDomain, Long> {

	Optional<ImageDomain> findByDomain(String domain);
}
