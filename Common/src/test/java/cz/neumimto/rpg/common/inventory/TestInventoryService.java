package cz.neumimto.rpg.common.inventory;

import cz.neumimto.rpg.common.model.EquipedSlot;
import cz.neumimto.rpg.common.entity.TestCharacter;

import javax.inject.Singleton;

@Singleton
public class TestInventoryService extends AbstractInventoryService<TestCharacter> {


    @Override
    public void load() {

    }

    @Override
    public void initializeCharacterInventory(TestCharacter character) {

    }

    @Override
    public EquipedSlot createEquipedSlot(String className, int slotId) {
        return () -> slotId;
    }

    @Override
    public void invalidateGUICaches(TestCharacter cc) {

    }


}
