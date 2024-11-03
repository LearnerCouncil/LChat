package rocks.learnercouncil.lchat.proxy.bungee;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import rocks.learnercouncil.lchat.proxy.common.CommonPlayer;
import rocks.learnercouncil.lchat.proxy.common.commands.ChatMessage;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class BungeePlayer implements CommonPlayer {

    private static final LChatBungee plugin = LChatBungee.getInstance();
    private final ProxiedPlayer player;

    public BungeePlayer(ProxiedPlayer player) {
        this.player = player;
    }

    @Override
    public void sendMessage(ChatMessage message) {
        player.sendMessage(message.bungee());
    }

    @Override
    public void sendLegacyMessage(String legacyMessage) {
        player.sendMessage(TextComponent.fromLegacyText(legacyMessage));
    }

    @Override
    public boolean hasPermission(String permission) {
        return player.hasPermission(permission);
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public String getServerName() {
        return player.getServer().getInfo().getName();
    }

    public static class Factory implements CommonPlayer.Factory {

        @Override
        public Optional<CommonPlayer> getPlayer(UUID uuid) {
            ProxiedPlayer player = plugin.getProxy().getPlayer(uuid);
            if (player == null) return Optional.empty();
            return Optional.of(new BungeePlayer(player));
        }

        @Override
        public Collection<CommonPlayer> getPlayers() {
            return plugin.getProxy().getPlayers().stream().map(BungeePlayer::new).collect(Collectors.toSet());
        }
    }
}
