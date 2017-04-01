/*    
 *     Copyright (c) 2015, NeumimTo https://github.com/NeumimTo
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *     
 */

package cz.neumimto.rpg.effects;

import cz.neumimto.rpg.effects.common.PropertyContainer;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.effect.potion.PotionEffectType;
import org.spongepowered.api.entity.living.Living;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by NeumimTo on 17.1.2015.
 */
public interface IEffectConsumer<T extends Living> extends PropertyContainer {

    T getEntity();

    Map<String, IEffectContainer<IEffect>> getEffectMap();

    default Collection<IEffectContainer<IEffect>> getEffects() {
        return getEffectMap().values();
    }

    default <A extends IEffect> IEffectContainer<A> getEffect(String cl) {
        return (IEffectContainer<A>) getEffectMap().get(cl);
    }

    default boolean hasEffect(String cl) {
        return getEffectMap().containsKey(cl);
    }


    default void addEffect(IEffect effect) {
	    IEffectContainer IEffectContainer1 = getEffectMap().get(effect.getName());
	    if (IEffectContainer1 == null) {
		    getEffectMap().put(effect.getName(), new EffectContainer<>(effect));
	    } else {
		    IEffectContainer1.getEffects().add(effect);
	    }
    }

    default void addEffect(IEffectContainer<IEffect> IEffectContainer) {
        IEffectContainer<IEffect> effectContainer1 = getEffectMap().get(IEffectContainer.getName());
        if (effectContainer1 == null) {
            getEffectMap().put(IEffectContainer.getName(), IEffectContainer);
        } else {
            effectContainer1.mergeWith(IEffectContainer);
        }
    }

    default void removeEffect(String cl) {
        getEffectMap().remove(cl);
    }

    default void removeEffect(IEffect cl) {
        getEffectMap().remove(cl.getName());
    }


    default void addPotionEffect(PotionEffectType p, int amplifier, long duration) {
        PotionEffect build = PotionEffect.builder().potionType(p).amplifier(amplifier).duration((int) duration).build();
        List<PotionEffect> potionEffects = getEntity().get(Keys.POTION_EFFECTS).get();
        potionEffects.add(build);
        getEntity().offer(Keys.POTION_EFFECTS, potionEffects);
    }

    default void addPotionEffect(PotionEffectType p, int amplifier, long duration, boolean partciles) {
        PotionEffect build = PotionEffect.builder().particles(partciles).potionType(p).amplifier(amplifier).duration((int) duration).build();
        addPotionEffect(build);
    }

    default void removePotionEffect(PotionEffectType type) {
        List<PotionEffect> potionEffects = getEntity().get(Keys.POTION_EFFECTS).get();
        List l = potionEffects.stream().filter(p -> p.getType() != type).collect(Collectors.toList());
        getEntity().offer(Keys.POTION_EFFECTS, l);
    }

    default boolean hasPotionEffect(PotionEffectType type) {
        List<PotionEffect> potionEffects = getEntity().get(Keys.POTION_EFFECTS).get();
        for (PotionEffect potionEffect : potionEffects) {
            if (potionEffect.getType() == type) {
                return true;
            }
        }
        return false;
    }

    default void addPotionEffect(PotionEffect e) {
        List<PotionEffect> potionEffects = getEntity().get(Keys.POTION_EFFECTS).get();
        potionEffects.add(e);
        getEntity().offer(Keys.POTION_EFFECTS, potionEffects);
    }

    void sendMessage(String message);
}
