package ua.nix.module.menu;

import java.io.BufferedReader;
import java.io.IOException;

public enum Instructions {
    CREATE("Create university object", new Create()),
    READ("Print university object", new Read()),
    TASK("Complete task", new Task());
    private final String description;
    private final Command command;

    Instructions(String description, Command command) {
        this.description = description;
        this.command = command;
    }

    public String getDescription() {
        return description;
    }

    public Command getCommand() {
        return command;
    }

    public Command execute(BufferedReader reader) throws IOException {
        if (command != null) {
            command.execute(reader);
        }
        return command;
    }
}
