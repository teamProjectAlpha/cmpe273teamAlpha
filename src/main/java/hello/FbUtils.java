package hello;

import org.springframework.social.facebook.api.Album;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Photo;

import javax.inject.Inject;
import java.util.ArrayList;

/**
 * Created by kaustubh on 19/04/15.
 */
public class FbUtils {

    private Facebook facebook;
    private DAO dbUtils;


    public FbUtils(Facebook facebook) {
        this.facebook = facebook;
        dbUtils = new DAO();
    }


    public boolean backupAlbum(String albumId) {
        //TODO : implementation of methods

//      1.get the album metadata from Facebook
        Album album= facebook.mediaOperations().getAlbum(albumId);

//      2.get list of all photo-sources for aAlbum with photo Metadata from facebook
        ArrayList<Photo> listOfPhotos = getPhotos(albumId);

//
//      3.add photo with metadata to aAlbum
        OurAlbum aAlbum = new OurAlbum(album);
            aAlbum.add((PagedList<Photo>) listOfPhotos);

//      4.store aAlbum to DB with album meta data from 1
        dbUtils.saveOrUpdate(aAlbum);

        return false;
    }

    private ArrayList<Photo> getPhotos(String albumId) {
        return null;
    }


}
