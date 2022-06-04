package pfg.party;

import com.velocitypowered.api.proxy.Player;

import java.util.ArrayList;

public interface PartyInstance {

    void makePartyOwner(Player player); // owner
    void makePartyModerator(Player player); // moderator
    Player getPartyOwner();
    ArrayList<Player> getPartyModerators();

    int getPartyLimit();
    void setPartyLimit(int limit);

    void invitePlayer(Player player); // invite
    void accept(Player player); // accept
    void deny(Player player); // deny

    void warp(); // warp

    ArrayList<Player> getPartyPlayers(); // list

    boolean cache();

}
