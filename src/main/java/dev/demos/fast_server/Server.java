package dev.demos.fast_server;

import dev.demos.fast_server.resources.Database;
import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


@ApplicationPath("api")
public class Server extends Application {
            
    @PostConstruct
    public void init() {
        Database.iniciar();
    }

}


