package rocks.learnercouncil.lchat.bungee;

import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import rocks.learnercouncil.lchat.bungee.commands.LChatCommand;
import rocks.learnercouncil.lchat.bungee.handlers.ChatHandler;
import rocks.learnercouncil.lchat.bungee.handlers.PluginMessageHandler;

public final class LChat extends Plugin {
    @Getter public static LChat instance;

    @Override
    public void onEnable() {
        instance = this;

        getProxy().registerChannel("lchat:main");

        PluginManager manager = getProxy().getPluginManager();

        manager.registerListener(this, new PluginMessageHandler());
        manager.registerListener(this, new ChatHandler.Events());

        manager.registerCommand(this, new LChatCommand());


    }

    @Override
    public void onDisable() {

    }
}
