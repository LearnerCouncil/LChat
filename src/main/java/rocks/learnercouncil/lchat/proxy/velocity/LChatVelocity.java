package rocks.learnercouncil.lchat.proxy.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import org.slf4j.Logger;
import rocks.learnercouncil.lchat.proxy.common.ChatFilter;
import rocks.learnercouncil.lchat.proxy.common.CommandSpy;
import rocks.learnercouncil.lchat.proxy.common.BasicLogger;
import rocks.learnercouncil.lchat.proxy.common.ConfigFile;
import rocks.learnercouncil.lchat.proxy.velocity.commands.CommandSpyCommand;
import rocks.learnercouncil.lchat.proxy.velocity.commands.LChatCommand;
import rocks.learnercouncil.lchat.proxy.velocity.commands.LcCommand;

import java.nio.file.Path;

@Getter
@Plugin(id = "lchat",
        version = "1.1",
        description = "A simple proxy-compatible chat utility plugin",
        authors = { "h2ofiremaster" })
public final class LChatVelocity {
    private final ProxyServer proxy;
    private final Logger logger;
    private final Path dataFolder;

    @Inject
    public LChatVelocity(ProxyServer proxy, Logger logger, @DataDirectory Path dataFolder) {
        this.proxy = proxy;
        this.logger = logger;
        this.dataFolder = dataFolder;
    }

    @Subscribe
    public void onProxyInitialize(ProxyInitializeEvent event) {
        BasicLogger basicLogger = new VelocityLogger(logger);
        ConfigFile config = new ConfigFile("config.yml", dataFolder.toFile(), basicLogger);

        ChatFilter.initialize(config);
        CommandSpy.initialize(config);

        CommandSpyCommand.register(this);
        LcCommand.register(this);
        LChatCommand.register(this);
    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {

    }
}
