package cz.neumimto.rpg.junit;

import cz.neumimto.rpg.api.items.ItemClass;
import cz.neumimto.rpg.api.items.RpgItemType;
import cz.neumimto.rpg.common.items.RpgItemTypeImpl;
import cz.neumimto.rpg.api.entity.players.attributes.AttributeConfig;
import cz.neumimto.rpg.api.entity.players.classes.ClassDefinition;

import java.util.Collections;

public class TestDictionary {

    public static final ItemClass WEAPON_CLASS_1 = new ItemClass("weaponclass1") ;
    public static final RpgItemType ITEM_TYPE_WEAPON_1 = new RpgItemTypeImpl("weapon1", null, WEAPON_CLASS_1, 10, 0);

    public static final ItemClass WEAPON_CLASS_2 = new ItemClass("weaponclass2");
    public static final RpgItemType ITEM_TYPE_WEAPON_2 = new RpgItemTypeImpl("weapon2", null, WEAPON_CLASS_2, 11, 0);

    public static final RpgItemType ARMOR_TYPE_1 = new RpgItemTypeImpl("armor1", null, ItemClass.ARMOR, 0, 100);

    public static final AttributeConfig STR = new AttributeConfig("str", "str", 100, Collections.emptyMap(), null, null);
    public static final AttributeConfig AGI = new AttributeConfig("agi", "agi", 100, Collections.emptyMap(), null, null);


    public static final ClassDefinition CLASS_PRIMARY = new ClassDefinition("primary","Primary");

    public static final ClassDefinition CLASS_TERTIARY = new ClassDefinition("tertiary","Tertiary");

    public static final ClassDefinition CLASS_SECONDARY = new ClassDefinition("secondary","Secondary");


    static {
        WEAPON_CLASS_1.getItems().add(ITEM_TYPE_WEAPON_1);
        WEAPON_CLASS_2.getItems().add(ITEM_TYPE_WEAPON_2);

        ItemClass.ARMOR.getItems().add(ARMOR_TYPE_1);

    }
}
