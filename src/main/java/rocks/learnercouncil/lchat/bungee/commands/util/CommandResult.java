package rocks.learnercouncil.lchat.bungee.commands.util;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import rocks.learnercouncil.lchat.bungee.ChatFilter;

import java.util.List;

import static net.md_5.bungee.api.ChatColor.*;

public class CommandResult {

    private static ComponentBuilder prefix() {
        return new ComponentBuilder().append("[LChat] ").color(DARK_PURPLE); 
    } 

    //Errors
    public static final BaseComponent[]
            TOO_FEW_ARGS = prefix().append("Too few arguments.").color(RED).create(),
            TOO_MANY_ARGS = prefix().append("Too many arguments.").color(RED).create(),
            ALREADY_SPYING = prefix().append("You are already spying on commands.").color(RED).create(),
            NOT_SPYING = prefix().append("You are not spying on commands.").color(RED).create(),
            INVALID_SCOPE = prefix().append("That scope doesn't exist. It must be either 'GLOBAL' or 'LOCAL'.").create();

    public static BaseComponent[] sameScope(String scope) {
        return prefix().append("Scope already set to " + scope + '.').color(RED).create();
    }

    //Results
    public static final BaseComponent[]
            NOW_SPYING = prefix().append("You are now spying on commands.").color(LIGHT_PURPLE).create(),
            NO_LONGER_SPYING = prefix().append("You are no longer spying on commands.").color(LIGHT_PURPLE).create();

    public static BaseComponent[] added(String word, boolean blacklist) {
        String list = blacklist ? "blacklist" : "whitelist";
        return prefix().append("Added '" + word + "' to the " + list + '.').color(LIGHT_PURPLE).create();
    }
    public static BaseComponent[] removed(String word, boolean blacklist) {
        String list = blacklist ? "blacklist" : "whitelist";
        return prefix().append("Removed '" + word + "' from the " + list + '.').color(LIGHT_PURPLE).create();
    }
    public static BaseComponent[] listContents(boolean blacklist) {
        List<String> list = blacklist ? ChatFilter.getBlacklist() : ChatFilter.getWhitelist();
        return prefix().append("--------------------\n")
                .color(DARK_PURPLE)
                .append(String.join(", ", list))
                .color(LIGHT_PURPLE)
                .append("\n--------------------")
                .color(DARK_PURPLE)
                .create();
    }
    public static BaseComponent[] setScope(String scope) {
        return prefix().append("Set scope to " + scope + '.').color(LIGHT_PURPLE).create();
    }

}
