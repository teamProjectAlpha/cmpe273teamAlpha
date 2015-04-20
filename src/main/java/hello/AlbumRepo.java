package hello;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by kaustubh on 20/04/15.
 */
public interface AlbumRepo extends MongoRepository<OurAlbum,String > {

    public OurAlbum findBy_id(String Id);

}
