package me.blueslime.slimeplugin.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.scheduler.Scheduler;
import dev.mruniverse.slimelib.SlimePlugin;
import dev.mruniverse.slimelib.SlimePluginInformation;
import dev.mruniverse.slimelib.file.configuration.ConfigurationHandler;
import dev.mruniverse.slimelib.logs.SlimeLogger;
import dev.mruniverse.slimelib.logs.SlimeLogs;
import me.blueslime.slimeplugin.velocity.exceptions.NotFoundLanguageException;
import me.blueslime.slimeplugin.velocity.loader.PluginLoader;
import me.blueslime.slimeplugin.velocity.loader.PluginLoaderDelay;
import org.slf4j.Logger;

import java.io.File;
import java.nio.file.Path;
import java.time.Duration;
import java.util.concurrent.TimeUnit;


@Plugin(
        id = "slimeplugin",
        name = "SlimePlugin",
        version = "1.0.0-SNAPSHOT",
        description = "Simple Slime Plugin",
        url = "https://github.com/MrUniverse44/SlimePluginVelocity",
        authors = { "JustJustin" }
)
public class Main implements SlimePlugin<ProxyServer> {

    private SlimePluginInformation information;

    private PluginLoader loader;

    private SlimeLogs logs;

    private File directory;

    @Inject
    private ProxyServer server;

    @Inject
    @DataDirectory
    private Path dataDirectory;

    @Inject
    private Logger logger;

    @Subscribe
    public void onInitialize(ProxyInitializeEvent event) {

        File directory = dataDirectory.getParent().toFile();

        this.directory = new File(directory, "SlimePlugin");

        this.logs = SlimeLogger.createLogs(
                getServerType(),
                this
        );

        this.information = new SlimePluginInformation(
                getServerType(),
                this
        );

        this.loader = new PluginLoader(this);

        Scheduler.TaskBuilder builder = server.getScheduler().buildTask(
                this,
                new PluginLoaderDelay(this)
        );

        builder.delay(1L, TimeUnit.SECONDS);

        builder.schedule();
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
    public File getDataFolder() {
        return directory;
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
    public ProxyServer getPlugin() {
        return server;
    }

    @Override
    public void reload() {
        loader.reload();
    }
}
