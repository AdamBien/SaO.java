import java.awt.Desktop;
import java.io.IOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.file.Path;

import com.sun.net.httpserver.SimpleFileServer;
import com.sun.net.httpserver.SimpleFileServer.OutputLevel;

public class SaO {

    private static Logger LOG = System.getLogger(SaO.class.getName());

    public static void main(String... args) throws IOException, InterruptedException {
        var port = 3000;
        var loopback = new InetSocketAddress(InetAddress.getLoopbackAddress(), port);
        var path = Path.of(".").toAbsolutePath();
        var webServer = SimpleFileServer.createFileServer(loopback, path, OutputLevel.VERBOSE);
        webServer.start();
        var url = "http://%s:%d".formatted(
                webServer.getAddress().getHostString(),
                webServer.getAddress().getPort());
        LOG.log(Level.INFO, url);
        Browser.open(url);
        LOG.log(Level.INFO, "browser opened ");
    }
}

interface Browser {
    static void open(String uriString) throws IOException {
        var uri = URI.create(uriString);
        Desktop.getDesktop().browse(uri);
    }
}

enum OS {

    MAC,LINUX,WINDOWS;

    static OS detect() {
        var os = System.getProperty("os.name")
                .toLowerCase();
        if (os.contains("mac")) {
            return MAC;
        }
        if (os.contains("nix")) {
            return LINUX;
        }
        if (os.contains("win")) {
            return WINDOWS;
        }
        throw new IllegalArgumentException("Unknown OS: " + os);
    }

}
