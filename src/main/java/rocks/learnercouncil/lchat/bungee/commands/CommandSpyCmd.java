package rocks.learnercouncil.lchat.bungee.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import rocks.learnercouncil.lchat.bungee.commands.util.CommandUtil;

import java.util.ArrayList;

public class CommandSpyCmd extends Command implements TabExecutor {
    public CommandSpyCmd() {
        super("commandspy", "lchat.commands.commandspy", "cmdspy");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        ArrayList<String> arguments = new ArrayList<>();
        if(args.length == 1) {
            arguments.add("on");
            arguments.add("off");
            arguments.add("toggle");
            arguments.add("scope");
        } else if(args.length == 2 && args[0].equalsIgnoreCase("scope")) {
            arguments.add("proxy");
            arguments.add("server");
        }
        return CommandUtil.getPartialMatches(arguments, args[args.length-1]);
    }
}
