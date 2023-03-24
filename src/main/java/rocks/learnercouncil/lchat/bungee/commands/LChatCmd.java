package rocks.learnercouncil.lchat.bungee.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import rocks.learnercouncil.lchat.bungee.ChatFilter;
import rocks.learnercouncil.lchat.bungee.LChat;
import rocks.learnercouncil.lchat.bungee.commands.util.CommandResult;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static rocks.learnercouncil.lchat.bungee.commands.util.CommandUtil.*;

public class LChatCmd extends Command implements TabExecutor {

    public LChatCmd() {
        super("lchat", "lchat.commands.lchat");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!sender.hasPermission("lchat.commands.lchat")) return;
        if(args.length < 1) {
            sender.sendMessage(CommandResult.TOO_FEW_ARGS);
            return;
        }
        if(args[0].equalsIgnoreCase("clear")) {
            if(args.length > 1) {
                sender.sendMessage(CommandResult.TOO_MANY_ARGS);
                return;
            }
            LChat.getInstance().getProxy().getPlayers().forEach(p -> p.sendMessage(new TextComponent(
                    "\n\n\n\n\n\n\n\n\n\n" +
                    "\n\n\n\n\n\n\n\n\n\n" +
                    "\n\n\n\n\n\n\n\n\n\n" +
                    "\n\n\n\n\n\n\n\n\n\n" +
                    "\n\n\n\n\n\n\n\n\n\n" +
                    "\n\n\n\n\n\n\n\n\n\n" +
                    "\n\n\n\n\n\n\n\n\n\n" +
                    "\n\n\n\n\n\n\n\n\n\n" +
                    "\n\n\n\n\n\n\n\n\n\n" +
                    "\n\n\n\n\n\n\n\n\n\n")));
            return;
        }
        if(equalsAny(args[0], "whitelist", "blacklist")) {
            if(args.length < 3) {
                sender.sendMessage(CommandResult.TOO_FEW_ARGS);
                return;
            }
            if(args.length > 3) {
                sender.sendMessage(CommandResult.TOO_MANY_ARGS);
                return;
            }
            boolean blacklist = args[0].equalsIgnoreCase("blacklist");
            if(args[1].equalsIgnoreCase("add")) {
                editList(blacklist, true, args[2]);
                sender.sendMessage(CommandResult.added(args[2], blacklist));
                return;
            }
            if(args[1].equalsIgnoreCase("remove")) {
                editList(blacklist, false, args[2]);
                sender.sendMessage(CommandResult.removed(args[2], blacklist));
                return;
            }
            if(args[1].equalsIgnoreCase("list")) {
                sender.sendMessage(CommandResult.listContents(blacklist));
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
