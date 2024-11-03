package rocks.learnercouncil.lchat.proxy.common;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import rocks.learnercouncil.lchat.proxy.bungee.BungeePlayer;
import rocks.learnercouncil.lchat.proxy.common.commands.ChatMessage;
import rocks.learnercouncil.lchat.proxy.common.commands.Permissions;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class Messenger {

    public static final String MAIN_CHANNEL = "lchat:main";
    public static final String CHAT_MESSAGE_CHANNEL = "chat-message";
    public static final String COMMAND_CHANNEL = "command";

    @SuppressWarnings("UnstableApiUsage")
    public static void receivePluginMessage(String channel, byte[] bytes, CommonPlayer.Factory playerFactory) {
        if (!channel.equals(MAIN_CHANNEL)) return;
        ByteArrayDataInput input = ByteStreams.newDataInput(bytes);
        String subchannel = input.readUTF();
        if(subchannel.equalsIgnoreCase(Messenger.CHAT_MESSAGE_CHANNEL)) {
            Messenger.handleChatMessage(input, playerFactory);
        }
        if(subchannel.equalsIgnoreCase(Messenger.COMMAND_CHANNEL)) {
            Messenger.handleCommand(input, playerFactory);
        }
    }

    public static void handleChatMessage(ByteArrayDataInput input, CommonPlayer.Factory playerFactory) {
            UUID uuid = UUID.fromString(input.readUTF());
            String message = input.readUTF();
            String rawMessage = input.readUTF();
            Optional<CommonPlayer> optionalSender = playerFactory.getPlayer(uuid);
            if (optionalSender.isEmpty()) return;
            CommonPlayer sender = optionalSender.get();
            Set<CommonPlayer> admins = playerFactory.getPlayers().stream()
                    .filter(p -> p.hasPermission(Permissions.FILTER_RECIPIENT))
                    .collect(Collectors.toSet());
            if(!sender.hasPermission(Permissions.FILTER_BYPASS) && ChatFilter.isUnsafe(message)) {
                admins.forEach(p -> p.sendMessage(new ChatMessage.Builder("[LChat] ", ChatMessage.Color.DARK_PURPLE)
                        .append(sender.getName() + " said something bad: ", ChatMessage.Color.RED)
                        .append(rawMessage, ChatMessage.Color.WHITE)
                        .build()));
            } else {
                playerFactory.getPlayers().forEach(p -> p.sendLegacyMessage(message));
            }

    }

    public static void handleCommand(ByteArrayDataInput input, CommonPlayer.Factory playerFactory) {
        UUID uuid = UUID.fromString(input.readUTF());
        String command = input.readUTF();
        CommandSpy.sendCommandMessage(uuid, command, playerFactory);
    }
}
