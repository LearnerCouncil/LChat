package rocks.learnercouncil.lchat.bungee;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class CommandSpy {
    public static Set<ProxiedPlayer> globalSpies, localSpies;

    public static void initialize() {
        globalSpies = LChat.getConfigFile()
                .getConfig()
                .getStringList("command-spies.global")
                .stream()
                .map(uuid -> LChat.getInstance()
                        .getProxy()
                        .getPlayer(UUID.fromString(uuid)))
                .collect(Collectors.toSet());
        localSpies = LChat.getConfigFile()
                .getConfig()
                .getStringList("command-spies.local")
                .stream()
                .map(uuid -> LChat.getInstance()
                        .getProxy()
                        .getPlayer(UUID.fromString(uuid)))
                .collect(Collectors.toSet());
    }

    public static void add(ProxiedPlayer player, boolean global) {
        Set<ProxiedPlayer> activeSet = global ? globalSpies : localSpies;
        Set<ProxiedPlayer> inactiveSet = global ? localSpies : globalSpies;

        inactiveSet.remove(player);
        activeSet.add(player);
    }

    public static void remove(ProxiedPlayer player) {
        globalSpies.remove(player);
        localSpies.remove(player);
    }

    public enum Scope {
        GLOBAL, LOCAL, NONE
    }
    public static Scope getScope(ProxiedPlayer player) {
        if(globalSpies.contains(player))
            return Scope.GLOBAL;
        if(localSpies.contains(player))
            return Scope.LOCAL;
        return Scope.NONE;
    }


    public static boolean toggle(ProxiedPlayer player) {
        Scope scope = getScope(player);
        if(scope == Scope.GLOBAL) {
            globalSpies.remove(player);
            return false;
        } else if(scope == Scope.LOCAL) {
            localSpies.remove(player);
            return false;
        } else {
            add(player, true);
            return true;
        }
    }

    public static void sendCommand(ProxiedPlayer sender, String command) {
        TextComponent message = new TextComponent("[Spy] " + sender.getDisplayName() + ": " + command);
        message.setColor(ChatColor.GOLD);
        CommandSpy.globalSpies.forEach(p -> p.sendMessage(message));
        CommandSpy.localSpies.forEach(p -> {
            if(p.getServer().equals(sender.getServer())) {
                p.sendMessage(message);
            }
        });
    }
}
