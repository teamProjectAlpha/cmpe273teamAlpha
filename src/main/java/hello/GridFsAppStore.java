package hello;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.gridfs.GridFsOperations;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * GridFs example
 * 
 * @author mkyong
 * 
 */

public class GridFsAppStore {

	public static void writeToMongo(String url) {

		ApplicationContext ctx = new AnnotationConfigApplicationContext(Application.class);

		//@Autowired
        GridFsOperations gridOperations = (GridFsOperations) ctx.getBean("gridFsTemplate");

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

            gridOperations.store(inputStream, "testing.png", "image/png", metaData);

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

}
