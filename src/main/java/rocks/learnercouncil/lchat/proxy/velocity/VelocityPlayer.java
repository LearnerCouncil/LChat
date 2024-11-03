package rocks.learnercouncil.lchat.proxy.velocity;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.Component;
import rocks.learnercouncil.lchat.proxy.common.CommonPlayer;
import rocks.learnercouncil.lchat.proxy.common.commands.ChatMessage;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class VelocityPlayer implements CommonPlayer {
    private final Player player;

    public VelocityPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void sendMessage(ChatMessage message) {
        player.sendMessage(message.velocity());
    }

    @Override
    public void sendLegacyMessage(String legacyMessage) {
        player.sendMessage(Component.text(legacyMessage));
    }

    @Override
    public boolean hasPermission(String permission) {
        return false;
    }

    @Override
    public String getName() {
        return player.getUsername();
    }

    @Override
    public String getServerName() {
        return player.getCurrentServer().map(s -> s.getServerInfo().getName()).orElse("none");
    }

    public static class Factory implements CommonPlayer.Factory {

        private final ProxyServer proxy;

        public Factory(ProxyServer proxy) {
            this.proxy = proxy;
        }

        @Override
        public Optional<CommonPlayer> getPlayer(UUID uuid) {
            return proxy.getPlayer(uuid).map(VelocityPlayer::new);
        }

        @Override
        public Collection<CommonPlayer> getPlayers() {
            return proxy.getAllPlayers().stream().map(VelocityPlayer::new).collect(Collectors.toSet());
        }
    }
}
