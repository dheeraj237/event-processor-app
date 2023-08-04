package com.medallia.imgprocessor.consumer;

import com.medallia.imgprocessor.model.ImageDomainDTO;

/**
 * @author: dheeraj.suthar
 */
public interface IConsumer {

	public void onMessage(ImageDomainDTO content);
}
