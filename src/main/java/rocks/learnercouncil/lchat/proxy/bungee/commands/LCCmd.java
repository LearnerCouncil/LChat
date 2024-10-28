package rocks.learnercouncil.lchat.proxy.bungee.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import rocks.learnercouncil.lchat.proxy.bungee.LChat;
import rocks.learnercouncil.lchat.proxy.common.commands.CommandResults;

import java.util.ArrayList;

public class LCCmd extends Command implements TabExecutor {
    public LCCmd() {
        super("lc", "lchat.commands.lc");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!sender.hasPermission("lchat.commands.lc")) return;
        String name = (sender instanceof ProxiedPlayer) ? sender.getName() : "[Console]";
        String message = String.join(" ", args);
        ProxyServer proxy = LChat.getInstance().getProxy();
        proxy.getPlayers()
                .stream()
                .filter(p -> p.hasPermission("lchat.commands.lc"))
                .forEach(p -> p.sendMessage(CommandResults.LcMessage(name, message).bungee()));

        proxy.getLogger().info(CommandResults.LcMessage(name, message).toString());
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}
