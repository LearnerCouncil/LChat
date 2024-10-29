package rocks.learnercouncil.lchat.proxy.bungee.events;

import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import rocks.learnercouncil.lchat.proxy.bungee.LChatBungee;
import rocks.learnercouncil.lchat.proxy.bungee.PluginMessageHandler;

public class PlayerJoin implements Listener {

    private static final LChatBungee plugin = LChatBungee.getInstance();

    @EventHandler
    public void onPlayerJoin(ServerConnectedEvent event) {
        plugin.getProxy()
                .getServers()
                .values()
                .forEach(s -> PluginMessageHandler.sendPluginMessage(s, "chat-style", LChatBungee.getConfigFile().getConfig().getString("chat-style")));
    }
}
