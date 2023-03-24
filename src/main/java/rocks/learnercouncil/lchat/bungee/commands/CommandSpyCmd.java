package rocks.learnercouncil.lchat.bungee.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import rocks.learnercouncil.lchat.bungee.CommandSpy;
import rocks.learnercouncil.lchat.bungee.commands.util.CommandResult;
import rocks.learnercouncil.lchat.bungee.commands.util.CommandUtil;

import java.util.ArrayList;
import java.util.UUID;

public class CommandSpyCmd extends Command implements TabExecutor {
    public CommandSpyCmd() {
        super("commandspy", "lchat.commands.commandspy", "cmdspy");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)) return;
        ProxiedPlayer player = (ProxiedPlayer) sender;
        UUID id = player.getUniqueId();
        if(args.length == 0) {
            boolean isSpying = CommandSpy.toggle(id);
            if(isSpying) player.sendMessage(CommandResult.NOW_SPYING);
            else player.sendMessage(CommandResult.NO_LONGER_SPYING);
            return;
        }
        if(args.length == 1) {
            if(args[0].equalsIgnoreCase("on")) {
                if(CommandSpy.getScope(id) == CommandSpy.Scope.NONE) {
                    CommandSpy.add(id, true);
                    player.sendMessage(CommandResult.NOW_SPYING);
                    return;
                }
                player.sendMessage(CommandResult.ALREADY_SPYING);
                return;
            }
            if(args[0].equalsIgnoreCase("off")) {
                if(CommandSpy.getScope(id) != CommandSpy.Scope.NONE) {
                    CommandSpy.remove(id);
                    player.sendMessage(CommandResult.NO_LONGER_SPYING);
                    return;
                }
                player.sendMessage(CommandResult.NOT_SPYING);
                return;
            }
            if(args[0].equalsIgnoreCase("toggle")) {
                boolean isSpying = CommandSpy.toggle(id);
                if(isSpying) player.sendMessage(CommandResult.NOW_SPYING);
                else player.sendMessage(CommandResult.NO_LONGER_SPYING);
                return;
            }
        }
        if(args.length == 2 && args[0].equalsIgnoreCase("scope")) {
            CommandSpy.Scope scope = CommandSpy.getScope(id);
            if(scope == CommandSpy.Scope.NONE) {
                player.sendMessage(CommandResult.NOT_SPYING);
                return;
            }
            if(!CommandUtil.equalsAny(args[1], "global", "local")) {
                player.sendMessage(CommandResult.INVALID_SCOPE);
                return;
            }
            if(args[1].equalsIgnoreCase("global")) {
                CommandSpy.add(id, true);
                if(scope == CommandSpy.Scope.GLOBAL)
                    player.sendMessage(CommandResult.sameScope("GLOBAL"));
                else
                    player.sendMessage(CommandResult.setScope("GLOBAL"));
                return;
            }
            if(args[1].equalsIgnoreCase("local")) {
                CommandSpy.add(id, false);
                if(scope == CommandSpy.Scope.LOCAL)
                    player.sendMessage(CommandResult.sameScope("LOCAL"));
                else
                    player.sendMessage(CommandResult.setScope("LOCAL"));
            }
        }
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
            arguments.add("global");
            arguments.add("local");
        }
        return CommandUtil.getPartialMatches(arguments, args[args.length-1]);
    }
}
