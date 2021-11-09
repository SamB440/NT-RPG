package cz.neumimto.rpg.spigot.events.damage;

import cz.neumimto.rpg.common.events.damage.DamageIEntityLateEvent;
import org.bukkit.event.HandlerList;

public class SpigotDamageIEntityLateEvent extends SpigotAbstractDamageEvent implements DamageIEntityLateEvent {
    private static final HandlerList HANDLERS_LIST = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
}
