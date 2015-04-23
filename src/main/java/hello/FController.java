package hello;

import javax.inject.Inject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.HTMLDocument;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
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
@ComponentScan
@RequestMapping("/")
public class FController {

    private Facebook facebook;

    @Autowired
    private FbUtils fbUtils;
    @Autowired
    private DAO dbUtils;

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
     *
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

        return new ResponseEntity(albums, HttpStatus.OK);

    }

    /**
     * Returns a list of Photo Links for the Listed album id from the local storage
     *
     * @param albumId
     * @return ArrayList[String]
     */
    @RequestMapping(value = "/{albumId}", method = RequestMethod.GET)
    public Object getPhotos(@PathVariable String albumId) {

        ArrayList<OurPhoto> ourPhotos = fbUtils.getOurPhotos(albumId);

        if (ourPhotos != null)
            return new ResponseEntity(ourPhotos, HttpStatus.OK);
        else
            return new ResponseEntity(null, HttpStatus.NO_CONTENT);
    }

    /**
     * returns albummetadata from the local storage
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getalbummeta", method = RequestMethod.GET)
    public Object getAlbumMeta(HttpServletRequest request) {

        String albumID = request.getParameter("album_id");
        Album album = facebook.mediaOperations().getAlbum(albumID);

        return new ResponseEntity(album, HttpStatus.OK);
    }


    /**
     * backup a particular album to the personal db
     */
    @RequestMapping(value = "/backup")
    public Object backupAlbum(HttpServletRequest request) {
        String albumId = request.getParameter("album_id");
        //= new FbUtils(facebook);
        boolean result = fbUtils.backupAlbum(albumId);

        if (result == true)
            return new ResponseEntity(dbUtils.getAlbum(albumId), HttpStatus.OK);
        else
            return new ResponseEntity(albumId + " adding failed", HttpStatus.REQUEST_TIMEOUT);
    }

    @RequestMapping(value = "/{albumId}/photos/{photoId}", method = RequestMethod.GET)
    public Object getPhotoURL(@PathVariable String albumId, @PathVariable String photoId) {

        String imageURL = null;
        imageURL = fbUtils.getImageURL(albumId, photoId);
        if (imageURL != null)
            return new ResponseEntity(imageURL, HttpStatus.OK);
        else
            return new ResponseEntity(null, HttpStatus.NO_CONTENT);
    }


}
