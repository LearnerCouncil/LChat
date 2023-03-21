package rocks.learnercouncil.lchat.spigot.handlers;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.entity.Player;
import rocks.learnercouncil.lchat.spigot.LChat;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProtocolHandler {

    private static final ProtocolManager protocolManager = LChat.getProtocolManager();
    private static final LChat plugin = LChat.getInstance();

    public static void addPacketListeners() {
        protocolManager.addPacketListener(new PacketAdapter(
                plugin,
                ListenerPriority.HIGH,
                PacketType.Play.Client.CHAT
        ) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();
                PacketContainer packet = event.getPacket();
                String message = event.getPacket().getStrings().readSafely(0);
                System.out.println("Client: " + packet);
               //String string = packet.getStrings().readSafely(0);
               //List<String> strings = packet.getStrings().getValues();
               //WrappedChatComponent chatComponent = packet.getChatComponents().readSafely(0);
               //List<WrappedChatComponent> chatComponents = packet.getChatComponents().getValues();
               //WrappedChatComponent[] chatComponentArray = packet.getChatComponentArrays().readSafely(0);
               //List<WrappedChatComponent[]> chatComponentArrays = packet.getChatComponentArrays().getValues();
               //System.out.println("Client Packet Received: " + player + "\n" +
               //        "string: " + string + ",\n" +
               //        "strings: " + strings + ",\n" +
               //        "chatComponent: " +chatComponent + ",\n" +
               //        "chatComponents: " +chatComponents + ",\n" +
               //        "chatComponentArray: " + Arrays.toString(chatComponentArray) + ",\n" +
               //        "chatComponentArrays: " + chatComponentArrays + ".\n");

                PluginMessageHandler.sendPluginMessage(player, "message", player.getUniqueId().toString(), message);
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(
                plugin,
                ListenerPriority.HIGH,
                PacketType.Play.Server.CHAT
        ) {
            @Override
            public void onPacketSending(PacketEvent event) {
                Player player = event.getPlayer();
                PacketContainer packet = event.getPacket();

                System.out.println("Server: " + packet);
                //String string = packet.getStrings().readSafely(0);
                //List<String> strings = packet.getStrings().getValues();
                //WrappedChatComponent chatComponent = packet.getChatComponents().readSafely(0);
                //List<WrappedChatComponent> chatComponents = packet.getChatComponents().getValues();
                //WrappedChatComponent[] chatComponentArray = packet.getChatComponentArrays().readSafely(0);
                //List<WrappedChatComponent[]> chatComponentArrays = packet.getChatComponentArrays().getValues();
                //System.out.println("Server Packet Received: " + player + "\n" +
                //        "string: " + string + ",\n" +
                //        "strings: " + strings + ",\n" +
                //        "chatComponent: " +chatComponent + ",\n" +
                //        "chatComponents: " +chatComponents + ",\n" +
                //        "chatComponentArray: " + Arrays.toString(chatComponentArray) + ",\n" +
                //        "chatComponentArrays: " + chatComponentArrays + ".\n");
            }
        });

    }

}
