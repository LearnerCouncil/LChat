package rocks.learnercouncil.lchat.bungee;

import lombok.Getter;
import net.md_5.bungee.config.Configuration;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class ChatFilter {

    @Getter private static List<String> whitelist, blacklist;

    public static void initialize() {
        Configuration config = LChat.getConfigFile().getConfig();
        whitelist = new LinkedList<>(config.getStringList("filter.whitelist"));
        blacklist = new LinkedList<>(config.getStringList("filter.blacklist"));
    }

    public static boolean isUnsafe(String message) {
        String[] words = message.split(" ");
        boolean unsafe = false;
        for(String word : words) {
            unsafe = blacklist.stream().anyMatch(word.toLowerCase()::contains);
            if(!unsafe) continue;
            for (String whitelistWord : whitelist) {
                if (whitelistWord.equalsIgnoreCase(word)) {
                    unsafe = false;
                    break;
                }
            }
        }
        return unsafe;
    }
}
