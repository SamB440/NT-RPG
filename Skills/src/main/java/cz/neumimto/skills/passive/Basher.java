package cz.neumimto.skills.passive;

import antlr.ASdebug.IASDebugStream;
import cz.neumimto.AdditionalProperties;
import cz.neumimto.SkillLocalization;
import cz.neumimto.core.ioc.Inject;
import cz.neumimto.effects.positive.Bash;
import cz.neumimto.model.BashModel;
import cz.neumimto.rpg.ResourceLoader;
import cz.neumimto.rpg.effects.EffectService;
import cz.neumimto.rpg.entities.EntityService;
import cz.neumimto.rpg.players.IActiveCharacter;
import cz.neumimto.rpg.players.properties.DefaultProperties;
import cz.neumimto.rpg.skills.*;
import org.spongepowered.api.event.cause.entity.damage.DamageTypes;

/**
 * Created by NeumimTo on 4.7.2017.
 */
@ResourceLoader.Skill
public class Basher extends PassiveSkill {

	@Inject
	private EffectService effectService;

	@Inject
	private EntityService entityService;

	public Basher() {
		setName("Basher");
		setDescription(SkillLocalization.basher);
		SkillSettings settings = new SkillSettings();
		settings.addNode(SkillNodes.DAMAGE, 10, 10);
		settings.addNode(SkillNodes.CHANCE, 0.1f, 0.005f);
		settings.addNode(SkillNodes.PERIOD, 2500, -100);
		super.settings = settings;
		setDamageType(DamageTypes.ATTACK);
		addSkillType(SkillType.PHYSICAL);
	}

	@Override
	public void applyEffect(ExtendedSkillInfo info, IActiveCharacter character) {
		BashModel model = getBashModel(info, character);
		effectService.addEffect(new Bash(character,-1, model), character, this);
	}

	@Override
	public void skillUpgrade(IActiveCharacter IActiveCharacter, int level) {
		super.skillUpgrade(IActiveCharacter, level);
		ExtendedSkillInfo info = IActiveCharacter.getSkills().get(getName());
		BashModel model = getBashModel(info, IActiveCharacter);
		effectService.removeEffect(Bash.name, IActiveCharacter, this);
		effectService.addEffect(new Bash(IActiveCharacter,-1, model), IActiveCharacter, this);
	}

	private BashModel getBashModel(ExtendedSkillInfo info, IActiveCharacter character) {
		BashModel model = new BashModel();
		model.chance = getIntNodeValue(info, SkillNodes.CHANCE);
		model.cooldown = getLongNodeValue(info, SkillNodes.COOLDOWN);
		model.damage = getIntNodeValue(info, SkillNodes.DAMAGE);
		model.stunDuration = getLongNodeValue(info, SkillNodes.DURATION);
		return model;
	}
}
