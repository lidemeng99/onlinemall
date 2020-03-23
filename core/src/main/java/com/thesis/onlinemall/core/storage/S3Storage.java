package com.thesis.onlinemall.core.storage;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.auth.SystemPropertiesCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.securitytoken.model.AssumeRoleRequest;
import com.amazonaws.services.securitytoken.model.Credentials;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;

import static com.amazonaws.SDKGlobalConfiguration.*;


public class S3Storage {
  static final String bucketName = "test-developer";
  static AmazonS3 s3;
  static TransferManager tx;
  private static String AWS_ACCESS_KEY = "AKIA2PYHNAEXIJUQ4SRK";
  private static String AWS_SECRET_KEY = "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 ";

  static {
    System.setProperty(ACCESS_KEY_SYSTEM_PROPERTY, AWS_ACCESS_KEY);
    System.setProperty(SECRET_KEY_SYSTEM_PROPERTY, AWS_SECRET_KEY);
    //System.setProperty(SESSION_TOKEN_SYSTEM_PROPERTY, "");
    BasicSessionCredentials tempCred = null;
    try {
      SystemPropertiesCredentialsProvider profileCred = new SystemPropertiesCredentialsProvider();
      AssumeRoleRequest theRole = new AssumeRoleRequest()
          .withRoleArn("arn:aws-cn:iam::721152002098:role/aws-role-006-1010-r2-dev")
          .withRoleSessionName("role-session-name");
      AWSSecurityTokenService sts = AWSSecurityTokenServiceClientBuilder.standard().withRegion(Regions.CN_NORTH_1)
          .withCredentials(profileCred).build();
      Credentials c = sts.assumeRole(theRole).getCredentials();
      tempCred = new BasicSessionCredentials(c.getAccessKeyId(), c.getSecretAccessKey(), c.getSessionToken());
    } catch (Exception e) {
      throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
          + "Please make sure that your credentials file is at the correct "
          + "location (~/.aws/credentials), and is in valid format.", e);
    }
    s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(tempCred))
        .withRegion(Regions.CN_NORTH_1).build();
  }

  /**
   * @param @param  tempFile 目标文件
   * @param @param  remoteFileName 文件名
   * @param @return
   * @param @throws IOException    设定文件
   * @return String    返回类型
   * @throws
   * @Title: uploadToS3
   * @Description: 将文件上传至S3上并且返回url
   */
  public static String uploadToS3(File tempFile, String remoteFileName) throws IOException {
    try {
      //上传文件
      s3.putObject(new PutObjectRequest(bucketName, remoteFileName, tempFile).withCannedAcl(CannedAccessControlList.PublicRead));
      //获取一个request
      GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(
          bucketName, remoteFileName);
      //生成公用的url
      URL url = s3.generatePresignedUrl(urlRequest);
      System.out.println("=========URL=================" + url + "============URL=============");
      return url.toString();
    } catch (AmazonServiceException ase) {
      ase.printStackTrace();
    } catch (AmazonClientException ace) {
      ace.printStackTrace();
    }
    return null;
  }

  /**
   * @param @param  remoteFileName
   * @param @throws IOException    设定文件
   * @return S3ObjectInputStream    返回类型  数据流
   * @throws
   * @Title: getContentFromS3
   * @Description: 获取文件2进制流
   */
  public static S3ObjectInputStream getContentFromS3(String remoteFileName) throws IOException {
    try {
      GetObjectRequest request = new GetObjectRequest(bucketName, remoteFileName);
      S3Object object = s3.getObject(request);
      S3ObjectInputStream inputStream = object.getObjectContent();
      return inputStream;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }


  /**
   * @param @param  remoteFileName 文件名
   * @param @param  path 下载的路径
   * @param @throws IOException    设定文件
   * @return void    返回类型
   * @throws
   * @Title: downFromS3
   * @Description: 将文件下载到本地路径
   */
  public static void downFromS3(String remoteFileName, String path) throws IOException {
    try {
      GetObjectRequest request = new GetObjectRequest(bucketName, remoteFileName);
      s3.getObject(request, new File(path));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * @param @param  remoteFileName 文件名
   * @param @return
   * @param @throws IOException    设定文件
   * @return String    返回类型
   * @throws
   * @Title: getUrlFromS3
   * @Description: 获取文件的url
   */
  public static String getUrlFromS3(String remoteFileName) throws IOException {
    try {
      GeneratePresignedUrlRequest httpRequest = new GeneratePresignedUrlRequest(bucketName, remoteFileName);
      String url = s3.generatePresignedUrl(httpRequest).toString();//临时链接
      return url;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 验证s3上是否存在名称为bucketName的Bucket
   *
   * @param bucketName
   * @return
   */
  public static boolean checkBucketExists(String bucketName) {
    List<Bucket> buckets = s3.listBuckets();
    for (Bucket bucket : buckets) {
      System.out.println(bucket.getName());
      if (Objects.equals(bucket.getName(), bucketName)) {
        return true;
      }
    }
    return false;
  }

  public static void delFromS3(String remoteFileName) throws IOException {
    try {
      s3.deleteObject(bucketName, remoteFileName);
    } catch (AmazonServiceException ase) {
      ase.printStackTrace();
    } catch (AmazonClientException ace) {
      ace.printStackTrace();
    }
  }
}
