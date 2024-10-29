package rocks.learnercouncil.lchat.proxy.velocity.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.CommandSource;
import rocks.learnercouncil.lchat.proxy.bungee.ChatFilter;
import rocks.learnercouncil.lchat.proxy.common.commands.CommandResults;
import rocks.learnercouncil.lchat.proxy.common.commands.Permissions;
import rocks.learnercouncil.lchat.proxy.velocity.LChatVelocity;

import java.util.List;

public class LChatCommand {

    private static CommandMeta getMeta(LChatVelocity plugin) {
        return plugin.getProxy()
                .getCommandManager()
                .metaBuilder("lchat")
                .plugin(plugin)
                .build();
    }

    public static void register(LChatVelocity plugin) {
        LiteralCommandNode<CommandSource> lchatNode =
                BrigadierCommand.literalArgumentBuilder("lchat")
                        .requires(src -> src.hasPermission(Permissions.LCHAT_COMMAND))
                        .then(BrigadierCommand.literalArgumentBuilder("clear")
                                .executes(cx -> {
                                    plugin.getProxy()
                                            .getAllPlayers()
                                            .forEach(p -> p.sendMessage(CommandResults.clearChat().velocity()));
                                    return Command.SINGLE_SUCCESS;
                                })
                        ).then(BrigadierCommand.requiredArgumentBuilder("list", StringArgumentType.word())
                                .suggests((cx, builder) -> {
                                    builder.suggest("whitelist");
                                    builder.suggest("blacklist");
                                    return builder.buildFuture();
                                })
                                .then(BrigadierCommand.literalArgumentBuilder("list")
                                        .executes(cx -> {
                                            Boolean blacklist = isBlacklist(cx);
                                            if (blacklist == null) return 0;
                                            cx.getSource().sendMessage(CommandResults.listContents(blacklist).velocity());
                                            return Command.SINGLE_SUCCESS;
                                        })
                                ).then(BrigadierCommand.literalArgumentBuilder("add")
                                        .then(BrigadierCommand.requiredArgumentBuilder("word", StringArgumentType.word())
                                                .executes(cx -> {
                                                    Boolean blacklist = isBlacklist(cx);
                                                    if (blacklist == null) return 0;
                                                    String word = StringArgumentType.getString(cx, "word");
                                                    modifyList(blacklist, true, word);
                                                    cx.getSource().sendMessage(CommandResults.added(word, blacklist)
                                                            .velocity());
                                                    return Command.SINGLE_SUCCESS;
                                                })
                                        )
                                ).then(BrigadierCommand.literalArgumentBuilder("remove")
                                        .then(BrigadierCommand.requiredArgumentBuilder("word", StringArgumentType.word())
                                                .executes(cx -> {
                                                    Boolean blacklist = isBlacklist(cx);
                                                    if (blacklist == null) return 0;
                                                    String word = StringArgumentType.getString(cx, "word");
                                                    modifyList(blacklist, false, word);
                                                    cx.getSource().sendMessage(CommandResults.added(word, blacklist)
                                                            .velocity());
                                                    return Command.SINGLE_SUCCESS;
                                                })
                                        )
                                )
                        ).build();
        plugin.getProxy().getCommandManager().register(getMeta(plugin), new BrigadierCommand(lchatNode));
    }

    private static Boolean isBlacklist(CommandContext<CommandSource> context) {
        String list = StringArgumentType.getString(context, "list");
        return switch (list.toLowerCase()) {
            case "blacklist" -> true;
            case "whitelist" -> false;
            default -> null;
        };
    }

    private static void modifyList(boolean blacklist, boolean add, String word) {
        List<String> list = blacklist ? ChatFilter.getBlacklist() : ChatFilter.getWhitelist();
        if (add) list.add(word);
        else list.remove(word);
    }
}
