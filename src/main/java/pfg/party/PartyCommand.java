package pfg.party;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import velocity.tcf.spaceproxy.SpaceProxy;

import java.util.Arrays;
import java.util.List;

public class PartyCommand implements SimpleCommand {
    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        if (source instanceof Player) {
            Player player = (Player) source;
            if (invocation.arguments().length < 2) {
                if (invocation.arguments().length == 0) sendHelp(player);
                else {
                    switch (invocation.arguments()[0]) {
                        case "create":
                            PlayerParty party = new PlayerParty();
                            party.setPartyLimit(10);
                            party.makePartyOwner(player);
                            if (party.cache()) player.sendMessage(Component.text("Created!", NamedTextColor.GREEN));
                            else player.sendMessage(Component.text("You can only create one party!", NamedTextColor.RED));
                            break;
                        case "warp":
                            player.sendMessage(Component.text("Not implemented.", NamedTextColor.RED));
                            break;
                        default:
                            sendHelp(player);
                    }
                }
            }
            else if (invocation.arguments().length < 2) sendHelp(player);
            else {
                String first = invocation.arguments()[0];
                String second = invocation.arguments()[1];
                switch (first) {
                    case "invite":
                        if (CacheParties.getParty(player.getUniqueId()) instanceof PlayerParty) {
                            PlayerParty party = (PlayerParty) CacheParties.getParty(player.getUniqueId());
                            if (SpaceProxy.server.getPlayer(second).isPresent()) {
                                Player p2 = SpaceProxy.server.getPlayer(second).get();
                                party.invitePlayer(p2);
                            } else player.sendMessage(Component.text("Not online.", NamedTextColor.RED));
                        }
                        break;
                    case "accept":
                        if (CacheParties.getParty(player.getUniqueId()) instanceof PlayerParty) {
                            if (invocation.arguments()[2] != null) {
                                String third = invocation.arguments()[2];
                                if (SpaceProxy.server.getPlayer(third).isPresent()) {
                                    Player owner = SpaceProxy.server.getPlayer(third).get();
                                    PlayerParty party = (PlayerParty) CacheParties.getParty(owner.getUniqueId());
                                    party.accept(player);
                                } else player.sendMessage(Component.text("Invalid name.", NamedTextColor.RED));

                            } else sendHelp(player);
                        }
                        break;
                    case "deny":
                        if (CacheParties.getParty(player.getUniqueId()) instanceof PlayerParty) {
                            if (invocation.arguments()[2] != null) {
                                String third = invocation.arguments()[2];
                                if (SpaceProxy.server.getPlayer(third).isPresent()) {
                                    Player owner = SpaceProxy.server.getPlayer(third).get();
                                    PlayerParty party = (PlayerParty) CacheParties.getParty(owner.getUniqueId());
                                    party.deny(player);
                                } else player.sendMessage(Component.text("Invalid name.", NamedTextColor.RED));

                            } else sendHelp(player);
                        }
                        break;
                    case "test":
                    case "admin":
                    case "ranked":
                        player.sendMessage(Component.text("Not implemented.", NamedTextColor.RED));
                        break;
                    default:
                        sendHelp(player);
                        break;
                }
            }
        }
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        return Arrays.asList("invite", "warp", "accept", "deny", "help", "test", "admin", "ranked", "create");
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return true;
    }

    public void sendHelp(Player player) {
        player.sendMessage(Component.text("/party: (Not done marked as red, done marked as green)"));
        player.sendMessage(Component.text("/p create - self explanatory", NamedTextColor.GREEN));
        player.sendMessage(Component.text("/p accept <player> - self explanatory", NamedTextColor.GREEN));
        player.sendMessage(Component.text("/p deny <player> - self explanatory", NamedTextColor.GREEN));
        player.sendMessage(Component.text("/p list - self explanatory", NamedTextColor.GREEN));
        player.sendMessage(Component.text("/p invite <player> - self explanatory", NamedTextColor.GREEN));
        player.sendMessage(Component.text("/p warp - self explanatory", NamedTextColor.RED));
        player.sendMessage(Component.text("/p ranked <subcommand> - ranked version of parties", NamedTextColor.RED));
    }

}
