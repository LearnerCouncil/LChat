package rocks.learnercouncil.lchat.proxy.common;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import rocks.learnercouncil.lchat.proxy.bungee.LChatBungee;

import java.util.Collections;
import java.util.List;
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

    public static void sendCommand(ProxiedPlayer sender, String command) {
        TextComponent message = new TextComponent("[Spy] " + sender.getDisplayName() + ": " + command);
        message.setColor(ChatColor.GOLD);
        CommandSpy.globalSpies.forEach(uuid -> {
            ProxiedPlayer player = plugin.getProxy().getPlayer(uuid);
            if(player != null)
                player.sendMessage(message);
        });
        CommandSpy.localSpies.forEach(uuid -> {
            ProxiedPlayer player = plugin.getProxy().getPlayer(uuid);
            if(player != null && player.getServer().equals(sender.getServer())) {
                player.sendMessage(message);
            }
        });
    }
}
