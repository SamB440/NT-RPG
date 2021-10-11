package cz.neumimto.rpg.spigot.events.party;

import cz.neumimto.rpg.common.events.party.PartyInviteEvent;
import org.bukkit.event.HandlerList;

public class SpigotPartyInviteEvent extends SpigotAbstractPartyEvent implements PartyInviteEvent {
    private static final HandlerList HANDLERS_LIST = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
}