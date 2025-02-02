package cz.neumimto.rpg.common.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Flags;
import co.aikar.commands.annotation.Subcommand;
import cz.neumimto.rpg.common.entity.players.IActiveCharacter;
import cz.neumimto.rpg.common.entity.players.parties.PartyService;

import javax.inject.Inject;

@Subcommand("nparty|np")
@CommandPermission("ntrpg.player.party")
public class PartyCommands extends BaseCommand {

    @Inject
    private PartyCommandFacade partyCommandFacade;

    @Inject
    private PartyService partyService;

    @Subcommand("accept")
    @CommandPermission("ntrpg.party.accept")
    public void acceptPartyInviteCommand(IActiveCharacter character) {
        if (character.getPendingPartyInvite() != null) {
            partyService.addToParty(character.getPendingPartyInvite(), character);
        }
    }

    @Subcommand("invite")
    @CommandPermission("ntrpg.party.invite")
    public void inviteToPartyCommand(IActiveCharacter character, @Flags("target") OnlineOtherPlayer target) {
        partyService.sendPartyInvite(character.getParty(), target.character);
    }

    @Subcommand("create")
    @CommandPermission("ntrpg.party.create")
    public void createPartyCommand(IActiveCharacter character) {
        partyCommandFacade.createParty(character);
    }

    @Subcommand("kick")
    @CommandPermission("ntrpg.party.kick")
    public void kickFromPartyCommand(IActiveCharacter character, @Flags("target") OnlineOtherPlayer target) {
        partyService.kickCharacterFromParty(character.getParty(), target.character);
    }
}
