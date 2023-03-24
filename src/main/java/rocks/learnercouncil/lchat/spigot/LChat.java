package rocks.learnercouncil.lchat.spigot;

import lombok.Getter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import rocks.learnercouncil.lchat.spigot.events.PlayerChat;
import rocks.learnercouncil.lchat.spigot.events.PlayerCommandPreprocess;

public final class LChat extends JavaPlugin {

    @Getter private static LChat instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        getServer().getMessenger().registerIncomingPluginChannel(this, "lchat:main", new PluginMessageHandler());
        getServer().getMessenger().registerOutgoingPluginChannel(this, "lchat:main");


        registerEvents(
                new PlayerChat(),
                new PlayerCommandPreprocess()
        );

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
