package com.medallia.imgprocessor.repository;

import com.medallia.imgprocessor.entity.ExternalDataSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author: dheeraj.suthar
 */
@Repository
public interface ExternalDataSourceRepository extends JpaRepository<ExternalDataSource, Long> {


	@Query("update ExternalDataSource eds set eds.isLive = FALSE where eds.id not in :idList")
	@Modifying
	void refreshDatabasesAvailability(List<Long> idList);


	@Query("from ExternalDataSource eds where eds.isLive = TRUE and eds.id =:dbId")
	Optional<ExternalDataSource> findAvailableDBById(Long dbId);
}
