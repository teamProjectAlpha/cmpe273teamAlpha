package hello;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.social.facebook.api.Album;
import org.springframework.social.facebook.api.Reference;

import java.util.*;

/**
 * Created by kaustubh on 19/04/15.
 */
@Document
public class OurAlbum {
    @Id
    private String _id;
    private LinkedHashMap likes;
    private LinkedHashMap comments;
    private ArrayList<OurPhoto> photos;
    private Date createdTime;
    private Date updatedTime;
    private Person createdBy;

    public OurAlbum() {
    }

    public OurAlbum(String _id, LinkedHashMap likes, LinkedHashMap comments, ArrayList<OurPhoto> photos, Date createdTime, Date updatedTime, Person createdBy) {
        this._id = _id;
        this.likes = likes;
        this.comments = comments;
        this.photos = photos;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
        this.createdBy = createdBy;
    }

    public OurAlbum(Album album) {
        _id = album.getId();
        likes = (LinkedHashMap) album.getExtraData().get("likes");
        comments = (LinkedHashMap) album.getExtraData().get("comments");
        System.out.println(album.getFrom().getClass().getName());
        createdTime = album.getCreatedTime();
        updatedTime = album.getUpdatedTime();
        createdBy = new Person(album.getFrom().getId(), album.getFrom().getName());
    }

    public void addPhotos(ArrayList<OurPhoto> listOfPhotos) {
        if (photos != null)
            photos.addAll(listOfPhotos);//=listOfPhotos;
        else
            photos = listOfPhotos;
    }

    //getters and setters


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public LinkedHashMap getLikes() {
        return likes;
    }

    public void setLikes(LinkedHashMap likes) {
        this.likes = likes;
    }

    public LinkedHashMap getComments() {
        return comments;
    }

    public void setComments(LinkedHashMap comments) {
        this.comments = comments;
    }

    public ArrayList<OurPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<OurPhoto> photos) {
        this.photos = photos;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Person getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Person createdBy) {
        this.createdBy = createdBy;
    }
}
