package org.timezonecalculator;

import org.timezonecalculator.timezonedb.TimeZoneDbModule;
import org.timezonecalculator.timezonedb.handlers.TimeZoneHandler;
import org.timezonecalculator.timezonedb.config.TimeZoneDBConfig;
import ratpack.guice.Guice;
import ratpack.server.RatpackServer;
import ratpack.server.ServerConfig;

public final class Server {
    private static final String TIME = "time";

    public static void main(String... args) throws Exception {

        RatpackServer server = RatpackServer.of(spec -> {
            ServerConfig serverConfig = getConfig();
            spec
                    .serverConfig(serverConfig)
                    .registry(Guice.registry(registry -> registry.module(TimeZoneDbModule.class)))
                    .handlers(chain -> chain
                            .path(TIME, TimeZoneHandler.class)
                            .files(f -> f.dir("public").indexFiles("index.html")));
        });
        server.start();
    }


    private static ServerConfig getConfig() {
        return ServerConfig
                .embedded()
                .port(5050)
                .findBaseDir()
                .development(true)
                .json(TimeZoneDBConfig.getJsonConfigPath())
                .require("/app", TimeZoneDBConfig.class)
                .build();
    }
}
