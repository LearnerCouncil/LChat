package rocks.learnercouncil.lchat.bungee;

import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import rocks.learnercouncil.lchat.bungee.commands.CommandSpyCmd;
import rocks.learnercouncil.lchat.bungee.commands.LCCmd;
import rocks.learnercouncil.lchat.bungee.commands.LChatCmd;

import java.util.stream.Collectors;

public final class LChat extends Plugin {
    @Getter private static LChat instance;
    @Getter private static ConfigFile configFile;

    @Override
    public void onEnable() {
        instance = this;
        configFile = new ConfigFile("config.yml");

        ChatFilter.initialize();
        CommandSpy.initialize();

        getProxy().registerChannel("lchat:main");

        PluginManager pluginManager = getProxy().getPluginManager();
        pluginManager.registerListener(this, new PluginMessageHandler());

        pluginManager.registerCommand(this, new LChatCmd());
        pluginManager.registerCommand(this, new LCCmd());
        pluginManager.registerCommand(this, new CommandSpyCmd());
    }

    @Override
    public void onDisable() {
        configFile.getConfig().set("filter.whitelist", ChatFilter.getWhitelist());
        configFile.getConfig().set("filter.blacklist", ChatFilter.getBlacklist());
        configFile.getConfig().set("command-spies.global", CommandSpy.globalSpies.stream().map(p -> p.getUniqueId().toString()).collect(Collectors.toList()));
        configFile.getConfig().set("command-spies.local", CommandSpy.localSpies.stream().map(p -> p.getUniqueId().toString()).collect(Collectors.toList()));
        configFile.saveConfig();
    }
}
