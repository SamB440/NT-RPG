package cz.neumimto.rpg.spigot.events.party;

import cz.neumimto.rpg.common.entity.players.IActiveCharacter;
import cz.neumimto.rpg.common.entity.players.party.IParty;
import cz.neumimto.rpg.common.events.party.PartyEvent;
import cz.neumimto.rpg.spigot.events.AbstractNEvent;
import org.bukkit.event.Cancellable;

public abstract class SpigotAbstractPartyEvent extends AbstractNEvent implements PartyEvent, Cancellable {

    private IParty party;
    private IActiveCharacter character;
    private boolean cancelled;

    @Override
    public IParty getParty() {
        return party;
    }

    @Override
    public void setParty(IParty party) {
        this.party = party;
    }

    @Override
    public IActiveCharacter getCharacter() {
        return character;
    }

    @Override
    public void setCharacter(IActiveCharacter character) {
        this.character = character;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

}
