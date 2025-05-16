package com.sb02.guestbookprj;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileService {

  private final S3Client s3Client;

  @Value("${aws.s3.bucket}")
  private String bucketName;

  @Value("${aws.s3.base-url}")
  private String baseUrl;

  public String upload(MultipartFile image) {
    String fileName = image.getOriginalFilename();
    String contentType = image.getContentType();
    long size = image.getSize();

    String s3Key = UUID.randomUUID() + "-" + fileName;

    PutObjectRequest putObjectRequest = PutObjectRequest.builder()
        .bucket(bucketName)
        .key(s3Key)
        .contentType(contentType)
        .build();

    try {
      s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(image.getInputStream(), size));
    } catch (IOException e) {
      log.error("Error uploading file to S3", e);
      throw new RuntimeException("Error uploading file to S3", e);
    }

    String s3Url = baseUrl + "/" + s3Key;

    log.info("File uploaded successfully: {}", s3Url);

    return s3Url;
  }

}
