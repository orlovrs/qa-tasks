package ui.config;

import io.github.cdimascio.dotenv.Dotenv;

public class Environment {
    static Dotenv env;

    static {
        env = Dotenv.load();
    }

    public static String BROWSER = env.get("BROWSER");
}
