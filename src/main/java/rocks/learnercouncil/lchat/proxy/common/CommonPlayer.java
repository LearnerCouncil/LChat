package rocks.learnercouncil.lchat.proxy.common;

import rocks.learnercouncil.lchat.proxy.common.commands.ChatMessage;

import java.util.Optional;
import java.util.UUID;

public interface CommonPlayer {
    void sendMessage(ChatMessage message);
    String getName();
    String getServerName();

    interface Factory {
        Optional<CommonPlayer> getPlayer(UUID uuid);
    }
}
