package cz.neumimto.rpg.common.effects.common;

import cz.neumimto.rpg.common.effects.CommonEffectTypes;
import cz.neumimto.rpg.common.effects.EffectBase;
import cz.neumimto.rpg.common.effects.Generate;
import cz.neumimto.rpg.common.entity.IEffectConsumer;

/**
 * Created by NeumimTo on 17.3.2016.
 */
@Generate(id = "name", description = "An effect which silences the target. Silenced target cannot casts spells, unless the skill has"
        + " type CAN_CAST_WHILE_SILENCED")
public class Silence extends EffectBase {

    public static String name = "Silence";

    public Silence(IEffectConsumer consumer, long duration) {
        super(name, consumer);
        setDuration(duration);
        addEffectType(CommonEffectTypes.SILENCE);
    }

    @Override
    public String getName() {
        return name;
    }
}
