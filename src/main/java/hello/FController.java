package hello;

        import javax.inject.Inject;
        import javax.servlet.http.HttpServlet;
        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;
        import javax.swing.text.html.HTMLDocument;

        import org.apache.catalina.connector.Response;
        import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
        import org.springframework.http.HttpMethod;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.ResponseEntity;
        import org.springframework.social.facebook.api.*;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.PathVariable;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestMethod;
        import org.springframework.social.oauth2.OAuth2Operations;
        import org.springframework.web.bind.annotation.ResponseBody;

        import java.util.ArrayList;
        import java.util.List;
        import java.util.Objects;

@Controller
@EnableAutoConfiguration
@RequestMapping("/")
public class FController {

    private Facebook facebook;

    @Inject
    public FController(Facebook facebook) {
        this.facebook = facebook;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Object helloFacebook() {

        if (!facebook.isAuthorized()) {

                    return "/connect/facebook";
        }

        return "facebookConnected";
    }

    /**
     * returns alist of albums to from Facebook
     * @return
     */
    @RequestMapping(value = "/getalbums", method = RequestMethod.GET)
    public Object getAlbums() {

        if (!facebook.isAuthorized()) {

            return "redirect:/connect/facebook";
        }

        PagedList<Album> albums = facebook
                .mediaOperations()
                .getAlbums();

        Album a= albums.get(0);
        String photos = facebook.mediaOperations().getPhotos(a.getId()).get(0).getSource();

        return new ResponseEntity(albums, HttpStatus.OK);

    }

    /**
     * Returns a list of Photo Links for the Listed album id from the local storage
     * @param albumId
     * @return ArrayList[String]
     */
    @RequestMapping(value = "/{albumId}/photos",method = RequestMethod.GET)
    public Object getPhotos(@PathVariable String albumId){

        MediaOperations media = facebook.mediaOperations();

        PagedList<Photo> listOfPhotos = media.getPhotos(albumId);


        List<String> photos = new ArrayList<String>();

        for (Photo p : listOfPhotos)
        {
            photos.add(p.getSource());

        }

        ImageOperations imageOperations=new ImageOperations();

        //writing the first album's first photo

        imageOperations.writeToMongo(photos.get(0));

        //reading the Image to afile from the database

        imageOperations.readFromMongo();

        return new ResponseEntity<String>("done reading and writing from MongoDB",HttpStatus.OK);
    }

    /**
     * returns albummetadata fro mthe local storage
     * @param request
     * @return
     */
    @RequestMapping(value = "/getalbummeta",method = RequestMethod.GET)
    public Object getAlbumMeta(HttpServletRequest request){

        String albumID = request.getParameter("album_id");
        Album album = facebook.mediaOperations().getAlbum(albumID);

        return new ResponseEntity(album,HttpStatus.OK);
    }


    /**
     * backup a particular album to the personal db
     */
    @RequestMapping(value = "/backup")
    public Object backupAlbum(HttpServletRequest request)
    {
        String albumId= request.getParameter("album_id");
        FbUtils fbUtils= new FbUtils(facebook);
        boolean result= fbUtils.backupAlbum(albumId);

        if (result == true)
            return new ResponseEntity(albumId,HttpStatus.OK);
        else
            return new ResponseEntity(albumId,HttpStatus.REQUEST_TIMEOUT);
    }

}
