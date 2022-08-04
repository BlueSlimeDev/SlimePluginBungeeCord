package me.blueslime.slimeplugin.velocity.loader;

import me.blueslime.slimeplugin.velocity.Main;
import me.blueslime.slimeplugin.velocity.SlimeFile;
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
