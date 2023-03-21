package rocks.learnercouncil.lchat.spigot.handlers;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;
import rocks.learnercouncil.lchat.spigot.LChat;

import java.util.Arrays;

@SuppressWarnings("UnstableApiUsage")
public class PluginMessageHandler implements PluginMessageListener {

    public static final LChat plugin = LChat.getInstance();


    public static void sendPluginMessage(Player p, String subchannel, String... message) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(subchannel);
        Arrays.stream(message).forEach(out::writeUTF);
        p.sendPluginMessage(plugin, "cameron:main", out.toByteArray());
    }

    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, byte[] message) {
        //if(!(channel.equals("lchat:main"))) return;
        //ByteArrayDataInput in = ByteStreams.newDataInput(message);
        //String subchannel = in.readUTF();
    }
}
