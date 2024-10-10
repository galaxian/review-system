package com.example.review.common.utiil;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class DummyS3Uploader implements S3Uploader {

	@Override
	public String upload(MultipartFile file) {
		final String fileName = file.getOriginalFilename();
		return "https://review.com/" + fileName.split("\\.")[0];
	}
}
