package hello;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.social.facebook.api.*;

import java.util.*;

/**
 * Created by kaustubh on 19/04/15.
 */
@Document
public class OurAlbum {
    @Id
    private String _id;
    private String name;
    private ArrayList<OurReference> likes;
    private LinkedHashMap comments;
    private ArrayList<OurPhoto> photos;
    private Date createdTime;
    private Date updatedTime;
    private Person createdBy;

    public OurAlbum() {
    }

    public OurAlbum(String _id, ArrayList<OurReference> likes, LinkedHashMap comments, ArrayList<OurPhoto> photos, Date createdTime, Date updatedTime, Person createdBy, String name) {
        this._id = _id;
        this.likes = likes;
        this.comments = comments;
        this.photos = photos;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
        this.createdBy = createdBy;
        this.name = name;

    }

    public OurAlbum(Album album) {
        _id = album.getId();
        likes = toOurLikes(album);
        //(LinkedHashMap) album.getExtraData().get("likes");
        comments = (LinkedHashMap) album.getExtraData().get("comments");
        System.out.println(album.getFrom().getClass().getName());
        createdTime = album.getCreatedTime();
        updatedTime = album.getUpdatedTime();
        createdBy = new Person(album.getFrom().getId(), album.getFrom().getName());
        name = album.getName();
    }

    public static ArrayList<OurReference> toOurLikes(Album fromFB) {

        //PagedList theLikes = (PagedList) fromFB.getExtraData().get("likes");
        PagedList<Reference> theLikes = FbUtils.facebook.likeOperations().getLikes(fromFB.getId());
        //Reference r= null;

        ArrayList<OurReference> ourLikes = new ArrayList<OurReference>();

        PagingParameters NextPage = theLikes.getNextPage();
        int hasNextPage = 1;

        while (hasNextPage == 1) {
            NextPage = theLikes.getNextPage();
            for (Reference like : theLikes) {
                ourLikes.add(new OurReference(like.getId(), like.getName()));
            }
            if (NextPage != null) {
                hasNextPage = 1;
                theLikes = FbUtils.facebook.likeOperations().getLikes(fromFB.getId(), NextPage);
            } else
                hasNextPage = 0;
        }


        return ourLikes;
    }

    public void addPhotos(ArrayList<OurPhoto> listOfPhotos) {
        if (photos != null)
            photos.addAll(listOfPhotos);//=listOfPhotos;
        else
            photos = listOfPhotos;
    }

    //getters and setters

    public ArrayList<OurReference> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<OurReference> likes) {
        this.likes = likes;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

//    public LinkedHashMap getLikes() {
//        return likes;
//    }
//
//    public void setLikes(LinkedHashMap likes) {
//        this.likes = likes;
//    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
