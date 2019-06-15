package cz.neumimto.events;

import cz.neumimto.effects.positive.DodgeEffect;
import cz.neumimto.rpg.api.effects.IEffectContainer;
import cz.neumimto.rpg.api.entity.IEntity;
import cz.neumimto.rpg.api.skills.scripting.JsBinding;
import cz.neumimto.rpg.sponge.events.CancellableNEvent;

/**
 * Created by NeumimTo on 6.7.2017.
 */
@JsBinding(JsBinding.Type.CLASS)
public class DamageDodgedEvent extends CancellableNEvent {

	private final IEntity source;
	private final IEntity target;
	private final IEffectContainer<Float, DodgeEffect> effect;

	public DamageDodgedEvent(IEntity source, IEntity target, IEffectContainer<Float, DodgeEffect> effect) {
		this.source = source;
		this.target = target;
		this.effect = effect;
	}

	@Override
	public IEntity getSource() {
		return source;
	}

	public IEntity getTarget() {
		return target;
	}

	public IEffectContainer<Float, DodgeEffect> getEffect() {
		return effect;
	}
}
