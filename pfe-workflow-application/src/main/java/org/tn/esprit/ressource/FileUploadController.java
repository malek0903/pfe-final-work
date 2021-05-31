package org.tn.esprit.ressource;

import org.apache.commons.io.IOUtils;
import org.apache.tika.Tika;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.tags.Tags;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tn.esprit.exception.ImageUploadException;
import org.tn.esprit.service.storage.IStorageService;

import javax.inject.Inject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;


@Path("file")
@Tags(value = @Tag(name = "Files", description = " file upload methods"))
public class FileUploadController {

    private Logger log = LoggerFactory.getLogger(FileUploadController.class);

    @Inject
    IStorageService storageService;

    @POST
    @Path("upload/{file-name}/bucket/{bucket-name}")
    public Response uploadImage(@PathParam(value = "file-name") String skuNumber,
                                @PathParam(value = "bucket-name") String bucketName,
                                InputStream inputStream)  throws  Exception  {
        File tempFile = createTempFile(skuNumber, inputStream);
        Tika tika = new Tika();
        String mimeType = tika.detect(tempFile);
//        if (!mimeType.contains("image")) {
//            throw new ImageUploadException(ErrorCodes.ERR_IMAGE_UPLOAD_INVALID_FORMAT, String.format("File uploaded for SKU:%s is not valid image format", skuNumber));
//        }

        storageService.uploadCatalogueImage(skuNumber, mimeType, tempFile,bucketName);

        return Response.status(Response.Status.CREATED).build();

    }



    @GET
    @Path("{bucket-name}/bucket/{fileName}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getFile(@PathParam(value = "bucket-name") String bucketName ,@PathParam(value = "fileName") String fileName){
        //InputStream inputStream = storageService.readMinioFile(fileName);

        return storageService.readMinioFile(bucketName,fileName) ;
    }


    @GET
    @Path("bucketName/{bucket-name}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getFileByBacket(@PathParam(value = "bucket-name") String bucketName){
        //InputStream inputStream = storageService.readMinioFile(fileName);
        return storageService.getAllFilesByBucketName(bucketName) ;
    }

    private File createTempFile(String skuNumber, InputStream inputStream) throws ImageUploadException {
        try {
            File tempFile = File.createTempFile(skuNumber, ".tmp");
            tempFile.deleteOnExit();

            FileOutputStream out = new FileOutputStream(tempFile);
            IOUtils.copy(inputStream, out);

            return tempFile;
        } catch (Exception e) {
            throw new ImageUploadException(String.format("Error occurred while creating temp file for uploaded image : %s", skuNumber), e);
        }
    }


}
