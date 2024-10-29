package rocks.learnercouncil.lchat.proxy.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.slf4j.Logger;
import rocks.learnercouncil.lchat.proxy.bungee.ChatFilter;
import rocks.learnercouncil.lchat.proxy.bungee.CommandSpy;
import rocks.learnercouncil.lchat.proxy.velocity.commands.CommandSpyCommand;
import rocks.learnercouncil.lchat.proxy.velocity.commands.LChatCommand;
import rocks.learnercouncil.lchat.proxy.velocity.commands.LcCommand;

@Getter
@Plugin(id = "lchat",
        version = "1.1",
        description = "A simple proxy-compatible chat utility plugin",
        authors = { "h2ofiremaster" })
public final class LChatVelocity {
    private final ProxyServer proxy;
    private final Logger logger;

    @Inject
    public LChatVelocity(ProxyServer proxy, Logger logger) {
        this.proxy = proxy;
        this.logger = logger;
    }

    @Subscribe
    public void onProxyInitialize(ProxyInitializeEvent event) {
        ChatFilter.initialize();
        CommandSpy.initialize();

        CommandSpyCommand.register(this);
        LcCommand.register(this);
        LChatCommand.register(this);
    }
}
