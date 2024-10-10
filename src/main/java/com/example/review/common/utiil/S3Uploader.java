package com.example.review.common.utiil;

import org.springframework.web.multipart.MultipartFile;

public interface S3Uploader {

	String upload(MultipartFile file);
}
