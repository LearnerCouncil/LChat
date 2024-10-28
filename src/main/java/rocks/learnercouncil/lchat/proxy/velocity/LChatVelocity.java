package rocks.learnercouncil.lchat.proxy.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import rocks.learnercouncil.lchat.proxy.velocity.commands.CommandSpyCommand;

@Plugin(id = "lchat", version = "1.1")
public final class LChatVelocity {
    private final ProxyServer proxy;

    @Inject
    public LChatVelocity(ProxyServer proxy) {
        this.proxy = proxy;
    }

    @Subscribe
    public void onProxyInitialize(ProxyInitializeEvent event) {
        CommandSpyCommand.register(proxy);
    }
}
