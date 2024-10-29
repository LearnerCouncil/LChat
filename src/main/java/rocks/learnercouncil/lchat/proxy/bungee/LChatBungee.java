package rocks.learnercouncil.lchat.proxy.bungee;

import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import rocks.learnercouncil.lchat.proxy.bungee.commands.CommandSpyCmd;
import rocks.learnercouncil.lchat.proxy.bungee.commands.LCCmd;
import rocks.learnercouncil.lchat.proxy.bungee.commands.LChatCmd;
import rocks.learnercouncil.lchat.proxy.bungee.events.PlayerJoin;
import rocks.learnercouncil.lchat.proxy.common.BasicLogger;
import rocks.learnercouncil.lchat.proxy.common.ChatFilter;
import rocks.learnercouncil.lchat.proxy.common.CommandSpy;
import rocks.learnercouncil.lchat.proxy.common.ConfigFile;

import java.util.UUID;
import java.util.stream.Collectors;

public final class LChatBungee extends Plugin {
    @Getter private static LChatBungee instance;
    @Getter private static ConfigFile configFile;

    @Override
    public void onEnable() {
        instance = this;
        BasicLogger basicLogger = new BungeeLogger(getLogger());
        configFile = new ConfigFile("config.yml", this.getDataFolder(), basicLogger);
        ChatFilter.initialize(configFile);
        CommandSpy.initialize(configFile);

        getProxy().registerChannel("lchat:main");

        PluginManager pluginManager = getProxy().getPluginManager();
        pluginManager.registerListener(this, new PluginMessageHandler());
        pluginManager.registerListener(this, new PlayerJoin());

        pluginManager.registerCommand(this, new LChatCmd());
        pluginManager.registerCommand(this, new LCCmd());
        pluginManager.registerCommand(this, new CommandSpyCmd());
    }

    @Override
    public void onDisable() {
        configFile.set("filter.whitelist", ChatFilter.getWhitelist());
        configFile.set("filter.blacklist", ChatFilter.getBlacklist());
        configFile.set("command-spies.global", CommandSpy.globalSpies.stream().map(UUID::toString).collect(Collectors.toList()));
        configFile.set("command-spies.local", CommandSpy.localSpies.stream().map(UUID::toString).collect(Collectors.toList()));
        configFile.save();
    }
}
