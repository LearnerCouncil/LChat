package rocks.learnercouncil.lchat.proxy.bungee;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import rocks.learnercouncil.lchat.proxy.common.ChatFilter;
import rocks.learnercouncil.lchat.proxy.common.CommandSpy;
import rocks.learnercouncil.lchat.proxy.common.CommonPlayer;

import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@SuppressWarnings("UnstableApiUsage")
public class PluginMessageHandler implements Listener {
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
        if(!(e.getTag().equals("lchat:main"))) return;
        ByteArrayDataInput in = ByteStreams.newDataInput(e.getData());
        String subchannel = in.readUTF();
        if(subchannel.equalsIgnoreCase("chat-message")) {
            UUID uuid = UUID.fromString(in.readUTF());
            String message = in.readUTF();
            String rawMessage = in.readUTF();
            ProxiedPlayer sender = plugin.getProxy().getPlayer(uuid);
            Set<ProxiedPlayer> recipients = plugin.getProxy().getPlayers().stream().filter(p -> p.hasPermission("lchat.filter.recipient")).collect(Collectors.toSet());
            if(!sender.hasPermission("lchat.filter.bypass") && ChatFilter.isUnsafe(message)) {
                recipients.forEach(p -> p.sendMessage(new ComponentBuilder()
                        .append("[LChat] ")
                        .color(ChatColor.DARK_PURPLE)
                        .append(sender.getDisplayName() + " said something bad: ")
                        .color(ChatColor.RED)
                        .appendLegacy(rawMessage)
                        .reset()
                        .create()));
            } else {
                plugin.getProxy().getPlayers().forEach(p -> p.sendMessage(TextComponent.fromLegacyText(message)));
            }
            return;
        }
        if(subchannel.equalsIgnoreCase("command")) {
            CommonPlayer.Factory factory = new BungeePlayer.Factory();
            UUID uuid = UUID.fromString(in.readUTF());
            String command = in.readUTF();
            CommandSpy.sendCommandMessage(uuid, command, factory);
        }
    }
}