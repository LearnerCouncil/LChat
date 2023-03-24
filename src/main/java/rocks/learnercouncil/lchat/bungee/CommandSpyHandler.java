package rocks.learnercouncil.lchat.bungee;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class CommandSpyHandler {
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

    public static void addSpy(ProxiedPlayer player, boolean global) {}

}
