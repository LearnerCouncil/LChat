package rocks.learnercouncil.lchat.proxy.velocity.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import rocks.learnercouncil.lchat.proxy.bungee.CommandSpy;
import rocks.learnercouncil.lchat.proxy.common.commands.CommandResults;
import rocks.learnercouncil.lchat.proxy.common.commands.Permissions;
import rocks.learnercouncil.lchat.proxy.velocity.LChatVelocity;

import java.util.UUID;

public class CommandSpyCommand {

    private static CommandMeta getMeta(LChatVelocity plugin) {
        return plugin.getProxy()
                .getCommandManager()
                .metaBuilder("commandspy")
                .aliases("cmdspy")
                .plugin(plugin)
                .build();
    }

    public static void register(LChatVelocity plugin) {
        LiteralCommandNode<CommandSource> commandSpyNode = BrigadierCommand.literalArgumentBuilder("commandspy")
                .requires(src -> src instanceof Player && src.hasPermission(Permissions.COMMMANDSPY_COMMAND))
                .executes(CommandSpyCommand::toggleSpying)
                .then(BrigadierCommand.literalArgumentBuilder("toggle")
                        .executes(CommandSpyCommand::toggleSpying)
                ).then(BrigadierCommand.literalArgumentBuilder("on")
                        .executes(cx -> setSpying(cx, true))
                ).then(BrigadierCommand.literalArgumentBuilder("off")
                        .executes(cx -> setSpying(cx, false))
                ).then(BrigadierCommand.literalArgumentBuilder("scope")
                        .then(BrigadierCommand.requiredArgumentBuilder("scope", StringArgumentType.word())
                                .suggests((cx, builder) -> {
                                    builder.suggest("global");
                                    builder.suggest("local");
                                    return builder.buildFuture();
                                })
                                .executes(cx -> {
                                    String scope = StringArgumentType.getString(cx, "scope");
                                    setScope(cx, scope);
                                    return Command.SINGLE_SUCCESS;
                                }))
                ).build();
        plugin.getProxy().getCommandManager().register(getMeta(plugin), new BrigadierCommand(commandSpyNode));
    }

    private static int toggleSpying(CommandContext<CommandSource> context) {
        Player player = (Player) context.getSource();
        boolean isSpying = CommandSpy.toggle(player.getUniqueId());
        if(isSpying)
            player.sendMessage(CommandResults.NOW_SPYING.velocity());
        else
            player.sendMessage(CommandResults.NO_LONGER_SPYING.velocity());
        return Command.SINGLE_SUCCESS;
    }

    private static int setSpying(CommandContext<CommandSource> context, boolean spying) {
        Player player = (Player) context.getSource();
        UUID id = player.getUniqueId();
        if(CommandSpy.getScope(id) != CommandSpy.Scope.NONE) {
            if (spying)
                CommandSpy.add(id, true);
            else
                CommandSpy.remove(id);
            player.sendMessage(CommandResults.NO_LONGER_SPYING.velocity());
        } else {
            player.sendMessage(CommandResults.NOT_SPYING.velocity());
        }
        return Command.SINGLE_SUCCESS;
    }

    private static void setScope(CommandContext<CommandSource> context, String scopeString) {
        Player player = (Player) context.getSource();
        UUID id = player.getUniqueId();

        CommandSpy.Scope oldScope = CommandSpy.getScope(id);
        CommandSpy.Scope newScope;
        try {
             newScope = CommandSpy.Scope.valueOf(scopeString.toUpperCase());
        } catch (IllegalArgumentException ignored) {
            player.sendMessage(CommandResults.INVALID_SCOPE.velocity());
            return;
        }

        switch (oldScope) {
            case NONE -> {
                player.sendMessage(CommandResults.NOT_SPYING.velocity());
                return;
            }
            case GLOBAL -> CommandSpy.add(id, true);
            case LOCAL -> CommandSpy.add(id, false);
        }

        if(oldScope == newScope)
            player.sendMessage(CommandResults.sameScope(newScope.toString()).velocity());
        else
            player.sendMessage(CommandResults.setScope(newScope.toString()).velocity());
    }



}
