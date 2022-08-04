package me.blueslime.slimeplugin.bungeecord.loader;

import me.blueslime.slimeplugin.bungeecord.Main;
import me.blueslime.slimeplugin.bungeecord.SlimeFile;
public class PluginLoaderDelay implements Runnable {

    private final Main main;

    public PluginLoaderDelay(Main main) {
        this.main = main;
    }

    @Override
    public void run() {
        main.getLoader().setFiles(SlimeFile.class);

        main.getLoader().init();
    }
}
