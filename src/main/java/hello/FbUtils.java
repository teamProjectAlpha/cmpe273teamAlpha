package hello;

import aws.s3.aws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.Album;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Photo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * Created by kaustubh on 19/04/15.
 */
@Repository
public class FbUtils {


    public static Facebook facebook;

    @Autowired
    private DAO dbUtils;

    @Autowired
    public FbUtils(Facebook facebook) {
        FbUtils.facebook = facebook;
    }

    public FbUtils() {
    }

    public String getId() {
        return facebook.userOperations().getUserProfile().getId();
    }
    public boolean backupAlbum(String albumId) {


//      1.get the album metadata from Facebook
        Album album = facebook.mediaOperations().getAlbum(albumId);

//      2.get list of all photo-sources for aAlbum with photo Metadata from facebook
        ArrayList<OurPhoto> listOfPhotos = OurPhoto.toOurPhotos(facebook, albumId);
        dbUtils.savePhotoList(listOfPhotos, albumId);


//      3.add photo with metadata to aAlbum
        OurAlbum aAlbum = new OurAlbum(album);
        aAlbum.addPhotos(listOfPhotos);

//      4.store aAlbum to DB with album meta data from 1
        return dbUtils.save(aAlbum) != null;
    }

    public boolean deleteAlbum(String albumId) {

        System.out.println(albumId);

        if (albumId != null)
            if (aws.deleteAlbum(albumId))
                if (dbUtils.removeAlbum(albumId))
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

    public String getImageURL(String albumId, String photoId) {

        ImageOperations imageOperations = new ImageOperations();
        String imageURL = imageOperations.readFromMongo(photoId, albumId);
        imageURL = imageURL.substring(1, imageURL.length());
        return imageURL;
    }

    public OurAlbum getAlbum(String albumID) {
        return dbUtils.getAlbum(albumID);
    }


    public ArrayList<OurAlbum> getOurAlbumsBy(String person_id) {
        return dbUtils.getAlbumsBy(person_id);
    }
}
