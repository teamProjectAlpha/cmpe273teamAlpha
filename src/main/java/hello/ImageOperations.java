package hello;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.Properties;

/**
 * GridFs example
 * 
 * @author mkyong
 * 
 */

public class ImageOperations {

    final Properties configProp=new Properties();
    ApplicationContext ctx = new GenericXmlApplicationContext("SpringConfig.xml");
    GridFsOperations gridOperations;

    ImageOperations()
    {
        try {
            configProp.load( this.getClass().getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToMongo(String url) {


        gridOperations = (GridFsOperations) ctx.getBean("gridFsTemplate");

		DBObject metaData = new BasicDBObject();
		metaData.put("extra1", "anything 1");
		metaData.put("extra2", "anything 2");

		InputStream inputStream = null;
		try {
            try {
                inputStream = new URL(url).openStream();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            String contentType;

            if(url.contains("jpg"))
                contentType="image/jpeg";
            else
                contentType="image/png";

            GridFSFile file= gridOperations.store(inputStream,"testing",contentType, metaData);

            System.out.println(file.getId().toString());
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

		System.out.println("Done Writing");

	}

    public void readFromMongo() {

        ApplicationContext ctx = new GenericXmlApplicationContext("SpringConfig.xml");
        GridFsOperations gridOperations = (GridFsOperations) ctx.getBean("gridFsTemplate");

        List<GridFSDBFile> result = gridOperations.find(new Query().addCriteria(Criteria.where(
                "filename").is("testing")));
        for (GridFSDBFile file : result) {
            try {
                /*System.out.println(file.getFilename());
                System.out.println(file.getContentType());*/

                String DirectoryLocation=new String(configProp.getProperty("IMAGE_DIRECTORY"));

                File directory=new File(DirectoryLocation);
                if(!directory.exists())
                    directory.mkdirs();

                StringBuilder fileLocation=new StringBuilder(DirectoryLocation);

                fileLocation.append(file.getFilename());

                if ("image/jpeg".equals(file.getContentType()))
                    fileLocation.append(".jpg");
                else
                    fileLocation.append(".png");


                //save as another image
                file.writeTo(fileLocation.toString());


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Done Reading From Database");

    }
}
