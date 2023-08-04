package com.medallia.imgprocessor.handler;

import com.medallia.imgprocessor.exception.DownStreamSystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

/**
 * @author: dheeraj.suthar
 */
@Component
@Slf4j
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

	@Override
	public boolean hasError(ClientHttpResponse clienthttpresponse) throws IOException {

		if (clienthttpresponse.getStatusCode() != HttpStatus.OK) {
			log.error("[HTTP Error] status: {} | body: {}", clienthttpresponse.getStatusCode(), clienthttpresponse.getBody());
			return true;
		}
		return false;
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		if (response.getStatusCode().is5xxServerError()) {
			log.error("[Server Error] status: {} info: {} body: {}", response.getStatusCode(), response.getStatusText(), response.getBody());
//			if (response.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE) {
				throw new DownStreamSystemException("Down stream system throws server error: " + response.getStatusCode());
//			}
			// more server errors

		} else if (response.getStatusCode().is4xxClientError()) {
			// all client specific errors
			log.error("[Client Error] status: {} info: {} body: {}", response.getStatusCode(), response.getStatusText(), response.getBody());
		}
	}
}
