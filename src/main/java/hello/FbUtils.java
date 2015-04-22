package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.Album;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Photo;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.ArrayList;

/**
 * Created by kaustubh on 19/04/15.
 */
@Repository
public class FbUtils {


    public static Facebook facebook;

    @Autowired
    private DAO dbUtils;

    @Inject
    public FbUtils(Facebook facebook) {
        this.facebook = facebook;
    }


    public boolean backupAlbum(String albumId) {


//      1.get the album metadata from Facebook
        Album album = facebook.mediaOperations().getAlbum(albumId);

//      2.get list of all photo-sources for aAlbum with photo Metadata from facebook
        ArrayList<OurPhoto> listOfPhotos = OurPhoto.toOurPhotos(facebook, albumId);
        dbUtils.savePhotoList(listOfPhotos, albumId);

        //getPhotos(albumId);

//      3.add photo with metadata to aAlbum
        OurAlbum aAlbum = new OurAlbum(album);
        aAlbum.addPhotos(listOfPhotos);

//      4.store aAlbum to DB with album meta data from 1
        if (dbUtils.save(aAlbum) != null)
            return true;
        return false;
    }

    private PagedList<Photo> getPhotos(String albumId) {

        return facebook.mediaOperations().getPhotos(albumId);

    }

    public PagedList<Album> getAlbums() {
        return facebook.mediaOperations().getAlbums();
    }

    public ArrayList<OurPhoto> getOurPhotos(String albumId) {
        return dbUtils.getPhotoList(albumId);
    }

    public OurAlbum getAlbum(String albumID) {
        return dbUtils.getAlbum(albumID);
    }
}
