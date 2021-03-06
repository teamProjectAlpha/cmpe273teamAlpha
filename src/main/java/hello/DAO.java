package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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

    public void savePhotoList(ArrayList<OurPhoto> ourPhotoArrayList, String albumId) {
        ImageOperations imageOperations = new ImageOperations();
        for (OurPhoto photo : ourPhotoArrayList)
            imageOperations.getImageFromFacebook(photo, albumId);
        //imageOperations.writeToMongo(photo, albumId);
    }

    public ArrayList<OurPhoto> getPhotoList(String albumId) {

        ArrayList<OurPhoto> ourPhotoArrayList = null;

        if (getAlbum(albumId) != null) {
            ourPhotoArrayList = getAlbum(albumId).getPhotos();
        }

        return ourPhotoArrayList;

    }


    public ArrayList<OurAlbum> getAlbumsBy(String person_id) {
        return albumRepo.findByCreatedBy(person_id);
    }

    public boolean removeAlbum(String albumId) {
        if (albumId == null)
            return false;

        try {
            System.out.println(albumId);
            System.out.println(albumRepo.deleteBy_id(albumId));

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
