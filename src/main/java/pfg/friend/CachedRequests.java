package pfg.friend;

import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import pfg.utils.MySQLFriends;

import java.util.HashMap;
import java.util.UUID;

public class CachedRequests {

    private static HashMap<UUID, UUID> req = new HashMap<>();

    public static void addPlayer(Player p1, Player p2) {
        if (req.get(p1.getUniqueId()) != null) {
            p1.sendMessage(Component.text("You can only have 1 request per time.", NamedTextColor.RED));
        }
        req.put(p1.getUniqueId(), p2.getUniqueId());
        p2.sendMessage(Component.text(p2.getUsername() + ", do /f accept " + p1.getUsername() + " to accept " + p1.getUsername() + "'s friend request!", NamedTextColor.GREEN));
    }

    public static void accept(Player p1, Player p2) {
        req.remove(p1.getUniqueId());
        MySQLFriends.acceptFriend(p1.getUniqueId().toString(), p2.getUniqueId().toString());
        p1.sendMessage(Component.text(p2.getUsername() + " has accepted your friend request!", NamedTextColor.DARK_AQUA));
        p2.sendMessage(Component.text("You have accepted " + p1.getUsername() + "'s friend request!", NamedTextColor.DARK_AQUA));
    }

    public static void deny(Player p1, Player p2) {
        req.remove(p1.getUniqueId());
        p1.sendMessage(Component.text(p2.getUsername() + " has denied your friend request!", NamedTextColor.AQUA));
        p2.sendMessage(Component.text("You have denied " + p1.getUsername() + "'s friend request!", NamedTextColor.AQUA));
    }

}
