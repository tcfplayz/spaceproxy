package velocity.tcf.spaceproxy.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.player.ServerPostConnectEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import pfg.utils.MySQLFriends;
import velocity.tcf.spaceproxy.util.MySQL;

public class PlayerListener {

    @Subscribe
    public void onPlayerJoin(ServerPostConnectEvent event) {
        if (MySQLFriends.playerExists(event.getPlayer().getUniqueId().toString())) {
            MySQLFriends.addPlayer(event.getPlayer().getUniqueId().toString());
        }
        TextComponent joined =
                Component.text("[", NamedTextColor.GRAY)
                        .append(Component.text("+", NamedTextColor.GREEN))
                        .append(Component.text("]", NamedTextColor.GRAY))
                        .append(Component.text(" " + event.getPlayer().getUsername(), NamedTextColor.GRAY));

        TextComponent left =
                Component.text("[", NamedTextColor.GRAY)
                        .append(Component.text("-", NamedTextColor.RED))
                        .append(Component.text("]", NamedTextColor.GRAY))
                        .append(Component.text(" " + event.getPlayer().getUsername(), NamedTextColor.GRAY));

        if (event.getPlayer().getCurrentServer().isPresent()) event.getPlayer().getCurrentServer().get().getServer().getPlayersConnected().forEach(player -> player.sendMessage(joined));

        if (event.getPreviousServer() != null)
            event.getPreviousServer().getPlayersConnected().forEach(player -> player.sendMessage(left));
    }

    @Subscribe
    public void disconnect(DisconnectEvent event) {
        MySQL.updateLastEntered(event.getPlayer().getUniqueId().toString());
        TextComponent left =
                Component.text("[", NamedTextColor.GRAY)
                        .append(Component.text("-", NamedTextColor.RED))
                        .append(Component.text("]", NamedTextColor.GRAY))
                        .append(Component.text(" " + event.getPlayer().getUsername(), NamedTextColor.GRAY));
        if (event.getPlayer().getCurrentServer().isPresent()) event.getPlayer().getCurrentServer().get().getServer().getPlayersConnected().forEach(player -> player.sendMessage(left));
    }


}
