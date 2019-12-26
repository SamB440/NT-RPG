package cz.neumimto.rpg.spigot.gui;

import cz.neumimto.rpg.api.Rpg;
import cz.neumimto.rpg.api.configuration.AttributeConfig;
import cz.neumimto.rpg.api.configuration.ClassTypeDefinition;
import cz.neumimto.rpg.api.entity.players.classes.ClassDefinition;
import cz.neumimto.rpg.api.items.ClassItem;
import cz.neumimto.rpg.api.localization.LocalizationKeys;
import cz.neumimto.rpg.api.logging.Log;
import cz.neumimto.rpg.api.persistance.model.CharacterBase;
import cz.neumimto.rpg.common.utils.model.CharacterListModel;
import cz.neumimto.rpg.spigot.damage.SpigotDamageService;
import cz.neumimto.rpg.spigot.entities.players.ISpigotCharacter;
import cz.neumimto.rpg.spigot.items.SpigotRpgItemType;
import de.tr7zw.nbtapi.NBTItem;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class SpigotGuiHelper {

    public static Inventory createMenuInventoryClassTypesView(Player player) {
        Map<String, ClassTypeDefinition> class_types = Rpg.get().getPluginConfig().CLASS_TYPES;
        Inventory classes = createInventoryTemplate(player, "Classes");
        makeBorder(classes, Material.WHITE_STAINED_GLASS_PANE);
        for (Map.Entry<String, ClassTypeDefinition> entry : class_types.entrySet()) {
            ItemStack itemStack = button(Material.CRAFTING_TABLE,
                    ChatColor.valueOf(entry.getValue().getPrimaryColor()) + entry.getKey(),
                    "ninfo classes " + entry.getKey());
            classes.addItem(itemStack);
        }
        return classes;
    }

    public static Inventory createMenuInventoryClassesByTypeView(Player player, String classType) {
        Map<String, ClassTypeDefinition> class_types = Rpg.get().getPluginConfig().CLASS_TYPES;
        ClassTypeDefinition definition = class_types.get(classType);
        Inventory classes = createInventoryTemplate(player, classType);
        DyeColor dyeColor = DyeColor.valueOf(definition.getDyeColor());
        makeBorder(classes, Material.getMaterial(dyeColor.name() + "_STAINED_GLASS_PANE"));

        Rpg.get().getClassService().getClassDefinitions().stream()
                .filter(a -> a.getClassType().equalsIgnoreCase(classType))
                .forEach(a -> classes.addItem(toItemStack(a)));

        return classes;
    }

    private static ItemStack toItemStack(ClassDefinition a) {
        String sItemType = a.getItemType();
        Material material = Material.matchMaterial(sItemType);
        ItemStack itemStack = button(material, ChatColor.valueOf(a.getPreferedColor()) + a.getName(), "ninfo class " + a.getName());

        List<String> lore;
        if (!(a.getCustomLore() == null || a.getCustomLore().isEmpty())) {
            lore = a.getCustomLore().stream().map(SpigotGuiHelper::parseStr).collect(Collectors.toList());
        } else {
            lore = new ArrayList<>();
            lore.add(ChatColor.valueOf(a.getPreferedColor()) + a.getName());
            lore.add(ChatColor.BOLD.toString() + ChatColor.valueOf(a.getPreferedColor()) + a.getClassType());

            lore.add(" ");
            if (a.getDescription() != null) {
                List<String> description = a.getDescription();
                for (String s : description) {
                    lore.add(ChatColor.ITALIC.toString() + ChatColor.GOLD + s);
                }
            }
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        itemStack = unclickableInterface(itemStack);

        return itemStack;
    }

    private static Inventory createInventoryTemplate(Player player, String title) {
        return Bukkit.createInventory(player, 6 * 9, title);
    }

    public static void makeBorder(Inventory i, Material material) {
        if (i.getType() == InventoryType.CHEST) {
            for (int j = 0; j < 9; j++) {
                ItemStack of = unclickableInterface(material);
                i.setItem(j, of);

                of = unclickableInterface(material);
                i.setItem(j + 45, of);
            }

            for (int j = 1; j < 5; j++) {
                ItemStack of = unclickableInterface(material);
                i.setItem(9 * j, of);

                of = unclickableInterface(material);
                i.setItem(9 * j + 8, of);
            }

        }


    }

    private static ItemStack button(Material material, String name, String command) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
        NBTItem nbti = new NBTItem(itemStack);
        nbti.setString("ntrpg.item-command", command);
        return nbti.getItem();
    }

    private static ItemStack unclickableInterface(Material material) {
        ItemStack itemStack = new ItemStack(material);
        return unclickableInterface(itemStack);
    }

    private static ItemStack unclickableInterface(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(" ");
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(itemMeta);
        NBTItem nbti = new NBTItem(itemStack);
        nbti.setBoolean("ntrpg.item-iface", true);
        return nbti.getItem();
    }

    private static ItemStack unclickableInterfaceKeepName(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(itemMeta);
        NBTItem nbti = new NBTItem(itemStack);
        nbti.setBoolean("ntrpg.item-iface", true);
        return nbti.getItem();
    }

    public static void sendcharacters(Player player, ISpigotCharacter player1, CharacterBase currentlyCreated) {
        CompletableFuture.runAsync(() -> {
            List<CharacterBase> playersCharacters = Rpg.get().getCharacterService().getPlayersCharacters(player.getUniqueId());

            for (CharacterBase base : playersCharacters) {
                TextComponent message = new TextComponent("[");
                message.setColor(net.md_5.bungee.api.ChatColor.YELLOW);
                TextComponent inner;
                if (base.getName().equalsIgnoreCase(currentlyCreated.getName())) {
                    inner = new TextComponent("*");
                    inner.setColor(net.md_5.bungee.api.ChatColor.RED);
                } else {
                    inner = new TextComponent("SELECT");
                    inner.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "character switch " + base.getName()));
                    inner.setColor(net.md_5.bungee.api.ChatColor.GREEN);
                }
                message.addExtra(inner);
                message.addExtra("] ");
                message.setColor(net.md_5.bungee.api.ChatColor.YELLOW);

                TextComponent textComponent = new TextComponent(base.getName() + " ");
                textComponent.setColor(net.md_5.bungee.api.ChatColor.GOLD);

                message.addExtra(textComponent);
                player.spigot().sendMessage(textComponent);
            }


        }, Rpg.get().getAsyncExecutor()).exceptionally(throwable -> {
            Log.error("Could not get character list", throwable);
            return null;
        });
    }

    private static String parseStr(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static Inventory createClassInfoView(Player player, ClassDefinition cc) {
        Inventory i = createInventoryTemplate(player, ChatColor.valueOf(cc.getPreferedColor()) + cc.getName());
        i.setItem(0, button(Material.PAPER, Rpg.get().getLocalizationService().translate(LocalizationKeys.BACK), "ninfo classes"));
        i.setItem(8, button(Material.DIAMOND, Rpg.get().getLocalizationService().translate(LocalizationKeys.CONFIRM), "char choose class " + cc.getName()));
        if (!cc.getAllowedArmor().isEmpty()) {
            i.setItem(29, button(Material.DIAMOND_CHESTPLATE, Rpg.get().getLocalizationService().translate(LocalizationKeys.ARMOR), "ninfo class-armor " + cc.getName()));
        }
        if (!cc.getWeapons().isEmpty() || !cc.getOffHandWeapons().isEmpty()) {
            i.setItem(30, button(Material.DIAMOND_SWORD, Rpg.get().getLocalizationService().translate(LocalizationKeys.WEAPONS), "ninfo class-weapons " + cc.getName()));
        }
        return i;
    }

    public static Inventory createClassWeaponView(Player player, ClassDefinition cc, Set<ClassItem> weapons) {
        String translate = Rpg.get().getLocalizationService().translate(LocalizationKeys.WEAPONS);
        Inventory i = createInventoryTemplate(player, ChatColor.valueOf(cc.getPreferedColor()) + cc.getName() + ChatColor.RESET + translate);
        i.setItem(0, button(Material.PAPER, Rpg.get().getLocalizationService().translate(LocalizationKeys.BACK), "ninfo class " + cc.getName()));
        int w = 9;
        SpigotDamageService damageService = (SpigotDamageService) Rpg.get().getDamageService();
        String dmg = Rpg.get().getLocalizationService().translate(LocalizationKeys.ITEM_DAMAGE);
        if (weapons == null) {
            weapons = Collections.emptySet();
        }
        for (ClassItem weapon : weapons) {
            SpigotRpgItemType type = (SpigotRpgItemType) weapon.getType();
            ItemStack itemStack = new ItemStack(type.getMaterial());
            double damage = weapon.getDamage();

            if (damage > 0) {
                ChatColor colorByDamage = ChatColor.valueOf(damageService.getColorByDamage(damage));
                ItemMeta itemMeta = itemStack.getItemMeta();
                if (type.getModelId() != null) {
                    itemMeta.setCustomModelData(Integer.parseInt(type.getModelId()));
                }
                List<String> list = new ArrayList<>();
                list.add(ChatColor.GRAY + dmg + ":" + colorByDamage + damage);
                itemMeta.setLore(list);
                itemStack.setItemMeta(itemMeta);
            }
            i.setItem(w, unclickableInterface(itemStack));
            w++;
        }
        return i;
    }

    public static Inventory createArmorView(Player player, ClassDefinition cc, Set<ClassItem> weapons) {
        String translate = Rpg.get().getLocalizationService().translate(LocalizationKeys.ARMOR);
        Inventory i = createInventoryTemplate(player, ChatColor.valueOf(cc.getPreferedColor()) + cc.getName() + " " + ChatColor.RESET + translate);
        i.setItem(0, button(Material.PAPER, Rpg.get().getLocalizationService().translate(LocalizationKeys.BACK), "ninfo class " + cc.getName()));
        int w = 9;
        for (ClassItem weapon : weapons) {
            SpigotRpgItemType type = (SpigotRpgItemType) weapon.getType();
            ItemStack itemStack = new ItemStack(type.getMaterial());
            double damage = weapon.getDamage();

            if (damage > 0) {
                ItemMeta itemMeta = itemStack.getItemMeta();
                if (type.getModelId() != null) {
                    itemMeta.setCustomModelData(Integer.parseInt(type.getModelId()));
                }
                itemStack.setItemMeta(itemMeta);
            }
            i.setItem(w, unclickableInterface(itemStack));
            w++;
        }
        return i;
    }

    public static Inventory createClassAttributesView(Player player, ClassDefinition cc) {
        String translate = Rpg.get().getLocalizationService().translate(LocalizationKeys.ARMOR);
        Map<AttributeConfig, Integer> attrs = cc.getStartingAttributes();
        Inventory i = createInventoryTemplate(player, ChatColor.valueOf(cc.getPreferedColor()) + cc.getName() + ChatColor.RESET + translate);

        i.setItem(0, button(Material.PAPER, Rpg.get().getLocalizationService().translate(LocalizationKeys.BACK), "ninfo class " + cc.getName()));

        int w = 9;

        translate = Rpg.get().getLocalizationService().translate(LocalizationKeys.ATTRIBUTES);
        for (Map.Entry<AttributeConfig, Integer> attr : attrs.entrySet()) {
            AttributeConfig att = attr.getKey();
            ItemStack itemStack = new ItemStack(Material.matchMaterial(att.getItemType()));
            ItemMeta itemMeta = itemStack.getItemMeta();
            List<String> lore = new ArrayList<>();

            lore.add(ChatColor.GREEN.toString() + ChatColor.BOLD + att.getName() + ChatColor.RESET + " - " + ChatColor.GREEN + attr.getValue());
            lore.add(" ");
            if (att.getDescription() != null || ! att.getDescription().isEmpty()) {
                lore.add(ChatColor.ITALIC.toString() + ChatColor.GOLD + att.getDescription());
            }
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
            i.setItem(w, unclickableInterface(itemStack));
            w++;
        }

        return i;
    }
}