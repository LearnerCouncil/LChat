package rocks.learnercouncil.lchat.spigot.handlers;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.entity.Player;
import rocks.learnercouncil.lchat.spigot.LChat;

public class ProtocolHandler {

    private static final ProtocolManager protocolManager = LChat.getProtocolManager();
    private static final LChat plugin = LChat.getInstance();

    public static void addPacketListeners() {
        protocolManager.addPacketListener(new PacketAdapter(
                plugin,
                ListenerPriority.NORMAL,
                PacketType.Play.Client.CHAT
        ) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();
                String message = event.getPacket().getStrings().read(0);
                PluginMessageHandler.sendPluginMessage(player, "message", player.getUniqueId().toString(), message);
            }
        });

    }

}
