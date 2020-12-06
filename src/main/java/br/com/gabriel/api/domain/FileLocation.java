package br.com.gabriel.api.domain;

import com.amazonaws.services.s3.AmazonS3;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;


public class FileLocation {

    private static final String HTTPS = "https://";
    private static final String S3 = ".s3.";
    private static final String PATH = ".amazonaws.com/";

    private String s3Filename;
    private String bucketName;
    private String region;

    public FileLocation(String s3Filename, String bucketName, String region){
        this.s3Filename = s3Filename;
              this.bucketName = bucketName;
        this.region = region;
    }

    public String getLocation(){
        StringBuilder location = new StringBuilder();
        location.append(HTTPS);
        location.append(bucketName);
        location.append(S3);
        location.append(region);
        location.append(PATH);
        location.append(s3Filename);
        return location.toString();
    }

}
