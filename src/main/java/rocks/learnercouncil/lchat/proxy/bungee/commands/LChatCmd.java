package rocks.learnercouncil.lchat.proxy.bungee.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import rocks.learnercouncil.lchat.proxy.bungee.ChatFilter;
import rocks.learnercouncil.lchat.proxy.bungee.LChatBungee;
import rocks.learnercouncil.lchat.proxy.common.commands.CommandResults;
import rocks.learnercouncil.lchat.proxy.common.commands.Permissions;

import java.util.ArrayList;
import java.util.List;

import static rocks.learnercouncil.lchat.proxy.common.commands.CommandUtil.*;

public class LChatCmd extends Command implements TabExecutor {

    public LChatCmd() {
        super("lchat", Permissions.LCHAT_COMMAND);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!sender.hasPermission(Permissions.LCHAT_COMMAND)) return;
        if(args.length < 1) {
            sender.sendMessage(CommandResults.TOO_FEW_ARGS.bungee());
            return;
        }
        if(args[0].equalsIgnoreCase("clear")) {
            if(args.length > 1) {
                sender.sendMessage(CommandResults.TOO_MANY_ARGS.bungee());
                return;
            }
            LChatBungee.getInstance().getProxy().getPlayers().forEach(p -> p.sendMessage(CommandResults.clearChat().bungee()));
            return;
        }
        if(equalsAny(args[0], "whitelist", "blacklist")) {
            int argLength = args[1].equalsIgnoreCase("list") ? 2 : 3;
            if(args.length < argLength) {
                sender.sendMessage(CommandResults.TOO_FEW_ARGS.bungee());
                return;
            }
            if(args.length > argLength) {
                sender.sendMessage(CommandResults.TOO_MANY_ARGS.bungee());
                return;
            }
            boolean blacklist = args[0].equalsIgnoreCase("blacklist");
            if(args[1].equalsIgnoreCase("add")) {
                editList(blacklist, true, args[2]);
                sender.sendMessage(CommandResults.added(args[2], blacklist).bungee());
                return;
            }
            if(args[1].equalsIgnoreCase("remove")) {
                editList(blacklist, false, args[2]);
                sender.sendMessage(CommandResults.removed(args[2], blacklist).bungee());
                return;
            }
            if(args[1].equalsIgnoreCase("list")) {
                sender.sendMessage(CommandResults.listContents(blacklist).bungee());
            }
        }
    }

    private void editList(boolean blacklist, boolean add, String value) {
        List<String> list = blacklist ? ChatFilter.getBlacklist() : ChatFilter.getWhitelist();
        if(add) list.add(value);
        else list.remove(value);
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        ArrayList<String> arguments = new ArrayList<>();
        if(args.length == 1) {
            arguments.add("clear");
            arguments.add("whitelist");
            arguments.add("blacklist");
        } else if(args.length == 2 && equalsAny(args[0], "whitelist", "blacklist")) {
            arguments.add("add");
            arguments.add("remove");
            arguments.add("list");
        }
        return getPartialMatches(arguments, args[args.length - 1]);
    }
}
