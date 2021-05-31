package org.tn.esprit.service.storage;


import io.minio.*;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import io.minio.messages.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tn.esprit.config.MinIOConfiguration;
import org.tn.esprit.exception.ImageUploadException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.MalformedInputException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to handle storage operations with MinIO Object Store application
 */
@ApplicationScoped
public class MinIOStorageService implements IStorageService  {

    private Logger log = LoggerFactory.getLogger(MalformedInputException.class);


    @Inject
    MinIOConfiguration minIOConfiguration;

    private MinioClient minioClient;

    void initializeMinIOClient() throws ImageUploadException, RuntimeException {
        try {
            // Create instance of minio client with configured service details
            minioClient =
                new MinioClient(
                    minIOConfiguration.getHost(),
                    minIOConfiguration.getPort(),
                    minIOConfiguration.getAccessKey(),
                    minIOConfiguration.getSecretKey(),
                    minIOConfiguration.isUseSsl());

            // Check and create if bucket is available to store catalogue images
            //createBucketIfNotExists();
        }
        catch (InvalidEndpointException | InvalidPortException e) {
            throw new RuntimeException(String.format("MinIO Service is not initialized due to invalid Host:%s or PORT:%s", "", ""));
        }

        catch (Exception e) {
            throw new RuntimeException("Error occurred while initializing MinIO Service", e);
        }
    }

    public void createBucketIfNotExists(String minioBucketName) {
        try {
            minioClient.makeBucket(minioBucketName);
        }catch (Exception e){
            throw  new RuntimeException();
        }
    }

    @Override
    public List<String>  getAllFilesByBucketName(String bucketName) {
        List<String> filesNameByBucket = new ArrayList<>() ;
        try{
            if(minioClient == null)
                initializeMinIOClient();
            Iterable<Result<Item>> results =
                    minioClient.listObjects(bucketName);
            for (Result<Item> result : results) {
                Item item = result.get();
                filesNameByBucket.add(item.objectName());
            }
        }catch (Exception e) {
            throw  new RuntimeException();
        }
        return filesNameByBucket ;
    }

    /**
     * Method to upload the image to minio object store
     * @param skuNumber
     * @param contentType
     * @param image
     * @throws ImageUploadException
     */
    public void uploadCatalogueImage(String skuNumber, String contentType, File image,String bucketName) throws ImageUploadException {
        try {

            if(minioClient == null)
                initializeMinIOClient();

            // Prepare options with size and content type
            PutObjectOptions options = new PutObjectOptions(image.length(),-1);
            options.setContentType(contentType);


            boolean isExist = minioClient.bucketExists(bucketName);
            if (!isExist){
                createBucketIfNotExists(bucketName);
            }

            // Put the object to bucket
            minioClient.putObject(
                bucketName,
                skuNumber,
                new FileInputStream(image),
                options);
        }
        catch (Exception e) {
            throw new ImageUploadException(String.format("Error occurred while uploading catalogue item image for SKU: %s", skuNumber), e);
        }
    }

    public String readMinioFile(String bucketName,String fileName){
        try {
            if (minioClient == null)
                initializeMinIOClient();
            return minioClient.presignedGetObject(bucketName,fileName);
        }catch (Exception e){
            throw  new RuntimeException();
        }
    }


}
