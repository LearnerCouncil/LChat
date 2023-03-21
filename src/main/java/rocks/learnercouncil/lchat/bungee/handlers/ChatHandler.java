package rocks.learnercouncil.lchat.bungee.handlers;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.chat.ComponentSerializer;
import net.md_5.bungee.event.EventHandler;
import rocks.learnercouncil.lchat.bungee.ChatMessage;
import rocks.learnercouncil.lchat.bungee.LChat;

import java.util.*;
import java.util.stream.Collectors;

public class ChatHandler {

    private static final LChat plugin = LChat.getInstance();
    public static final Map<UUID, List<String>> playerMessages = new HashMap<>();
    public static final Map<UUID, List<ChatMessage>> messageMap = new HashMap<>();

    public static void add(UUID uuid, ChatMessage message) {
        List<ChatMessage> messages = messageMap.get(uuid);
        messages.add(message);
        messages.remove(0);
    }

    public static void delete(UUID deleter, int messageHash) {
        System.out.println("0: Hash:" + messageHash + ", Messages: " + messageMap.get(deleter).stream().map(m -> m.toString() + "\n").collect(Collectors.toList()));
        Optional<ChatMessage> messageOptional = messageMap.get(deleter).stream().filter(m -> m.hashCode() == messageHash).findAny();
        System.out.println("0");
        if(!messageOptional.isPresent()) return;
        ChatMessage message = messageOptional.get();
        System.out.println("1: " + message);
        Set<UUID> toResend = new HashSet<>();
        int iterations = 0;
        for(UUID uuid : messageMap.keySet()) {
            System.out.println("Iteration: 0 ---------------------------------");
            List<ChatMessage> messages = messageMap.get(uuid).stream().filter(ChatMessage::isFromPlayer).collect(Collectors.toList());
            messages.forEach(m -> {
                System.out.println("Iteration: 0 ---------------------------------");
                if(m.equals(message)) {
                    messageMap.get(uuid).set(messageMap.get(uuid).indexOf(m), getDeletedMessage(uuid, m));
                    toResend.add(uuid);
                }
            });
        }
        toResend.forEach(ChatHandler::resend);
    }

    private static ChatMessage getDeletedMessage(UUID uuid, ChatMessage message) {
        ComponentBuilder builder = new ComponentBuilder("<removed>").color(ChatColor.RED);
        if(plugin.getProxy().getPlayer(uuid).hasPermission("lchat.delete"))
            builder.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ComponentSerializer.parse(message.getMessage()))));
        return new ChatMessage(ComponentSerializer.toString(builder.create()), false);
    }

    public static void resend(UUID uuid) {
        messageMap.get(uuid).forEach(m -> m.send(plugin.getProxy().getPlayer(uuid)));
    }

    public static void clear(UUID player) {
        messageMap.remove(player);
    }
    public static void initialize(UUID player) {
        messageMap.put(player, new LinkedList<>(Collections.nCopies(100, ChatMessage.blank())));
    }

    public static class Events implements Listener {

        @EventHandler
        public void onPlayerLeave(ServerDisconnectEvent event) {
            ChatHandler.clear(event.getPlayer().getUniqueId());
        }

        @EventHandler
        public void onPlayerJoin(ServerConnectEvent event) {
            if(event.getReason() != ServerConnectEvent.Reason.JOIN_PROXY) return;
            ChatHandler.initialize(event.getPlayer().getUniqueId());
        }
    }
}
