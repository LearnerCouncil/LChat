package rocks.learnercouncil.lchat.proxy.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import org.slf4j.Logger;
import rocks.learnercouncil.lchat.proxy.velocity.commands.CommandSpyCommand;
import rocks.learnercouncil.lchat.proxy.velocity.commands.LcCommand;

@Plugin(id = "lchat",
        version = "1.1",
        description = "A simple proxy-compatible chat utility plugin",
        authors = { "h2ofiremaster" })
public final class LChatVelocity {
    private final @Getter ProxyServer proxy;
    private final @Getter Logger logger;

    @Inject
    public LChatVelocity(ProxyServer proxy, Logger logger) {
        this.proxy = proxy;
        this.logger = logger;
    }

    @Subscribe
    public void onProxyInitialize(ProxyInitializeEvent event) {
        CommandSpyCommand.register(this);
        LcCommand.register(this);
    }
}
