package rocks.learnercouncil.lchat.commands;

import java.util.List;
import java.util.stream.Collectors;

public class CommandUtil {

    protected static boolean equalsAny(String arg, String... comparisons) {
        for(String comparison : comparisons)
            if(arg.equalsIgnoreCase(comparison)) return true;
        return false;
    }

    protected static Iterable<String> copyPartialMatches(List<String> arguments, String comparator) {
        return arguments.stream().filter(a -> a.startsWith(comparator.toLowerCase())).collect(Collectors.toList());
    }

}
