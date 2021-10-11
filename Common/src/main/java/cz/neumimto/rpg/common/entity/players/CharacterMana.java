package cz.neumimto.rpg.common.entity.players;

import cz.neumimto.rpg.common.Rpg;
import cz.neumimto.rpg.common.entity.CommonProperties;
import cz.neumimto.rpg.common.entity.EntityService;
import cz.neumimto.rpg.common.entity.IReservable;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.objects.Object2DoubleOpenHashMap;

/**
 * Created by NeumimTo on 30.12.2014.
 */
public class CharacterMana implements IReservable {

    private final IActiveCharacter character;
    private EntityService entityService;

    private Object2DoubleOpenHashMap<String> additionalValues;

    private double additionalValue;

    public CharacterMana(IActiveCharacter activeCharacter) {
        this.entityService = Rpg.get().getEntityService();
        this.character = activeCharacter;
        this.additionalValues = new Object2DoubleOpenHashMap<>();
    }

    @Override
    public double getMaxValue() {
        return additionalValue + entityService.getEntityProperty(character, CommonProperties.max_mana);
    }

    @Override
    public void setMaxValue(double f) {
        character.setProperty(CommonProperties.max_mana, f);
    }

    @Override
    public void setReservedAmnout(float f) {
        character.setProperty(CommonProperties.reserved_mana, f);
    }

    @Override
    public double getReservedAmount() {
        return entityService.getEntityProperty(character, CommonProperties.reserved_mana);
    }

    @Override
    public double getValue() {
        return entityService.getEntityProperty(character, CommonProperties.mana);
    }

    @Override
    public void setValue(double f) {
        if (character.getMana().getMaxValue() < f) {
            f = character.getMana().getMaxValue();
        }

        character.setProperty(CommonProperties.mana, f);
    }

    @Override
    public double getRegen() {
        return entityService.getEntityProperty(character, CommonProperties.mana_regen);
    }

    @Override
    public void setRegen(float f) {
        character.setProperty(CommonProperties.mana_regen, f);
    }

    public void withSource(String key, double value) {
        additionalValues.addTo(key, value);
        recalc();
    }

    public void removeSource(String key) {
        additionalValues.removeDouble(key);
        recalc();
    }

    private void recalc() {
        DoubleIterator iterator = additionalValues.values().iterator();
        while (iterator.hasNext()) {
            additionalValue += iterator.nextDouble();
        }
    }
}
