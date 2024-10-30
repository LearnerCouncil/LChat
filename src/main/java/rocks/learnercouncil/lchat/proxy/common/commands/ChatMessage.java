package rocks.learnercouncil.lchat.proxy.common.commands;

import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

import java.util.ArrayList;

import static net.kyori.adventure.text.Component.text;

public class ChatMessage {
    private final Component[] message;

    ChatMessage(Component[] message) {
        this.message = message;
    }

    public static ChatMessage simple(String string, Color color) {
        return new ChatMessage(new Component[]{ new Component(string, color) });
    }

    public BaseComponent[] bungee() {
        ComponentBuilder builder = new ComponentBuilder();
        for(Component component : message) {
            builder.append(component.string()).color(component.color().bungee());
        }
        return builder.create();
    }

    public net.kyori.adventure.text.Component velocity() {
        TextComponent.Builder builder = text();
        for(Component component : message) {
            builder.append(text(component.string(), component.color().velocity()));
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

        public ChatMessage build() {
            return new ChatMessage(components.toArray(Component[]::new));
        }
    }
    record Component(String string, Color color) {}
    @SuppressWarnings("unused")
    public enum Color {
        BLACK("§0"),
        DARK_BLUE("§1"),
        DARK_GREEN("§2"),
        DARK_AQUA("§3"),
        DARK_RED("§4"),
        DARK_PURPLE("§5"),
        GOLD("§6"),
        GRAY("§7"),
        DARK_GRAY("§8"),
        BLUE("§9"),
        GREEN("§a"),
        AQUA("§b"),
        RED("§c"),
        LIGHT_PURPLE("§d"),
        YELLOW("§e"),
        WHITE("§f");

        public ChatColor bungee() {
            return switch (this) {
                case BLACK -> ChatColor.BLACK;
                case DARK_BLUE -> ChatColor.DARK_BLUE;
                case DARK_GREEN -> ChatColor.DARK_GREEN;
                case DARK_AQUA -> ChatColor.DARK_AQUA;
                case DARK_RED -> ChatColor.DARK_RED;
                case DARK_PURPLE -> ChatColor.DARK_PURPLE;
                case GOLD -> ChatColor.GOLD;
                case GRAY -> ChatColor.GRAY;
                case DARK_GRAY -> ChatColor.DARK_GRAY;
                case BLUE -> ChatColor.BLUE;
                case GREEN -> ChatColor.GREEN;
                case AQUA -> ChatColor.AQUA;
                case RED -> ChatColor.RED;
                case LIGHT_PURPLE -> ChatColor.LIGHT_PURPLE;
                case YELLOW -> ChatColor.YELLOW;
                case WHITE -> ChatColor.WHITE;
            };
        }

        public NamedTextColor velocity() {
            return switch (this) {
                case BLACK -> NamedTextColor.BLACK;
                case DARK_BLUE -> NamedTextColor.DARK_BLUE;
                case DARK_GREEN -> NamedTextColor.DARK_GREEN;
                case DARK_AQUA -> NamedTextColor.DARK_AQUA;
                case DARK_RED -> NamedTextColor.DARK_RED;
                case DARK_PURPLE -> NamedTextColor.DARK_PURPLE;
                case GOLD -> NamedTextColor.GOLD;
                case GRAY -> NamedTextColor.GRAY;
                case DARK_GRAY -> NamedTextColor.DARK_GRAY;
                case BLUE -> NamedTextColor.BLUE;
                case GREEN -> NamedTextColor.GREEN;
                case AQUA -> NamedTextColor.AQUA;
                case RED -> NamedTextColor.RED;
                case LIGHT_PURPLE -> NamedTextColor.LIGHT_PURPLE;
                case YELLOW -> NamedTextColor.YELLOW;
                case WHITE -> NamedTextColor.WHITE;
            };
        }

        public final String code;

        Color(String code) {
            this.code = code;
        }
    }
}
