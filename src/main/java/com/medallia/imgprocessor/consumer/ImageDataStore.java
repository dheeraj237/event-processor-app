package com.medallia.imgprocessor.consumer;

import com.medallia.imgprocessor.model.ImageDomainDTO;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * in memory image data storage for repush
 * @author: dheeraj.suthar
 */
@Component
@Slf4j
public class ImageDataStore {

	private Queue<ImageDomainDTO> imageData;

	public ImageDataStore() {
		log.info("Initialized image data store DLQ for repush...");
		this.imageData = new LinkedList<>();
	}

	public void storeData(@NonNull ImageDomainDTO imageDomainDTO) {
		synchronized (this) {
			this.imageData.offer(imageDomainDTO);
		}
	}

	public List<ImageDomainDTO> dumpData() {
		List<ImageDomainDTO> data = new ArrayList<>();
		synchronized (this) {
			while(!this.imageData.isEmpty()) {
				data.add(this.imageData.poll());
			}
		}
		return data;
	}

	public List<ImageDomainDTO> getQueueData() {
		List<ImageDomainDTO> data = new ArrayList<>(this.imageData);
		return data;
	}
}
