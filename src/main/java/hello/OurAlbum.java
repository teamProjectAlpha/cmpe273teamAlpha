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



    public OurAlbum(Album album) {

    }

    public void add(PagedList<Photo> listOfPhotos) {

        photos= (PagedList<Photo>) listOfPhotos;


    }
}
