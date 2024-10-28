package rocks.learnercouncil.lchat.proxy.common.commands;

import rocks.learnercouncil.lchat.proxy.bungee.ChatFilter;

import java.util.List;

import static rocks.learnercouncil.lchat.proxy.common.commands.CommandResult.*;

public class CommandResults {
    private static final Color PREFIX = Color.DARK_PURPLE;
    private static final Color RESULT = Color.LIGHT_PURPLE;
    private static final Color ERROR = Color.RED;
    private static final Color SPECIAL = Color.GREEN;

    private static Builder prefix() {
        return new Builder("[LChat] ", PREFIX);
    }

    // Errors
    public static final CommandResult
            TOO_FEW_ARGS = prefix().append("Too few arguments.", ERROR).build(),
            TOO_MANY_ARGS = prefix().append("Too many arguments.", ERROR).build(),
            ALREADY_SPYING = prefix().append("You are already spying on commands.", ERROR).build(),
            NOT_SPYING = prefix().append("You are not spying on commands.", ERROR).build(),
            INVALID_SCOPE = prefix().append("That scope doesn't exist. It must be either 'GLOBAL' or 'LOCAL'.", ERROR).build();

    public static CommandResult sameScope(String scope) {
        return prefix().append("Scope already set to ", ERROR)
                .append(scope, SPECIAL)
                .append(".", ERROR)
                .build();
    }
    
    // Results

    public static final CommandResult
            NOW_SPYING = prefix().append("You are now spying on commands.", RESULT).build(),
            NO_LONGER_SPYING = prefix().append("You are no longer spying on commands.", RESULT).build();

    public static CommandResult added(String word, boolean blacklist) {
        String list = blacklist ? "blacklist" : "whitelist";
        return prefix().append("Added '", RESULT)
                .append(word, SPECIAL)
                .append("' to the ", RESULT)
                .append(list, SPECIAL)
                .append(".", RESULT)
                .build();
    }
    public static CommandResult removed(String word, boolean blacklist) {
        String list = blacklist ? "blacklist" : "whitelist";
        return prefix().append("Removed '", RESULT)
                .append(word, SPECIAL)
                .append("' to the ", RESULT)
                .append(list, SPECIAL)
                .append(".", RESULT)
                .build();
    }
    public static CommandResult listContents(boolean blacklist) {
        List<String> list = blacklist ? ChatFilter.getBlacklist() : ChatFilter.getWhitelist();
        return prefix().append("--------------------\n", PREFIX)
                .append(String.join(", ", list), RESULT)
                .append("\n--------------------", PREFIX)
                .build();
    }
    public static CommandResult setScope(String scope) {
        return prefix().append("Set scope to ", RESULT)
                .append(scope, SPECIAL)
                .append(".", RESULT)
                .build();
    }

    public static CommandResult LcMessage(String sender, String message) {
        return new Builder("[LC] ", Color.AQUA)
                .append(sender + ": ", Color.AQUA)
                .append(message, Color.WHITE)
                .build();
    }

    @SuppressWarnings("TextBlockMigration")
    public static CommandResult clearChat() {
        return new Builder("\n\n\n\n\n\n\n\n\n\n" +
                "\n\n\n\n\n\n\n\n\n\n" +
                "\n\n\n\n\n\n\n\n\n\n" +
                "\n\n\n\n\n\n\n\n\n\n" +
                "\n\n\n\n\n\n\n\n\n\n" +
                "\n\n\n\n\n\n\n\n\n\n" +
                "\n\n\n\n\n\n\n\n\n\n" +
                "\n\n\n\n\n\n\n\n\n\n" +
                "\n\n\n\n\n\n\n\n\n\n" +
                "\n\n\n\n\n\n\n\n\n\n",
                Color.WHITE)
                .build();
    }
}
