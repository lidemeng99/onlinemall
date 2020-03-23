package com.thesis.onlinemall.product.infrastructure.repository;

import com.thesis.onlinemall.core.storage.S3Storage;
import java.io.File;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api")
public class S3Controller {

  @PostMapping("/s3/upload")
  public String upload(@RequestParam("file") MultipartFile srcFile) throws Exception {
    String suffixName = StringUtils.substringAfterLast(srcFile.getOriginalFilename(), ".");
    File tempFile = new File("/tmp/" + UUID.randomUUID().toString() + "." + suffixName);
    tempFile.createNewFile();

    srcFile.transferTo(tempFile.toPath());
    String url = S3Storage
        .uploadToS3(tempFile, tempFile.getName());
    tempFile.delete();
    return StringUtils.substringBefore(url,"?");
  }
}
