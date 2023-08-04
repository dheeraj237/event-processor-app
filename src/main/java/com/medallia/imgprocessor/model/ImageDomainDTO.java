package com.medallia.imgprocessor.model;

import com.medallia.imgprocessor.entity.ImageDomain;
import com.medallia.imgprocessor.entity.ImageFile;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: dheeraj.suthar
 */
@Data
public class ImageDomainDTO implements Serializable {

	private static final long serialVersionUID = 6180782292038190764L;
	private String domain;
	private List<String> images;

	public ImageDomain buildImageDomainFromDTO() {
		ImageDomain imageDomain = new ImageDomain();
		imageDomain.setDomain(this.getDomain());
		Set<ImageFile> imageUrls = new HashSet<>();
		if (!CollectionUtils.isEmpty(this.getImages())) {
			this.getImages().forEach(img -> {
				ImageFile imageFile = new ImageFile();
				imageFile.setImagePath(img);
				imageUrls.add(imageFile);
			});
		}
		imageDomain.setImages(imageUrls);
		return imageDomain;
	}


	@Override
	public String toString() {
		return "ImageDomainDTO{" +
				"domain='" + domain + '\'' +
				", images=" + images +
				'}';
	}
}
