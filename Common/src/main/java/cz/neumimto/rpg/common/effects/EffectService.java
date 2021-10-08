package cz.neumimto.rpg.common.effects;

import cz.neumimto.rpg.common.entity.IEffectConsumer;
import cz.neumimto.rpg.common.entity.IEntity;
import cz.neumimto.rpg.common.entity.players.IActiveCharacter;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface EffectService {

    void load();

    void registerEffectType(EffectType effectType);

    void registerEffectTypes(Class<? extends Enum> e);

    void stopEffect(IEffect effect);

    void startEffectScheduler();

    <T extends IEffect> boolean addEffect(T effect);

    <T extends IEffect> boolean addEffect(T effect, IEffectSourceProvider effectSourceProvider);

    @SuppressWarnings("unchecked")
    <T extends IEffect> boolean addEffect(T effect, IEffectSourceProvider effectSourceProvider, IEntity entitySource);

    void removeEffect(IEffect effect, IEffectConsumer consumer);

    int removeEffectsByType(IEffectConsumer consumer, Set<EffectType> type);

    <T, E extends IEffect<T>> void removeEffectContainer(IEffectContainer<T, E> container, IEffectConsumer consumer);

    @SuppressWarnings("unchecked")
    void removeEffect(String iEffect, IEffectConsumer consumer, IEffectSourceProvider effectSource);

    void registerGlobalEffect(IGlobalEffect iGlobalEffect);

    void removeGlobalEffect(String name);

    IGlobalEffect getGlobalEffect(String name);

    Map<String, IGlobalEffect> getGlobalEffects();

    void applyGlobalEffectAsEnchantment(IGlobalEffect effect, IEffectConsumer consumer, Map<String, String> value,
                                        IEffectSourceProvider effectSourceType);

    void applyGlobalEffectsAsEnchantments(Map<IGlobalEffect, EffectParams> map, IEffectConsumer consumer,
                                          IEffectSourceProvider effectSourceType);

    void removeGlobalEffectsAsEnchantments(Collection<IGlobalEffect> itemEffects, IActiveCharacter character,
                                           IEffectSourceProvider effectSourceProvider);

    boolean isGlobalEffect(String s);

    @SuppressWarnings("unchecked")
    /**
     * Called only in cases when entities dies, or players logs off
     */
    void removeAllEffects(IEffectConsumer character);

    void stopEffectScheduler();

    void purgeEffectCache();

    Map<IGlobalEffect, EffectParams> parseItemEffects(Map<String, EffectParams> stringEffectParamsMap);

    Optional<EffectType> getEffectType(String effectType);
}
