package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by kaustubh on 19/04/15.
 */

@Repository
public class DAO {


    @Autowired
    private AlbumRepo albumRepo;

    /**
     * TODO : saves or updates the album to backeup location
     *
     * @param aAlbum
     */
    public void update(OurAlbum aAlbum) {
        OurAlbum update = albumRepo.findOne(aAlbum.get_id());

        if (aAlbum.get_id() != null)
            update.set_id(aAlbum.get_id());
        update.setComments(aAlbum.getComments());
        update.setLikes(aAlbum.getLikes());
        update.setPhotos(aAlbum.getPhotos());

        albumRepo.save(update);
    }

    public OurAlbum save(OurAlbum album) {
        return albumRepo.save(album);
    }

    /**
     * @param albumId
     * @return
     */
    public OurAlbum getAlbum(String albumId) {

        return albumRepo.findBy_id(albumId);
    }

    /**
     * TODO : returns list of albums backedup
     *
     * @return
     */
    public List<String> getAlbumList(String Id) {

        //albumRepo.findById()
        return null;
    }


}
