package rocks.learnercouncil.lchat.bungee.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import rocks.learnercouncil.lchat.bungee.handlers.ChatHandler;

import java.util.ArrayList;

public class LChatCommand extends Command implements TabExecutor {
    public LChatCommand() {
        super("lchat", "lchat.delete");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)) return;
        if(args.length != 2) return;
        if(!args[0].equals("deletemessage")) return;
        int hash;
        try {
            hash = Integer.parseInt(args[1]);
        } catch (NumberFormatException ignored) {
            return;
        }
        ChatHandler.delete(((ProxiedPlayer) sender).getUniqueId(), hash);
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}
