package pfg.party;

import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;

public class PlayerParty implements PartyInstance {

    ArrayList<Player> invited = new ArrayList<>();
    ArrayList<Player> players = new ArrayList<>();
    ArrayList<Player> moderators = new ArrayList<>();
    Player owner = null;
    int limit = 10;

    @Override
    public void makePartyOwner(Player player) {
        owner = player;
    }

    @Override
    public void makePartyModerator(Player player) {
        moderators.add(player);
    }

    @Override
    public Player getPartyOwner() {
        return owner;
    }

    @Override
    public ArrayList<Player> getPartyModerators() {
        return moderators;
    }

    @Override
    public int getPartyLimit() {
        return limit;
    }

    @Override
    public void setPartyLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public void invitePlayer(Player player) {
        invited.add(player);
        player.sendMessage(Component.text(getPartyOwner().getUsername() + " has invited you to a party!", NamedTextColor.GREEN));
        getPartyOwner().sendMessage(Component.text("You have invited " + player + " to your party", NamedTextColor.GREEN));

    }

    @Override
    public void accept(Player player) {
        invited.remove(player);
        players.add(player);
        player.sendMessage(Component.text("You have joined " + getPartyOwner() + "'s party!", NamedTextColor.GREEN));
        getPartyOwner().sendMessage(Component.text(player + " has joined your party", NamedTextColor.GREEN));
    }

    @Override
    public void deny(Player player) {
        invited.remove(player);
        player.sendMessage(Component.text("You have denied " + getPartyOwner() + "'s party.", NamedTextColor.RED));
        getPartyOwner().sendMessage(Component.text(player + " has denied your party invite.", NamedTextColor.RED));

    }

    @Override
    public void warp() {
        for (Player plr : getPartyPlayers()) {
            if (getPartyOwner().getCurrentServer().isPresent()) plr.createConnectionRequest(getPartyOwner().getCurrentServer().get().getServer()).fireAndForget();
        }
    }

    @Override
    public ArrayList<Player> getPartyPlayers() {
        return players;
    }

    @Override
    public boolean cache() {
        if (CacheParties.getParty(getPartyOwner().getUniqueId()) != null) return false;
        else {
            CacheParties.addParty(this, getPartyOwner().getUniqueId());
            return true;
        }
    }

}
