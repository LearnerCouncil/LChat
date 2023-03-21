package rocks.learnercouncil.lchat.bungee;


import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.chat.ComponentSerializer;
import rocks.learnercouncil.lchat.bungee.handlers.ChatHandler;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ChatMessage {

    @Getter private final String message;
    @Getter private final boolean fromPlayer;

    public ChatMessage(String message, boolean fromPlayer) {
        this.message = message;
        this.fromPlayer = fromPlayer;
    }

    public static ChatMessage blank() {
        return new ChatMessage("", false);
    }

    public void send(ProxiedPlayer player) {
        ComponentBuilder builder = new ComponentBuilder().append(ComponentSerializer.parse(message));
        if(fromPlayer && player.hasPermission("lchat.delete")) {
            builder.append(" [x]")
                    .color(ChatColor.RED)
                    .event(new ClickEvent(
                            ClickEvent.Action.RUN_COMMAND,
                            "/lchat deletemessage " + this.hashCode()
                    ));
        }
        player.sendMessage(builder.create());
        if(!ChatHandler.playerMessages.containsKey(player.getUniqueId())) ChatHandler.playerMessages.put(player.getUniqueId(), new LinkedList<>());
        List<String> list = ChatHandler.playerMessages.get(player.getUniqueId());
        if(list.size() > 5) list.remove(0);
        list.add(ComponentSerializer.toString(builder.create()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatMessage that = (ChatMessage) o;
        return fromPlayer == that.fromPlayer && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, fromPlayer);
    }
}
