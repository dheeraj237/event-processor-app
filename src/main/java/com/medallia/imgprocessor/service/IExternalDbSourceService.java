package com.medallia.imgprocessor.service;

import com.medallia.imgprocessor.exception.NoDataSourceFoundException;
import com.medallia.imgprocessor.model.DomainDbDTO;
import com.medallia.imgprocessor.model.ExternalDataSourceDTO;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * @author: dheeraj.suthar
 */
public interface IExternalDbSourceService {

	List<ExternalDataSourceDTO> getDatabasesFromExternal();

	DomainDbDTO getDatabaseForDomain(@NonNull String domain);

	void refreshAvailableDatabases(List<Long> idList);

	void saveLiveDatabase(List<ExternalDataSourceDTO> dataSources);

	ExternalDataSourceDTO getAvailabeleDBByID(Long dbId);
}
