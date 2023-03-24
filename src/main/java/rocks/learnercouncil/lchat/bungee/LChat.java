package rocks.learnercouncil.lchat.bungee;

import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import rocks.learnercouncil.lchat.bungee.commands.*;

public final class LChat extends Plugin {
    @Getter private static LChat instance;
    @Getter private static ConfigFile configFile;

    @Override
    public void onEnable() {
        instance = this;
        configFile = new ConfigFile("cofig.yml");

        ChatFilter.initialize();

        getProxy().registerChannel("lchat:main");
        PluginManager pluginManager = getProxy().getPluginManager();
        pluginManager.registerListener(this, new PluginMessageHandler());

        pluginManager.registerCommand(this, new LChatCmd());
        pluginManager.registerCommand(this, new LCCmd());
        pluginManager.registerCommand(this, new CommandSpyCmd());
    }

    @Override
    public void onDisable() {

    }
}
