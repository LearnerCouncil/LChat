package rocks.learnercouncil.lchat.proxy.velocity;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import rocks.learnercouncil.lchat.proxy.common.Messenger;

public class VelocityMessenger {

    public static final MinecraftChannelIdentifier CHANNEL = MinecraftChannelIdentifier.from(Messenger.MAIN_CHANNEL);

    private static ProxyServer proxy;

    public static void initialize(ProxyServer proxy) {
        VelocityMessenger.proxy = proxy;
    }


    @Subscribe
    public void onPluginMessageReceived(PluginMessageEvent e) {
        if (proxy == null) return;
        if (!(e.getSource() instanceof ServerConnection)) return;
        Messenger.receivePluginMessage(e.getIdentifier().getId(), e.getData(), new VelocityPlayer.Factory(proxy));
        e.setResult(PluginMessageEvent.ForwardResult.handled());
    }
}
