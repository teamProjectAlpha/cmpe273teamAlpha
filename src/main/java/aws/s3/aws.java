package aws.s3;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaustubh on 05/05/15.
 */
public final class aws {

    private static final String BUCKET = "cmpe273-kaustubh";//bucket Name
    private static final long EXPIRY_TIME = 1000 * 3600; // Add 1 hour.


    /**
     * get the Url for the resource objectKey from Aws bucket BUCKET
     *
     * @param objectKey the complete name for the file
     * @return URL of the objectKey
     */
    public static URL getUrlfor(String objectKey) {
        AmazonS3 s3Client = new AmazonS3Client(new ProfileCredentialsProvider());


        java.util.Date expiration = new java.util.Date();
        long msec = expiration.getTime();
        msec += EXPIRY_TIME;
        expiration.setTime(msec);

        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(BUCKET, objectKey);
        generatePresignedUrlRequest.setMethod(HttpMethod.GET);
        generatePresignedUrlRequest.setExpiration(expiration);

        return s3Client.generatePresignedUrl(generatePresignedUrlRequest);


    }


    /**
     * get all the Url for the resource objectKey from Aws bucket BUCKET
     *
     * @param objectKeys
     * @return ArrayList Of URL
     */
    public static ArrayList<URL> getUrlfor(ArrayList<String> objectKeys) {

        AmazonS3 s3Client = new AmazonS3Client(new ProfileCredentialsProvider());
        java.util.Date expiration;
        long msec = 0;
        GeneratePresignedUrlRequest generatePresignedUrlRequest = null;
        ArrayList<URL> returnURLS = new ArrayList<URL>();

        for (String objectKey : objectKeys) {
            expiration = new java.util.Date();
            msec = expiration.getTime() + EXPIRY_TIME;
            expiration.setTime(msec);

            generatePresignedUrlRequest = new GeneratePresignedUrlRequest(BUCKET, objectKey);
            generatePresignedUrlRequest.setMethod(HttpMethod.GET);
            generatePresignedUrlRequest.setExpiration(expiration);

            returnURLS.add(s3Client.generatePresignedUrl(generatePresignedUrlRequest));
        }
        return returnURLS;

    }

    /**
     * upload a file to AWS BUCKET
     *
     * @param path path of files to be uploaded
     * @return md5 hash of the uploaded file
     */
    public static String uploadFile(String path, String fileName) {
        AmazonS3 s3Client = new AmazonS3Client(new ProfileCredentialsProvider());

        File file = new File(path);

        AccessControlList acl = new AccessControlList();
        acl.grantPermission(GroupGrantee.AuthenticatedUsers, Permission.Read);
        acl.grantPermission(GroupGrantee.AuthenticatedUsers, Permission.Write);
        PutObjectRequest o = new PutObjectRequest(BUCKET, fileName, file).withAccessControlList(acl);
        PutObjectResult result = s3Client.putObject(o);
        return (result.getETag());

    }

    /**
     * uploads list of Files to AWS
     *
     * @param paths ArrayList of path of files to be uploaded
     * @return md5 hash of the uploaded file
     */
    public static ArrayList<String> uploadFiles(ArrayList<String> paths) {
        AmazonS3 s3Client = new AmazonS3Client(new ProfileCredentialsProvider());
        File file;
        AccessControlList acl;
        ArrayList<String> result = new ArrayList<String>();

        for (String path : paths) {
            file = new File(path);
            acl = new AccessControlList();
            acl.grantPermission(GroupGrantee.AuthenticatedUsers, Permission.Read);
            acl.grantPermission(GroupGrantee.AuthenticatedUsers, Permission.Write);


            result.add(
                    s3Client.putObject(
                            new PutObjectRequest(BUCKET, file.getName(), file)
                                    .withAccessControlList(acl))
                            .getETag());

        }

        return result;

    }

    public static boolean deleteAlbum(String objectKey) {

        if (objectKey == null)
            return false;

        AmazonS3 s3Client = new AmazonS3Client(new ProfileCredentialsProvider());
        DeleteObjectsRequest multiObjectDeleteRequest;

        ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
                .withBucketName(BUCKET)
                .withPrefix(objectKey);
        ObjectListing objectListing;

        do {
            multiObjectDeleteRequest = new DeleteObjectsRequest(BUCKET);
            List<DeleteObjectsRequest.KeyVersion> keys = new ArrayList<DeleteObjectsRequest.KeyVersion>();
            objectListing = s3Client.listObjects(listObjectsRequest);
            for (S3ObjectSummary objectSummary :
                    objectListing.getObjectSummaries()) {
                keys.add(new DeleteObjectsRequest.KeyVersion(objectSummary.getKey()));

            }
            multiObjectDeleteRequest.setKeys(keys);
            listObjectsRequest.setMarker(objectListing.getNextMarker());
            try {
                DeleteObjectsResult delObjRes = s3Client.deleteObjects(multiObjectDeleteRequest);

                System.out.format("Successfully deleted all the %s items.\n", delObjRes.getDeletedObjects().size());

            } catch (MultiObjectDeleteException e) {
                e.printStackTrace();
                return false;
            }

        } while (objectListing.isTruncated());

        return true;


    }

    /**
     * TEST for the functions
     *
     * @param args
     */
    public static void test(String[] args) {

        uploadFile("./newFile.txt", "newfile/newfile");
        uploadFile("./newFile.txt", "newfile/newfile1");

        System.out.println(getUrlfor("newFile.txt"));

        deleteAlbum("newfile");

    }
}
