package org.example;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 */
public class App {

    private Config config;

    public static void main(String[] args) {
        App app = new App();
        app.parseConfig();
        app.startServers();
    }

    private void parseConfig() {
        Yaml yaml = new Yaml();
        try {
            config = yaml.loadAs(Files.newBufferedReader(Paths.get(getClass().getResource("/config.yml").toURI())), Config.class);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void startServers() {
        for (HttpConfig config : config.getHttp()) {
            HttpServer httpServer = new HttpServer(config);
            new Thread(httpServer::start).start();
        }
        config.getSocket().forEach(socketConfig -> {
            SocketServer socketServer = new SocketServer(socketConfig);
            new Thread(socketServer::start).start();
        });
    }
}
