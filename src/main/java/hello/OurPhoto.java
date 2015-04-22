package hello;

import org.springframework.data.annotation.Id;
import org.springframework.social.facebook.api.*;

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
    ArrayList<OurReference> likes;
    HashMap<String, Object> comments;
    Person createdBy;

    public static ArrayList<OurPhoto> toOurPhotos(Facebook facebook, String albumId) {

        PagedList<Photo> fromFB = facebook.mediaOperations().getPhotos(albumId);
        ArrayList<OurPhoto> toOurPhotos = new ArrayList<OurPhoto>();

        int hasNextPage = 1;

        while (hasNextPage == 1) {


            for (Photo in : fromFB) {
                toOurPhotos.add(toOurPhoto(in));
            }

            if (fromFB.getNextPage() != null) {
                PagingParameters pm = fromFB.getNextPage();
                fromFB = facebook.mediaOperations().getPhotos(albumId, pm);
                hasNextPage = 1;
            } else
                hasNextPage = 0;
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
        //toOurPhoto.setLikes((HashMap<String, Object>) photoFromFB.getExtraData().get("likes"));
        toOurPhoto.setLikes(toOurLikes(photoFromFB));
        toOurPhoto.setComments((HashMap<String, Object>) photoFromFB.getExtraData().get("comments"));
        toOurPhoto.setCreatedBy(new Person(photoFromFB.getFrom().getId(), photoFromFB.getFrom().getName()));
        return toOurPhoto;

    }


    public static ArrayList<OurReference> toOurLikes(Photo fromFB) {

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

//    public ArrayList<Object> getLikes() {
//
//        return likes;
//    }
//
//    public void setLikes(ArrayList<OurReference> likes) {
//        this.likes = likes;
//    }
// public void setLikes(HashMap<String, Object> likes) {
//       likes this.likes = likes;
//    }


    public ArrayList<OurReference> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<OurReference> likes) {
        this.likes = likes;
    }

    public HashMap<String, Object> getComments() {
        return comments;
    }

    public void setComments(HashMap<String, Object> comments) {
        this.comments = comments;
    }
}
