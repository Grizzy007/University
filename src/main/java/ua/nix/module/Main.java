package ua.nix.module;

import org.flywaydb.core.Flyway;
import ua.nix.module.menu.UI;

public class Main {
    public static void main(String[] args) {
        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:mysql://us-cdbr-east-06.cleardb.net/heroku_fa9a0adde74cfd6?reconnect=true",
                        "b9d4c3dc2b0eed","21a03a1a")
                .baselineOnMigrate(true)
                .locations("db/migration")
                .load();
        flyway.migrate();
        UI ui = new UI();
        ui.start();
    }
}

