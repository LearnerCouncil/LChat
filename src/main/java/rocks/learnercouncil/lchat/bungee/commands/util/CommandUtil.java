package rocks.learnercouncil.lchat.bungee.commands.util;

import java.util.List;
import java.util.stream.Collectors;

public class CommandUtil {

    public static boolean equalsAny(String arg, String... comparisons) {
        for(String comparison : comparisons)
            if(arg.equalsIgnoreCase(comparison)) return true;
        return false;
    }

    public static Iterable<String> getPartialMatches(List<String> arguments, String comparator) {
        return arguments.stream().filter(a -> a.startsWith(comparator.toLowerCase())).collect(Collectors.toList());
    }

}
