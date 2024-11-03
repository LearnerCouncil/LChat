package rocks.learnercouncil.lchat.proxy.bungee;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import rocks.learnercouncil.lchat.proxy.common.CommandSpy;
import rocks.learnercouncil.lchat.proxy.common.Messenger;
import rocks.learnercouncil.lchat.proxy.common.CommonPlayer;

import java.util.Arrays;
import java.util.UUID;

@SuppressWarnings("UnstableApiUsage")
public class BungeeMessenger implements Listener {
    private static final LChatBungee plugin = LChatBungee.getInstance();

    public static void sendPluginMessage(ServerInfo server, String subchannel, String... message) {

        if(plugin.getProxy().getPlayers() == null || plugin.getProxy().getPlayers().isEmpty()) {
            plugin.getLogger().severe(ChatColor.DARK_RED + "No players online, message couldn't be sent: " + subchannel + " | " + Arrays.toString(message));
            return;
        }

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(subchannel);
        Arrays.stream(message).forEach(out::writeUTF);

        server.sendData("lchat:main", out.toByteArray());
    }

    @EventHandler
    public void onPluginMessageReceived(PluginMessageEvent e) {
        Messenger.receivePluginMessage(e.getTag(), e.getData(), new BungeePlayer.Factory());
    }
}