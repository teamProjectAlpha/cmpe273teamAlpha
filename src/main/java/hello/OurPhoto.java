package hello;

import com.mongodb.DBObject;
import org.springframework.data.annotation.Id;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.PagingParameters;
import org.springframework.social.facebook.api.Photo;
import org.springframework.social.facebook.api.Tag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kaustubh on 20/04/15.
 */
public class OurPhoto {
    @Id
    String _id;
    String name;
    String source;
    Date createdTime;
    List<Tag> tags;
    HashMap<String, Object> likes;
    HashMap<String, Object> comments;
    Person createdBy;

    public static ArrayList<OurPhoto> toOurPhotos(PagedList<Photo> fromFB) {

        ArrayList<OurPhoto> toOurPhotos = new ArrayList<OurPhoto>();
        OurPhoto temp = null;
        System.out.println();
        for (Photo in : fromFB) { // TODO add traversal of paged list
            toOurPhotos.add(toOurPhoto(in));
            //PagingParameters next=fromFB.getNextPage();
        }

        return toOurPhotos;//fromFB;
    }

    public static OurPhoto toOurPhoto(Photo photoFromFB) {


        OurPhoto toOurPhoto = new OurPhoto();
        //  temp.set_id(in.getFrom());
        toOurPhoto.set_id(photoFromFB.getId());
        toOurPhoto.setName(photoFromFB.getName());
        toOurPhoto.setSource(photoFromFB.getSource());
        toOurPhoto.setCreatedTime(photoFromFB.getCreatedTime());
        toOurPhoto.setTags(photoFromFB.getTags());
        toOurPhoto.setLikes((HashMap<String, Object>) photoFromFB.getExtraData().get("likes"));
        toOurPhoto.setComments((HashMap<String, Object>) photoFromFB.getExtraData().get("comments"));
        toOurPhoto.setCreatedBy(new Person(photoFromFB.getFrom().getId(),photoFromFB.getFrom().getName()));
        return toOurPhoto;

    }


    //getter setters


    public Person getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Person createdBy) {
        this.createdBy = createdBy;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
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
}
