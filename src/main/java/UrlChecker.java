import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * UrlChecker is a simple utility to check if it's possible to connect
 * and get data from a (web) server from Java.
 *
 * The main use of UrlChecker is to check sites with SSL (HTTPS),
 * because they sometimes require installation of root
 * cerifitcaes. Either because the Java in use is old or because the
 * site's cerificate is self-signed.
 *
 * You can also use UrlChecker to check whether access to external
 * sites is restricted, i.e. by a firewall.
 *
 * Using the file: protocol you can even check if a file is readable
 * from Java.
 *
 * Examples
 *
 *    $ java UrlChecker http://google.com/
 *    Got 11080 bytes from http://google.com/
 *
 *    $ java UrlChecker https://google.com/
 *    Got 11099 bytes from https://google.com/
 *
 *    $ java UrlChecker http://google.coma/
 *    java.net.UnknownHostException: google.coma
 *
 *    $ java UrlChecker ftp://google.com
 *    java.net.ConnectException: Connection timed out: connect
 *
 *    $ java UrlChecker google.com
 *    java.net.MalformedURLException: no protocol: google.com
 *
 *    $ java UrlChecker http:
 *    java.lang.IllegalArgumentException: protocol = http host = null
 *
 *    $ java UrlChecker file:UrlChecker.class
 *    Got 1797 bytes from file:UrlChecker.class
 *
 *    $ java UrlChecker file:UrlChecker.clazz
 *    java.io.FileNotFoundException: UrlChecker.not (The system cannot find the file specified)
 *
 * @author <a href="soren@lund.org">Søren Lund</a>
 * @version 1.0
 */
public class UrlChecker {

    public static void main (String[] args) {
        if (args.length == 1) {
            try {
                System.out.println("Got " + new UrlChecker().get(args[0]).length() +
                                   " bytes from " + args[0]);
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        } else {
            System.err.println("USAGE: java " + UrlChecker.class.getName() + " <URL>");
        }
    }

    public String get(String url) throws MalformedURLException, IOException {
        StringBuffer buffer = new StringBuffer();
        InputStream is = null;
        try {
            is = new URL(url).openStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            for (String l = in.readLine(); l != null; l = in.readLine()) buffer.append(l);
        } finally {
            if (is != null) is.close();
        }
        return buffer.toString();
    }
}
