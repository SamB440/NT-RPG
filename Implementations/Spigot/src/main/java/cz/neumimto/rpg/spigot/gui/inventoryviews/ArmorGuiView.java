package cz.neumimto.rpg.spigot.gui.inventoryviews;

import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.google.auto.service.AutoService;
import cz.neumimto.rpg.common.Rpg;
import cz.neumimto.rpg.common.items.RpgItemType;
import cz.neumimto.rpg.common.localization.LocalizationKeys;
import cz.neumimto.rpg.common.localization.LocalizationService;
import cz.neumimto.rpg.spigot.entities.players.ISpigotCharacter;
import cz.neumimto.rpg.spigot.entities.players.SpigotCharacterService;
import cz.neumimto.rpg.spigot.gui.elements.GuiCommand;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;

@Singleton
@AutoService(ConfigurableInventoryGui.class)
public class ArmorGuiView extends ConfigurableInventoryGui {

    private static final Map<UUID, ChestGui> cache = new HashMap<>();

    @Inject
    private SpigotCharacterService characterService;

    @Inject
    private LocalizationService localizationService;

    private static ArmorGuiView instance;

    public ArmorGuiView() {
        super("Armor.conf");
        this.instance = this;
    }

    public static ChestGui get(CommandSender commandSender) {
        Player player = (Player) commandSender;
        UUID uniqueId = player.getUniqueId();
        if (cache.containsKey(uniqueId)) {
            return cache.get(uniqueId);
        }
        ChestGui gui = instance.loadGui(player);
        cache.put(uniqueId, gui);
        return gui;
    }

    @Override
    public void clearCache() {
        super.clearCache();
        cache.clear();
    }

    @Override
    public void clearCache(UUID uuid) {
        cache.remove(uuid);
    }

    @Override
    protected String getTitle(CommandSender commandSender, GuiConfig guiConfig, String param) {
        return characterService.getCharacter((Player) commandSender).getName();
    }

    @Override
    public Map<String, List<GuiCommand>> getPaneData(CommandSender commandSender) {
        Map<String, List<GuiCommand>> map = new HashMap<>();
        List<GuiCommand> list = new ArrayList<>();
        if (commandSender instanceof Player player) {
            ISpigotCharacter character = characterService.getCharacter(player);
            Set<RpgItemType> allowedArmor = character.getAllowedArmor();
            for (RpgItemType rpgItemType : allowedArmor) {
                ItemStack itemStack = toItemStack(rpgItemType);
                list.add(new GuiCommand(itemStack));
            }
        }
        map.put("Armor", list);
        return map;
    }

    private static ItemStack toItemStack(RpgItemType key) {
        Material material = Material.matchMaterial(key.getId());
        List<String> lore = new ArrayList<>();
        ItemStack is = new ItemStack(material);

        LocalizationService localizationService = Rpg.get().getLocalizationService();
        String translate = localizationService.translate(LocalizationKeys.ITEM_CLASS);
        lore.add(ChatColor.GRAY + translate + ": " + ChatColor.GREEN + key.getItemClass().getName());
        if (Rpg.get().getPluginConfig().DEBUG.isBalance() && key.getModelId() != null && !key.getModelId().isEmpty()) {
            lore.add(ChatColor.DARK_GRAY + "DEBUG:: CustomModelData:" + key.getModelId());
        }

        ItemMeta itemMeta = is.getItemMeta();
        if (key.getModelId() != null) {
            itemMeta.setCustomModelData(Integer.valueOf(key.getModelId()));
        }
        itemMeta.addItemFlags(ItemFlag.values());
        itemMeta.setLore(lore);
        is.setItemMeta(itemMeta);
        return is;
    }
}
