package velocity.tcf.spaceproxy.level;

import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import velocity.tcf.spaceproxy.util.MySQL;

public class LevelCalculator {

    private static int calculateMaximumExperienceRequired(int l) {
        return (30 * (l + 1));
    }

    public static void maxExpAutoRenew(Player player) {
        int lvl = MySQL.getExpLvl(player.getUniqueId().toString())[1];
        int exp = MySQL.getExpLvl(player.getUniqueId().toString())[0];
        if (exp >= calculateMaximumExperienceRequired(lvl)) {
            MySQL.updateLevel(player.getUniqueId().toString(), MySQL.getExpLvl(player.getUniqueId().toString())[1]);
            MySQL.updateExp(player.getUniqueId().toString(), 0);
            player.sendMessage(Component.text("You have achieved level " + MySQL.getExpLvl(player.getUniqueId().toString())[1], NamedTextColor.GREEN));
        }
        else return;
    }
}
