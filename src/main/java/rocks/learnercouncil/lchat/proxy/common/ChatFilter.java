package rocks.learnercouncil.lchat.proxy.common;

import lombok.Getter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ChatFilter {

    @Getter private static List<String> whitelist, blacklist;

    public static void initialize(ConfigFile config) {
        whitelist = config.getListOrDefault("filter.whitelist", String.class, Collections.emptyList());
        blacklist = config.getListOrDefault("filter.blacklist", String.class, Collections.emptyList());
    }

    public static boolean isUnsafe(String message) {
        String[] words = message.split(" ");
        boolean unsafe = false;
        for(String word : words) {
            unsafe = blacklist.stream().anyMatch(word.toLowerCase()::contains);
            if(!unsafe) continue;
            for (String whitelistWord : whitelist) {
                word = word.replaceAll("\\p{Punct}", "").replace("…", "");
                if (whitelistWord.equalsIgnoreCase(word)) {
                    unsafe = false;
                    break;
                }
            }
        }
        return unsafe;
    }
}
