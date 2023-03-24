package rocks.learnercouncil.lchat.spigot.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import rocks.learnercouncil.lchat.spigot.PluginMessageHandler;

public class PlayerCommandPreprocess implements Listener {

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        PluginMessageHandler.sendPluginMessage(event.getPlayer(), "command", event.getMessage());
    }
}
