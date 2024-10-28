package rocks.learnercouncil.lchat.proxy.bungee.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import rocks.learnercouncil.lchat.proxy.bungee.CommandSpy;
import rocks.learnercouncil.lchat.proxy.common.commands.CommandUtil;
import rocks.learnercouncil.lchat.proxy.common.commands.CommandResults;

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
            if(isSpying) player.sendMessage(CommandResults.NOW_SPYING.bungee());
            else player.sendMessage(CommandResults.NO_LONGER_SPYING.bungee());
            return;
        }
        if(args.length == 1) {
            if(args[0].equalsIgnoreCase("on")) {
                if(CommandSpy.getScope(id) == CommandSpy.Scope.NONE) {
                    CommandSpy.add(id, true);
                    player.sendMessage(CommandResults.NOW_SPYING.bungee());
                    return;
                }
                player.sendMessage(CommandResults.ALREADY_SPYING.bungee());
                return;
            }
            if(args[0].equalsIgnoreCase("off")) {
                if(CommandSpy.getScope(id) != CommandSpy.Scope.NONE) {
                    CommandSpy.remove(id);
                    player.sendMessage(CommandResults.NO_LONGER_SPYING.bungee());
                    return;
                }
                player.sendMessage(CommandResults.NOT_SPYING.bungee());
                return;
            }
            if(args[0].equalsIgnoreCase("toggle")) {
                boolean isSpying = CommandSpy.toggle(id);
                if(isSpying) player.sendMessage(CommandResults.NOW_SPYING.bungee());
                else player.sendMessage(CommandResults.NO_LONGER_SPYING.bungee());
                return;
            }
        }
        if(args.length == 2 && args[0].equalsIgnoreCase("scope")) {
            CommandSpy.Scope scope = CommandSpy.getScope(id);
            if(scope == CommandSpy.Scope.NONE) {
                player.sendMessage(CommandResults.NOT_SPYING.bungee());
                return;
            }
            if(!CommandUtil.equalsAny(args[1], "global", "local")) {
                player.sendMessage(CommandResults.INVALID_SCOPE.bungee());
                return;
            }
            if(args[1].equalsIgnoreCase("global")) {
                CommandSpy.add(id, true);
                if(scope == CommandSpy.Scope.GLOBAL)
                    player.sendMessage(CommandResults.sameScope("GLOBAL").bungee());
                else
                    player.sendMessage(CommandResults.setScope("GLOBAL").bungee());
                return;
            }
            if(args[1].equalsIgnoreCase("local")) {
                CommandSpy.add(id, false);
                if(scope == CommandSpy.Scope.LOCAL)
                    player.sendMessage(CommandResults.sameScope("LOCAL").bungee());
                else
                    player.sendMessage(CommandResults.setScope("LOCAL").bungee());
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
