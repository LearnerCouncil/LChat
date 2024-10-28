package rocks.learnercouncil.lchat.proxy.common.commands;

import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

import java.util.ArrayList;

import static net.kyori.adventure.text.Component.text;

public class CommandResult {
    private final Component[] message;

    CommandResult(Component[] message) {
        this.message = message;
    }

    public BaseComponent[] bungee() {
        ComponentBuilder builder = new ComponentBuilder();
        for(Component component : message) {
            builder.append(component.string()).color(component.color().bungee);
        }
        return builder.create();
    }

    public net.kyori.adventure.text.Component velocity() {
        TextComponent.Builder builder = text();
        for(Component component : message) {
            builder.append(text(component.string(), component.color().velocity));
        }
        return builder.build();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(Component component : message) {
            builder.append(component.color().code).append(component.string);
        }
        return builder.toString();
    }

    public static class Builder {
        private final ArrayList<Component> components = new ArrayList<>();

        public Builder(String string, Color color) {
            this.components.add(new Component(string, color));
        }


        public Builder append(String string, Color color) {
            components.add(new Component(string, color));
            return this;
        }

        public CommandResult build() {
            return new CommandResult(components.toArray(Component[]::new));
        }
    }
    record Component(String string, Color color) {}
    @SuppressWarnings("unused")
    public enum Color {
        BLACK(ChatColor.BLACK, NamedTextColor.BLACK, "§0"),
        DARK_BLUE(ChatColor.DARK_BLUE, NamedTextColor.DARK_BLUE, "§1"),
        DARK_GREEN(ChatColor.DARK_GREEN, NamedTextColor.DARK_GREEN, "§2"),
        DARK_AQUA(ChatColor.DARK_AQUA, NamedTextColor.DARK_AQUA, "§3"),
        DARK_RED(ChatColor.DARK_RED, NamedTextColor.DARK_RED, "§4"),
        DARK_PURPLE(ChatColor.DARK_PURPLE, NamedTextColor.DARK_PURPLE, "§5"),
        GOLD(ChatColor.GOLD, NamedTextColor.GOLD, "§6"),
        GRAY(ChatColor.GRAY, NamedTextColor.GRAY, "§7"),
        DARK_GRAY(ChatColor.DARK_GRAY, NamedTextColor.DARK_GRAY, "§8"),
        BLUE(ChatColor.BLUE, NamedTextColor.BLUE, "§9"),
        GREEN(ChatColor.GREEN, NamedTextColor.GREEN, "§a"),
        AQUA(ChatColor.AQUA, NamedTextColor.AQUA, "§b"),
        RED(ChatColor.RED, NamedTextColor.RED, "§c"),
        LIGHT_PURPLE(ChatColor.LIGHT_PURPLE, NamedTextColor.LIGHT_PURPLE, "§d"),
        YELLOW(ChatColor.YELLOW, NamedTextColor.YELLOW, "§e"),
        WHITE(ChatColor.WHITE, NamedTextColor.WHITE, "§f");

        public final ChatColor bungee;
        public final NamedTextColor velocity;
        public final String code;

        Color(ChatColor bungee, NamedTextColor velocity, String code) {
            this.bungee = bungee;
            this.velocity = velocity;
            this.code = code;
        }
    }
}
