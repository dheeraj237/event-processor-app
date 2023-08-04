package com.medallia.imgprocessor.consumer;

import com.medallia.imgprocessor.model.ImageDomainDTO;
import com.medallia.imgprocessor.service.IEventProcessorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author: dheeraj.suthar
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ProcessMessageConsumer  implements IConsumer {

	private final IEventProcessorService imageProcessorService;

	@Override
	@JmsListener(destination = "${local.activemq.name:localqueue}")
	public void onMessage(ImageDomainDTO content) {
		log.info("Consuming message {}", content);
		imageProcessorService.storeImageData(content);
	}
}
