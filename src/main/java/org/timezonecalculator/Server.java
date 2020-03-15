package org.timezonecalculator;

import org.timezonecalculator.timezonedb.TimeZoneDbModule;
import org.timezonecalculator.timezonedb.TimeZoneHandler;
import org.timezonecalculator.timezonedb.config.TimeZoneDBConfig;
import ratpack.groovy.template.MarkupTemplateModule;
import ratpack.guice.Guice;
import ratpack.server.RatpackServer;
import ratpack.server.ServerConfig;

public class Server {
    static final String APPLICATION_IS_UP = "Application is Up.";
    private static final String TIME = "time";

    public static void main(String... args) throws Exception {

        RatpackServer server = RatpackServer.of(spec -> {
            ServerConfig serverConfig = getBuild();
            spec
                    .serverConfig(serverConfig)
                    .registry(Guice.registry(registry -> {
                        registry.module(TimeZoneDbModule.class);
                        registry.module(MarkupTemplateModule.class);
                    }))
                    .handlers(chain -> chain
                            .path(TIME, TimeZoneHandler.class)
//                            .all(ctx-> ctx.render(Groovy.groovyMarkupTemplate("index.gtpl"))));
                            .files(f -> {
                                f.dir("public").indexFiles("index.html");
                            }));
        });
        server.start();
    }


    private static ServerConfig getBuild() {
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
