package br.com.gabriel.api.service.s3;

import br.com.gabriel.api.domain.FileLocation;
import br.com.gabriel.api.domain.UniqueFileName;
import br.com.gabriel.api.model.UploadedFileModel;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class S3Service {

    private AmazonS3 s3;
    private String bucketName;
    private String region;

    @Autowired
    public S3Service(AmazonS3 s3, String bucketName, String region){
        this.s3 = s3;
        this.bucketName = bucketName;
        this.region = region;
    }

    public List<UploadedFileModel> uploadedFileModelList(MultipartFile[] files){

        List<UploadedFileModel> uploadedFileModels = new ArrayList<>();

        for (MultipartFile file : files) {
            String originalName = file.getOriginalFilename();
            String s3FileName = getUniqueFileName(originalName);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());

            try {
                PutObjectRequest request = new PutObjectRequest(bucketName, s3FileName, file.getInputStream(), metadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead);
                s3.putObject(request);

                UploadedFileModel uploadedFileModel = new UploadedFileModel(s3FileName, getFileLocation(s3FileName));
                uploadedFileModels.add(uploadedFileModel);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        return uploadedFileModels;
    }

    private String getFileLocation(String s3Filename) {
        return new FileLocation(s3Filename, bucketName, region).getLocation();
    }

    private String getUniqueFileName(String fileName){
        return new UniqueFileName(fileName).getFileName();
    }


}
