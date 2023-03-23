package rocks.learnercouncil.lchat.bungee;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.chat.ComponentSerializer;
import net.md_5.bungee.event.EventHandler;
import rocks.learnercouncil.lchat.bungee.LChat;

import java.util.UUID;

@SuppressWarnings("UnstableApiUsage")
public class PluginMessageHandler implements Listener {
    private static final LChat plugin = LChat.getInstance();

    /*
    public static void sendPluginMessage(ServerInfo server, String subchannel, String... message) {

        if(plugin.getProxy().getPlayers() == null || plugin.getProxy().getPlayers().isEmpty()) {
            plugin.getLogger().severe(ChatColor.DARK_RED + "No players online, message couldn't be sent: " + subchannel + " | " + Arrays.toString(message));
            return;
        }

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(subchannel);
        Arrays.stream(message).forEach(out::writeUTF);

        server.sendData("lchat:main", out.toByteArray());
    }*/

    @EventHandler
    public void onPluginMessageReceived(PluginMessageEvent e) {
        if(!(e.getTag().equals("lchat:main"))) return;
        ByteArrayDataInput in = ByteStreams.newDataInput(e.getData());
        String subchannel = in.readUTF();
        if(subchannel.equalsIgnoreCase("chat-message")) {
            String message = in.readUTF();
        }
    }
}