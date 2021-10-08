package cz.neumimto.rpg.spigot.effects.common;

import cz.neumimto.rpg.common.effects.EffectBase;
import cz.neumimto.rpg.common.effects.Generate;
import cz.neumimto.rpg.common.effects.IEffect;
import cz.neumimto.rpg.common.entity.IEffectConsumer;
import cz.neumimto.rpg.spigot.entities.players.ISpigotCharacter;
import org.bukkit.entity.Player;

@Generate(id = "name", description = "Converts all incoming healing to damage")
public class UnlimtedFoodLevelEffect extends EffectBase<Double> {

    public static String name = "Unlimited Food";

    public UnlimtedFoodLevelEffect(IEffectConsumer consumer, long duration, double multipler) {
        super(name, consumer);
        setDuration(duration);
        setValue(multipler);
        setPeriod(10000);
    }


    @Override
    public void onTick(IEffect self) {
        ISpigotCharacter character = (ISpigotCharacter) getConsumer();
        Player player = character.getPlayer();
        player.setFoodLevel(19);
    }
}
