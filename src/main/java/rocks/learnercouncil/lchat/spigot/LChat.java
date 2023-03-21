package rocks.learnercouncil.lchat.spigot;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import lombok.Getter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import rocks.learnercouncil.lchat.spigot.events.PlayerChat;
import rocks.learnercouncil.lchat.spigot.handlers.PluginMessageHandler;
import rocks.learnercouncil.lchat.spigot.handlers.ProtocolHandler;

public final class LChat extends JavaPlugin {

    @Getter private static LChat instance;
    @Getter private static ProtocolManager protocolManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        protocolManager = ProtocolLibrary.getProtocolManager();
        ProtocolHandler.addPacketListeners();

        getServer().getMessenger().registerIncomingPluginChannel(this, "lchat:main", new PluginMessageHandler());
        getServer().getMessenger().registerOutgoingPluginChannel(this, "lchat:main");

        saveDefaultConfig();

        registerEvents(
                new PlayerChat()
        );

        PlayerChat.chatStyle = getConfig().getString("chat-style");

    }

    private void registerEvents(Listener... listeners) {
        for(Listener listener : listeners)
            getServer().getPluginManager().registerEvents(listener, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
