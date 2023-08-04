package com.medallia.imgprocessor.tasks;

import com.medallia.imgprocessor.model.ExternalDataSourceDTO;
import com.medallia.imgprocessor.service.IExternalDbSourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * this task intended to poll all available db from test-endpoints and update live available to local in-memory db
 * is also mark inactive to other existing db which not present in current polled live db.
 * @author: dheeraj.suthar
 */
@Component
@Slf4j
@RequiredArgsConstructor
// OSCAR: what is the user of this IBaseScheduledTasks
public class AvailableDatabaseRefreshTask implements IBaseScheduledTasks {

	private final IExternalDbSourceService externalDbSourceService;

	/**
	 * live database polling scheduler
	 */
	@Scheduled(fixedDelayString = "${app.external.datasource.refresh.delay:60000}")
	@Override
	public void process() {
		List<ExternalDataSourceDTO> dataSources = externalDbSourceService.getDatabasesFromExternal();

		if (!CollectionUtils.isEmpty(dataSources)) {

			log.info("[Available DB] data source list: {}", dataSources);

			externalDbSourceService.saveLiveDatabase(dataSources);

			List<Long> idList = dataSources.stream().map(ExternalDataSourceDTO::getId).collect(Collectors.toList());

			externalDbSourceService.refreshAvailableDatabases(idList);

		} else {
			log.warn("No datasource found skipping updating the live database list..");
		}
	}
}
