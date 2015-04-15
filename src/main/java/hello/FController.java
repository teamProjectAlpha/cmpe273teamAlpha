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

    @RequestMapping(value = "/getalbums", method = RequestMethod.GET)
    public Object getAlbums() {

        if (!facebook.isAuthorized()) {

            return "redirect:/connect/facebook";
        }

        PagedList<Album> albums = facebook
                .mediaOperations()
                .getAlbums();

        Album a= albums.get(1);
        String photos = facebook.mediaOperations().getPhotos(a.getId()).get(0).getSource();

        return new ResponseEntity(albums, HttpStatus.OK);

    }

    /**
     * Returns a list of Photo Links for the Listed album id
     * @param albumId
     * @return ArrayList[String]
     */
    @RequestMapping(value = "/{albumId}/photos",method = RequestMethod.GET)
    public Object getPhotos(@PathVariable String albumId){
        MediaOperations media = facebook.mediaOperations();

        PagedList<Photo> listOfPhotos = media.getPhotos(albumId);


        List<String> photos = new ArrayList<>();

        for (Photo p : listOfPhotos)
        {
            photos.add(p.getSource());

        }
        return new ResponseEntity<List>(photos,HttpStatus.OK);
    }

    /**
     * returns albummetadata
     * @param request
     * @return
     */
    @RequestMapping(value = "/getalbummeta",method = RequestMethod.GET)
    public Object getAlbumMeta(HttpServletRequest request){

        String albumID = request.getParameter("album_id");
        Album album = facebook.mediaOperations().getAlbum(albumID);

        return new ResponseEntity(album,HttpStatus.OK);
    }

}
