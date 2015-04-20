package hello;

import org.springframework.data.annotation.Id;
import org.springframework.social.facebook.api.Album;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Photo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kaustubh on 19/04/15.
 */
public class OurAlbum {
    @Id
    String _id;

    HashMap<String,Object> likes;
    HashMap<String,Object> comments;
    PagedList<Photo> photos;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public HashMap<String, Object> getLikes() {
        return likes;
    }

    public void setLikes(HashMap<String, Object> likes) {
        this.likes = likes;
    }

    public HashMap<String, Object> getComments() {
        return comments;
    }

    public void setComments(HashMap<String, Object> comments) {
        this.comments = comments;
    }

    public PagedList<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(PagedList<Photo> photos) {
        this.photos = photos;
    }

    public OurAlbum(Album album) {
        _id=album.getId();
        likes= (HashMap<String, Object>) album.getExtraData().get("likes");
        comments= (HashMap<String, Object>) album.getExtraData().get("comments");


    }

    public void addPhotos(PagedList<Photo> listOfPhotos) {

        photos= (PagedList<Photo>) listOfPhotos;


    }
}
