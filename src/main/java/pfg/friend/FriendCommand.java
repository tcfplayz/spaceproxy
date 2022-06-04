package pfg.friend;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import velocity.tcf.spaceproxy.SpaceProxy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FriendCommand implements SimpleCommand {
    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        if (source instanceof Player) {
            Player player = (Player) source;
            if (invocation.arguments().length < 2) sendHelp(player);
            else {
                String first = invocation.arguments()[0];
                String second = invocation.arguments()[1];
                if (SpaceProxy.server.getPlayer(second).isPresent()) {
                    assert SpaceProxy.server.getPlayer(second).isPresent();
                    Player player2 = SpaceProxy.server.getPlayer(second).get();
                    switch (first) {
                        case "add":
                            CachedRequests.addPlayer(player, player2);
                            break;
                        case "accept":
                            CachedRequests.accept(player2, player);
                            break;
                        case "deny":
                            CachedRequests.deny(player2, player);
                        case "remove":
                        case "send":
                        case "tp":
                            player.sendMessage(Component.text("Not implemented.", NamedTextColor.RED));
                            break; // TODO: to be implemented
                        default:
                            sendHelp(player);
                            break;
                    }
                } else player.sendMessage(Component.text("That player is not online!", NamedTextColor.RED));
            }
        }
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        List<String> strings;
        switch (invocation.arguments().length) {
            case 0:
                strings = Arrays.asList("add", "remove", "tp", "send", "accept", "deny", "help");
                break;
            case 1:
                switch (invocation.arguments()[0]) {
                    case "add":
                    case "remove":
                    case "accept":
                    case "deny":
                    case "tp":
                        strings = new ArrayList(SpaceProxy.server.getAllPlayers());
                        Collections.sort(strings);
                        break;
                    default:
                        strings = List.of("");
                        break;
                }
                break;
            default:
                strings = List.of("");
                break;
        }
        return strings;
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return true;
    }

    private void sendHelp(Player player) {
        player.sendMessage(Component.text("/friend: (Not done marked as red, done marked as green)"));
        player.sendMessage(Component.text("/f accept <player> - self explanatory", NamedTextColor.GREEN));
        player.sendMessage(Component.text("/f deny <player> - self explanatory", NamedTextColor.RED));
        player.sendMessage(Component.text("/f add <player> - self explanatory", NamedTextColor.GREEN));
        player.sendMessage(Component.text("/f list - self explanatory", NamedTextColor.RED));
        player.sendMessage(Component.text("/f remove <player> - self explanatory", NamedTextColor.RED));
        player.sendMessage(Component.text("/f tp <player> - goes to the player's server", NamedTextColor.RED));
        player.sendMessage(Component.text("/f send <player> - admin only", NamedTextColor.RED));
    }
}
