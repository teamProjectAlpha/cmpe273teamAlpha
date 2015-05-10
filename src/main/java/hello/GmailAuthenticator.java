package hello;

/**
 * Created by Adwait on 5/9/2015.
 */

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;


class GmailAuthenticator extends Authenticator {
    String user;
    String pw;

    public GmailAuthenticator(String username, String password) {
        super();
        this.user = username;
        this.pw = password;
    }

    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, pw);
    }
}