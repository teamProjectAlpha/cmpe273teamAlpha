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
    LinkedHashMap likes;
    LinkedHashMap comments;
    ArrayList<OurPhoto> photos;
    //Reference from;
    Date createdTime;
    Date updatedTime;

    public OurAlbum (){}
    public OurAlbum(String _id, LinkedHashMap likes, LinkedHashMap comments, ArrayList<OurPhoto> photos/*, Reference from*/, Date createdTime, Date updatedTime) {
        this._id = _id;
        this.likes = likes;
        this.comments = comments;
        this.photos = photos;
       // this.from = from;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }

    public OurAlbum(Album album) {
        _id=album.getId();
        likes= (LinkedHashMap) album.getExtraData().get("likes");
        comments= (LinkedHashMap) album.getExtraData().get("comments");
       // from=album.getFrom();
        System.out.println(album.getFrom().getClass().getName());
        createdTime= album.getCreatedTime();
        updatedTime= album.getUpdatedTime();
    }

    public void addPhotos(ArrayList<OurPhoto> listOfPhotos) {
        if(photos!=null)
            photos.addAll(listOfPhotos);//=listOfPhotos;
        else
            photos=listOfPhotos;
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

//    public Reference getFrom() {
//        return from;
//    }
//
//    public void setFrom(Reference from) {
//        this.from = from;
//    }

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
}
