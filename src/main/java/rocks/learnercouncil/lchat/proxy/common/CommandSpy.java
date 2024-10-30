package rocks.learnercouncil.lchat.proxy.common;

import rocks.learnercouncil.lchat.proxy.bungee.LChatBungee;
import rocks.learnercouncil.lchat.proxy.common.commands.ChatMessage;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class CommandSpy {
    public static Set<UUID> globalSpies, localSpies;

    public static LChatBungee plugin = LChatBungee.getInstance();

    public static void initialize(ConfigFile config) {
        globalSpies = config.getListOrDefault("command-spies.global", String.class, Collections.emptyList())
                .stream()
                    .map(UUID::fromString)
                    .collect(Collectors.toSet());
        localSpies = config.getListOrDefault("command-spies.local", String.class, Collections.emptyList())
                .stream()
                .map(UUID::fromString)
                .collect(Collectors.toSet());

    }

    public static void add(UUID uuid, boolean global) {
        Set<UUID> activeSet = global ? globalSpies : localSpies;
        Set<UUID> inactiveSet = global ? localSpies : globalSpies;

        inactiveSet.remove(uuid);
        activeSet.add(uuid);
    }

    public static void remove(UUID uuid) {
        globalSpies.remove(uuid);
        localSpies.remove(uuid);
    }

    public enum Scope {
        GLOBAL, LOCAL, NONE
    }
    public static Scope getScope(UUID uuid) {
        if(globalSpies.contains(uuid))
            return Scope.GLOBAL;
        if(localSpies.contains(uuid))
            return Scope.LOCAL;
        return Scope.NONE;
    }


    public static boolean toggle(UUID uuid) {
        Scope scope = getScope(uuid);
        if(scope == Scope.GLOBAL) {
            globalSpies.remove(uuid);
            return false;
        } else if(scope == Scope.LOCAL) {
            localSpies.remove(uuid);
            return false;
        } else {
            add(uuid, true);
            return true;
        }
    }

    public static void sendCommandMessage(UUID senderId, String command, CommonPlayer.Factory playerFactory) {
        Optional<CommonPlayer> optionalSender = playerFactory.getPlayer(senderId);
        if (optionalSender.isEmpty()) return;
        CommonPlayer sender = optionalSender.get();
        ChatMessage message = ChatMessage.simple(
                "[Spy] %s: %s".formatted(sender.getName(), command),
                ChatMessage.Color.GOLD
        );
        CommandSpy.globalSpies.stream()
                .map(playerFactory::getPlayer)
                .flatMap(Optional::stream)
                .forEach(player -> player.sendMessage(message));
        CommandSpy.localSpies.stream()
                .map(playerFactory::getPlayer)
                .flatMap(Optional::stream)
                .forEach(player -> {
                    if(player.getServerName().equals(sender.getServerName())) {
                        player.sendMessage(message);
            }
        });
    }
}
