package com.medallia.imgprocessor.tasks;

import com.medallia.imgprocessor.consumer.ImageDataStore;
import com.medallia.imgprocessor.model.ImageDomainDTO;
import jakarta.jms.Queue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * this task intended to repush failed image data after consumed from the async queue and store into imageDataStore
 * local in memory cache.
 * @author: dheeraj.suthar
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class RepushFailedImageDataTask implements IBaseScheduledTasks {

	private final JmsTemplate jmsTemplate;
	private final Queue queue;
	private final ImageDataStore imageDataStore;

	/**
	 * repush failed image data scheduler
	 */
	@Scheduled(fixedDelayString = "${app.external.imagedata.repush.delay:60000}")
	@Override
	public void process() {
		List<ImageDomainDTO> imageDomainDTOList = imageDataStore.dumpData();

		if (!CollectionUtils.isEmpty(imageDomainDTOList)) {
			log.info("Dump of {} failed image data collected for repush", imageDomainDTOList.size());
			imageDomainDTOList.forEach(data -> {
				jmsTemplate.convertAndSend(queue, data);
			});
		} else {
			log.warn("No image data dump found skipping repush..");
		}
	}
}
