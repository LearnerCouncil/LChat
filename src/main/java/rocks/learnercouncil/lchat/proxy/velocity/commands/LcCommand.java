package rocks.learnercouncil.lchat.proxy.velocity.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import rocks.learnercouncil.lchat.proxy.common.commands.CommandResults;
import rocks.learnercouncil.lchat.proxy.common.commands.Permissions;
import rocks.learnercouncil.lchat.proxy.velocity.LChatVelocity;

public class LcCommand {

    private static CommandMeta getMeta(LChatVelocity plugin) {
        return plugin.getProxy()
                .getCommandManager()
                .metaBuilder("lc")
                .plugin(plugin)
                .build();
    }

    public static void register(LChatVelocity plugin) {
        ProxyServer proxy = plugin.getProxy();
        LiteralCommandNode<CommandSource> lcNode = BrigadierCommand.literalArgumentBuilder("lc")
                .requires(src -> src.hasPermission(Permissions.LC_COMMAND))
                .then(BrigadierCommand.requiredArgumentBuilder("message", StringArgumentType.greedyString())
                        .executes(cx -> {
                            CommandSource source = cx.getSource();
                            String name = source instanceof Player ? ((Player) source).getUsername() : "[Console]";
                            String message = StringArgumentType.getString(cx, "message");
                            proxy.getAllPlayers()
                                    .stream()
                                    .filter(p -> p.hasPermission(Permissions.LC_COMMAND))
                                    .forEach(p -> p.sendMessage(CommandResults.lcMessage(name, message).velocity()));
                            ComponentLogger.logger().info(CommandResults.lcMessage(name, message).velocity());
                            return Command.SINGLE_SUCCESS;
                        })
                ).build();
        proxy.getCommandManager().register(getMeta(plugin), new BrigadierCommand(lcNode));
    }
}
