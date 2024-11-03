package me.cooleg.paperboathider.listeners;

import com.noxcrew.noxesium.api.protocol.rule.ServerRuleIndices;
import com.noxcrew.noxesium.paper.api.event.NoxesiumPlayerRegisteredEvent;
import me.superneon4ik.noxesiumutils.NoxesiumUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class NoxesiumUtilsHook implements Listener {


    private final BoatListeners listeners;

    public NoxesiumUtilsHook(BoatListeners listeners) {
        this.listeners = listeners;
    }

    @EventHandler
    public void noxesiumLoginEvent(NoxesiumPlayerRegisteredEvent event) {
        if (event.getProtocolVersion() < 4) {return;}

        var rule = NoxesiumUtils.getManager()
                .getServerRule(event.getPlayer(), ServerRuleIndices.DISABLE_BOAT_COLLISIONS);
        if (rule == null) return;

        listeners.showAll(event.getPlayer());
        rule.setValue(true);
    }

    public boolean canShowSafely(Player player) {
        Integer ver = NoxesiumUtils.getManager().getProtocolVersion(player);
        if (ver == null) {return false;}
        return ver >= 4;
    }

}
