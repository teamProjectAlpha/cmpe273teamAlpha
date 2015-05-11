package hello;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaustubh on 20/04/15.
 */
public interface AlbumRepo extends MongoRepository<OurAlbum,String > {

    OurAlbum findBy_id(String Id);

    List<OurAlbum> deleteBy_id(String Id);

    @Query("{'createdBy._id':'?0'}")
    ArrayList<OurAlbum> findByCreatedBy(String personId);


}
