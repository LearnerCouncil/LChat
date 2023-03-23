package rocks.learnercouncil.lchat.spigot.events;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import rocks.learnercouncil.lchat.spigot.LChat;
import rocks.learnercouncil.lchat.spigot.handlers.PluginMessageHandler;

public class PlayerChat implements Listener {

    public static String chatStyle = "";
    private final LChat plugin = LChat.getInstance();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (chatStyle == null || chatStyle.isEmpty()) chatStyle = "&f<%player_name%> ";
        String msg = ChatColor.translateAlternateColorCodes('&',
                        PlaceholderAPI.setPlaceholders(event.getPlayer(), chatStyle)
                ) + event.getMessage();
        PluginMessageHandler.sendPluginMessage(event.getPlayer(), "chat-message", event.getPlayer().getUniqueId().toString(), msg);
        plugin.getLogger().info("[CHAT] " + msg);
        event.setCancelled(true);
    }
}
