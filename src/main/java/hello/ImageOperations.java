package hello;

import aws.s3.aws;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.Properties;
/**
 * GridFs example
 *
 * @author mkyong
 */

public class ImageOperations {

    final Properties configProp = new Properties();
    ApplicationContext ctx = new GenericXmlApplicationContext("SpringConfig.xml");
    GridFsOperations gridOperations;

    ImageOperations() {
        try {
            configProp.load(this.getClass().getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToMongo(OurPhoto photo, String albumId) {
        String filename = photo.get_id();

        gridOperations = (GridFsOperations) ctx.getBean("gridFsTemplate");

        DBObject metaData = new BasicDBObject();
        metaData.put("albumid", albumId);


        InputStream inputStream = null;
        try {
            try {
                inputStream = new URL(photo.getSource()).openStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String contentType;

            if (photo.getSource().contains("jpg"))
                contentType = "image/jpeg";
            else
                contentType = "image/png";

            GridFSFile file = gridOperations.store(inputStream, filename, contentType, metaData);

            //System.out.println(file.getId().toString());
            // GridFSInputFile as=new GridFSInputFile(inputStream);


        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Done Writing Photo with ID : " + filename);

    }

    public String readFromMongo(String photoId, String albumId) {

        ApplicationContext ctx = new GenericXmlApplicationContext("SpringConfig.xml");
        GridFsOperations gridOperations = (GridFsOperations) ctx.getBean("gridFsTemplate");
        String url = null;

        List<GridFSDBFile> result = gridOperations.find(new Query().addCriteria(Criteria.where(
                "filename").is(photoId)));


        for (GridFSDBFile file : result) {
            try {
                String DirectoryLocation = new String(configProp.getProperty("IMAGE_DIRECTORY"));
                DirectoryLocation += albumId + "/";
                File directory = new File(DirectoryLocation);
                if (!directory.exists())
                    directory.mkdirs();

                StringBuilder fileLocation = new StringBuilder(DirectoryLocation);

                fileLocation.append(file.getFilename());

                if ("image/jpeg".equals(file.getContentType()))
                    fileLocation.append(".jpg");
                else
                    fileLocation.append(".png");


                // save as another image
                System.out.println(fileLocation.toString());
                file.writeTo(fileLocation.toString());
                url = fileLocation.toString();




            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Done Reading Image " + photoId + " From Database");

        return url;

    }

    public void getImageFromFacebook(OurPhoto photo, String albumId) {

        String DirectoryLocation = new String(configProp.getProperty("IMAGE_DIRECTORY"));
        DirectoryLocation += albumId + "/";
        File directory = new File(DirectoryLocation);
        if (!directory.exists())
            directory.mkdirs();

        String photo_id = photo.get_id();

        InputStream inputStream = null;

        try {

            inputStream = new URL(photo.getSource()).openStream();

        } catch (IOException e) {
            e.printStackTrace();
        }

        String contentType;
        if (photo.getSource().contains("jpg"))
            contentType = ".jpg";
        else
            contentType = ".png";


        StringBuilder fileLocation = new StringBuilder(DirectoryLocation);
        fileLocation.append(photo_id);
        fileLocation.append(contentType);


        OutputStream os = null;
        try {
            os = new FileOutputStream(fileLocation.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        byte[] b = new byte[2048];
        int length;

        try {
            while ((length = inputStream.read(b)) != -1) {
                os.write(b, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        aws.uploadFile(fileLocation.toString());


        // save as another image
        System.out.println(fileLocation.toString());
        // file.writeTo(fileLocation.toString());
        //url = fileLocation.toString();

//        return url;

    }
}
