import java.io.IOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.file.Path;

import com.sun.net.httpserver.SimpleFileServer;
import com.sun.net.httpserver.SimpleFileServer.OutputLevel;

interface SaO {

    Logger LOG = System.getLogger(SaO.class.getName());

    static void main(String... args) throws IOException, InterruptedException {
        var port = 3000;
        var loopback = new InetSocketAddress(InetAddress.getLoopbackAddress(), port);
        var path = Path.of(".").toAbsolutePath();
        var webServer = SimpleFileServer.createFileServer(loopback, path, OutputLevel.VERBOSE);
        webServer.start();
        var url = "http://%s:%d".formatted(
                webServer.getAddress().getHostString(),
                webServer.getAddress().getPort());
        LOG.log(Level.INFO, url);
        var browser = new ProcessBuilder("open", "-a","Firefox",url);
        browser.start();
        LOG.log(Level.INFO, "browser opened ");
    }
}