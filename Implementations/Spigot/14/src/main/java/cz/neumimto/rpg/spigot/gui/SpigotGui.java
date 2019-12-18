package cz.neumimto.rpg.spigot.gui;

import cz.neumimto.rpg.api.effects.EffectStatusType;
import cz.neumimto.rpg.api.effects.IEffect;
import cz.neumimto.rpg.api.entity.players.classes.ClassDefinition;
import cz.neumimto.rpg.api.entity.players.classes.PlayerClassData;
import cz.neumimto.rpg.api.gui.IPlayerMessage;
import cz.neumimto.rpg.api.inventory.CannotUseItemReason;
import cz.neumimto.rpg.api.localization.Arg;
import cz.neumimto.rpg.api.localization.LocalizationKeys;
import cz.neumimto.rpg.api.localization.LocalizationService;
import cz.neumimto.rpg.api.persistance.model.CharacterBase;
import cz.neumimto.rpg.api.skills.tree.SkillTree;
import cz.neumimto.rpg.common.inventory.runewords.RuneWord;
import cz.neumimto.rpg.spigot.entities.players.ISpigotCharacter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class SpigotGui implements IPlayerMessage<ISpigotCharacter> {

    @Inject
    private LocalizationService localizationService;

    @Override
    public boolean isClientSideGui() {
        return false;
    }

    @Override
    public void sendCooldownMessage(ISpigotCharacter player, String message, double cooldown) {
        player.sendMessage(localizationService.translate(LocalizationKeys.ON_COOLDOWN, Arg.arg("skill", message).with("time", cooldown)));
    }

    @Override
    public void sendEffectStatus(ISpigotCharacter player, EffectStatusType type, IEffect effect) {

    }

    @Override
    public void invokeCharacterMenu(ISpigotCharacter player, List<CharacterBase> characterBases) {

    }

    @Override
    public void sendPlayerInfo(ISpigotCharacter character, ISpigotCharacter target) {

    }

    @Override
    public void showExpChange(ISpigotCharacter character, String classname, double expchange) {

    }

    @Override
    public void showLevelChange(ISpigotCharacter character, PlayerClassData clazz, int level) {

    }

    @Override
    public void sendStatus(ISpigotCharacter character) {

    }

    @Override
    public void sendListOfCharacters(ISpigotCharacter player, CharacterBase currentlyCreated) {
        SpigotGuiHelper.sendcharacters(player.getPlayer(), player, currentlyCreated);
    }

    @Override
    public void showClassInfo(ISpigotCharacter character, ClassDefinition cc) {

    }

    @Override
    public void sendListOfRunes(ISpigotCharacter character) {

    }

    @Override
    public void displayGroupArmor(ClassDefinition g, ISpigotCharacter target) {

    }

    @Override
    public void displayGroupWeapon(ClassDefinition g, ISpigotCharacter target) {

    }

    @Override
    public void displayAttributes(ISpigotCharacter target, ClassDefinition group) {

    }

    @Override
    public void displayMana(ISpigotCharacter character) {

    }

    @Override
    public void sendCannotUseItemNotification(ISpigotCharacter character, String item, CannotUseItemReason reason) {
        if (reason == CannotUseItemReason.CONFIG) {
            character.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, translate(LocalizationKeys.CANNOT_USE_ITEM_CONFIGURATION_REASON));
        } else if (reason == CannotUseItemReason.LEVEL) {
            BaseComponent translate = translate(LocalizationKeys.CANNOT_USE_ITEM_LEVEL_REASON);
            translate.setColor(ChatColor.RED);
            character.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, translate);
        } else if (reason == CannotUseItemReason.LORE) {
            BaseComponent translate = translate(LocalizationKeys.CANNOT_USE_ITEM_LORE_REASON);
            translate.setColor(ChatColor.RED);
            character.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, translate);
        }
    }

    private BaseComponent translate(String key) {
        return TextComponent.fromLegacyText(localizationService.translate(key))[0];
    }

    @Override
    public void openSkillTreeMenu(ISpigotCharacter player) {

    }

    @Override
    public void moveSkillTreeMenu(ISpigotCharacter character) {

    }

    @Override
    public void displaySkillDetailsInventoryMenu(ISpigotCharacter character, SkillTree tree, String command) {

    }

    @Override
    public void displayInitialProperties(ClassDefinition byName, ISpigotCharacter player) {

    }

    @Override
    public void sendClassesByType(ISpigotCharacter character, String def) {
        Player player = character.getPlayer();
        Inventory inventory = SpigotGuiHelper.createMenuInventoryClassesByTypeView(player, def);
        player.openInventory(inventory);
    }

    @Override
    public void sendClassTypes(ISpigotCharacter character) {
        Player player = character.getPlayer();
        Inventory inventory = SpigotGuiHelper.createMenuInventoryClassTypesView(player);
        player.openInventory(inventory);
    }

    @Override
    public void displayCharacterMenu(ISpigotCharacter character) {

    }

    @Override
    public void displayCharacterAttributes(ISpigotCharacter character) {

    }

    @Override
    public void displayCurrentClicks(ISpigotCharacter character, String combo) {

    }

    @Override
    public void displayCharacterArmor(ISpigotCharacter character, int page) {

    }

    @Override
    public void displayCharacterWeapons(ISpigotCharacter character, int page) {

    }

    public void displayRuneword(ISpigotCharacter character, RuneWord runeword, boolean b) {

    }

    public void displayRunewordAllowedItems(ISpigotCharacter character, RuneWord runeWord) {

    }

    public void displayRunewordAllowedGroups(ISpigotCharacter character, RuneWord runeWord) {

    }

    public void displayRunewordRequiredGroups(ISpigotCharacter character, RuneWord runeWord) {

    }

    public void displayRunewordBlockedGroups(ISpigotCharacter character, RuneWord runeWord) {

    }


}
