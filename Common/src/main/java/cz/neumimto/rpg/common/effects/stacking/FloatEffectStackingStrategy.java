package cz.neumimto.rpg.common.effects.stacking;


import cz.neumimto.rpg.common.effects.EffectStackingStrategy;

/**
 * Created by NeumimTo on 2.4.2017.
 */
public class FloatEffectStackingStrategy implements EffectStackingStrategy<Float> {

    public static final FloatEffectStackingStrategy INSTANCE = new FloatEffectStackingStrategy();

    private FloatEffectStackingStrategy() {
    }

    @Override
    public Float mergeValues(Float current, Float toAdd) {
        return current + toAdd;
    }

    @Override
    public Float getDefaultValue() {
        return 0f;
    }
}
