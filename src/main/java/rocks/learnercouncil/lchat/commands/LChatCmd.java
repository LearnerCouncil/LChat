package rocks.learnercouncil.lchat.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static rocks.learnercouncil.lchat.commands.CommandUtil.*;

public class LChatCmd extends Command implements TabExecutor {

    public LChatCmd() {
        super("lchat", "lchat.commands.lchat");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

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
        return copyPartialMatches(arguments, args[args.length - 1]);
    }
}
