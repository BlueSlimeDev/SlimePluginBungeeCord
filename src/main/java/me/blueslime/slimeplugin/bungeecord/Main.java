package me.blueslime.slimeplugin.bungeecord;

import dev.mruniverse.slimelib.SlimePlugin;
import dev.mruniverse.slimelib.SlimePluginInformation;
import dev.mruniverse.slimelib.file.configuration.ConfigurationHandler;
import dev.mruniverse.slimelib.logs.SlimeLogger;
import dev.mruniverse.slimelib.logs.SlimeLogs;
import me.blueslime.slimeplugin.bungeecord.exceptions.NotFoundLanguageException;
import me.blueslime.slimeplugin.bungeecord.loader.PluginLoader;
import me.blueslime.slimeplugin.bungeecord.loader.PluginLoaderDelay;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.concurrent.TimeUnit;

public class Main extends Plugin implements SlimePlugin<Plugin> {

    private SlimePluginInformation information;

    private PluginLoader loader;

    private SlimeLogs logs;

    @Override
    public void onEnable() {
        this.logs = SlimeLogger.createLogs(
                getServerType(),
                this
        );

        this.information = new SlimePluginInformation(
                getServerType(),
                this
        );

        this.loader = new PluginLoader(this);

        getProxy().getScheduler().schedule(
                this,
                new PluginLoaderDelay(this),
                1L,
                TimeUnit.SECONDS
        );
    }

    public ConfigurationHandler getMessages() {
        ConfigurationHandler configuration = getLoader().getMessages();

        if (configuration == null) {
            exception();
        }

        return configuration;
    }

    private void exception() {
        new NotFoundLanguageException("The current language in the settings file doesn't exists, probably you will see errors in console").printStackTrace();
    }

    @Override
    public SlimePluginInformation getPluginInformation() {
        return information;
    }

    @Override
    public PluginLoader getLoader() {
        return loader;
    }

    @Override
    public SlimeLogs getLogs() {
        return logs;
    }

    @Override
    public Plugin getPlugin() {
        return this;
    }

    @Override
    public void reload() {
        loader.reload();
    }
}
