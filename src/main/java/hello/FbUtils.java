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


    private Facebook facebook;

    @Autowired
    private DAO dbUtils;

    @Inject
    public FbUtils(Facebook facebook) {
        this.facebook = facebook;
        //dbUtils = new DAO();
    }




    public boolean backupAlbum(String albumId) {
        //TODO : implementation of methods

//      1.get the album metadata from Facebook
        Album album= facebook.mediaOperations().getAlbum(albumId);

//      2.get list of all photo-sources for aAlbum with photo Metadata from facebook
        ArrayList<Photo> listOfPhotos = getPhotos(albumId);

//      3.add photo with metadata to aAlbum
        OurAlbum aAlbum = new OurAlbum(album);
            aAlbum.addPhotos((PagedList<Photo>) listOfPhotos);

//      4.store aAlbum to DB with album meta data from 1
        dbUtils.save(aAlbum);

        return false;
    }

    private PagedList<Photo> getPhotos(String albumId) {
        //TODO add a custom implementation of Photo Class to ensure that onely requried meta data is stored
        PagedList<Photo> fromFB= facebook.mediaOperations().getPhotos(albumId);
        PagedList<OurPhoto> toOurPhoto = OurPhoto.parse(fromFB);
        return null;//TODO
    }

    public PagedList<Album> getAlbums(){
        return facebook.mediaOperations().getAlbums();
    }

}
