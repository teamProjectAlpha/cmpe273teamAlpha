package hello;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;
import java.util.Properties;

@Configuration
@AutoConfigureAfter(DispatcherServletAutoConfiguration.class)
@SpringBootApplication
public class Application extends WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter {

    final Properties configProp = new Properties();

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        try {
            configProp.load(this.getClass().getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String DirectoryLocation = new String(configProp.getProperty("IMAGE_DIRECTORY"));

        String myExternalFilePath = "file:///" + DirectoryLocation;

        registry.addResourceHandler("/m/**").addResourceLocations(myExternalFilePath);

        super.addResourceHandlers(registry);
    }

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }

} 