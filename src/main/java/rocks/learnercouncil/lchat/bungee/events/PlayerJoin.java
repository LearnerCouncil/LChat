package rocks.learnercouncil.lchat.bungee.events;

import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import rocks.learnercouncil.lchat.bungee.LChat;
import rocks.learnercouncil.lchat.bungee.PluginMessageHandler;

public class PlayerJoin implements Listener {

    private static final LChat plugin = LChat.getInstance();

    @EventHandler
    public void onPlayerJoin(ServerConnectedEvent event) {
        plugin.getProxy()
                .getServers()
                .values()
                .forEach(s -> PluginMessageHandler.sendPluginMessage(s, "chat-style", LChat.getConfigFile().getConfig().getString("chat-style")));
    }
}
