package rocks.learnercouncil.lchat.bungee;

import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import rocks.learnercouncil.lchat.bungee.handlers.PluginMessageHandler;

public final class LChat extends Plugin {
    @Getter public static LChat instance;

    @Override
    public void onEnable() {
        instance = this;

        getProxy().registerChannel("lchat:main");
        getProxy().getPluginManager().registerListener(this, new PluginMessageHandler());
    }

    @Override
    public void onDisable() {

    }
}
