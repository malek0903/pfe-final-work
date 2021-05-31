package org.tn.esprit.service.storage;

import io.minio.Result;
import io.minio.messages.Item;
import org.tn.esprit.exception.ImageUploadException;

import java.io.File;
import java.util.List;

/**
 * Interface for handling storage requirements
 */
public interface IStorageService {

    /**
     * Upload catalogue image to storage service
     * @param skuNumber
     * @param contentType
     * @param image
     * @throws ImageUploadException
     */
     void uploadCatalogueImage(String skuNumber, String contentType, File image ,String bucketName) throws ImageUploadException, ImageUploadException;

     String readMinioFile(String bucketName,String fileName) ;

     void createBucketIfNotExists(String bucketName);

     List<String> getAllFilesByBucketName(String bucketName);
}
