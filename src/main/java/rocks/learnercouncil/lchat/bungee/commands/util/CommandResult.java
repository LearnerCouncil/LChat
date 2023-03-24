package rocks.learnercouncil.lchat.bungee.commands.util;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import rocks.learnercouncil.lchat.bungee.ChatFilter;

import java.util.List;
import java.util.stream.Collectors;

import static net.md_5.bungee.api.ChatColor.*;

public class CommandResult {
    private static final ComponentBuilder PREFIX = new ComponentBuilder().append("[LChat] ").color(DARK_PURPLE);

    //Errors
    public static final BaseComponent[]
            TOO_FEW_ARGS = PREFIX.append("Too few arguments.").color(RED).create(),
            TOO_MANY_ARGS = PREFIX.append("Too many arguments.").color(RED).create();


    //Results

    public static BaseComponent[] added(String word, boolean blacklist) {
        String list = blacklist ? "blacklist" : "whitelist";
        return PREFIX.append("Added '" + word + "' to the " + list + '.').color(LIGHT_PURPLE).create();
    }
    public static BaseComponent[] removed(String word, boolean blacklist) {
        String list = blacklist ? "blacklist" : "whitelist";
        return PREFIX.append("Removed '" + word + "' from the " + list + '.').color(LIGHT_PURPLE).create();
    }
    public static BaseComponent[] listContents(boolean blacklist) {
        List<String> list = blacklist ? ChatFilter.getBlacklist() : ChatFilter.getWhitelist();
        return PREFIX.append("--------------------\n")
                .color(DARK_PURPLE)
                .append(String.join(", ", list))
                .color(LIGHT_PURPLE)
                .append("\n--------------------")
                .color(DARK_PURPLE)
                .create();
    }

}
