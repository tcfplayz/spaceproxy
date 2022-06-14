package velocity.tcf.spaceproxy;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import velocity.tcf.spaceproxy.level.LevelCalculator;
import velocity.tcf.spaceproxy.util.MySQL;

import java.util.concurrent.TimeUnit;

@Plugin(
        id = "space-proxy",
        name = "SpaceProxy",
        version = "1.1",
        authors = {"tcfplayz"}
)
public class SpaceProxy {

    public static ProxyServer server = null;

    @Inject
    public SpaceProxy(ProxyServer server) {
        SpaceProxy.server = server;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        server.getScheduler()
                .buildTask(this, () -> server.getAllServers().forEach(server -> server.getPlayersConnected().forEach(player -> {
                    LevelCalculator.maxExpAutoRenew(player);
                    MySQL.updatePlaytime(player.getUniqueId().toString(), MySQL.getPlaytime(player.getUniqueId().toString()) + 500);
                })))
                .repeat(500L, TimeUnit.MILLISECONDS)
                .schedule();
        server.getScheduler()
                .buildTask(this, () -> server.getAllServers().forEach(server -> {
                    for (Player p : server.getPlayersConnected()) {
                        MySQL.updateExp(p.getUniqueId().toString(), MySQL.getExpLvl(p.getUniqueId().toString())[0] + 1);
                        MySQL.updateCoins(p.getUniqueId().toString(), MySQL.getCoins(p.getUniqueId().toString()) + 1);
                    }
                }))
                .repeat(75L, TimeUnit.SECONDS)
                .schedule();
    }

}