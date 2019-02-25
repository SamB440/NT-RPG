package cz.neumimto.skills.active;

import com.flowpowered.math.imaginary.Quaterniond;
import com.flowpowered.math.vector.Vector3d;
import cz.neumimto.core.ioc.Inject;
import cz.neumimto.rpg.IEntity;
import cz.neumimto.rpg.ResourceLoader;
import cz.neumimto.rpg.damage.SkillDamageSource;
import cz.neumimto.rpg.damage.SkillDamageSourceBuilder;
import cz.neumimto.rpg.entities.EntityService;
import cz.neumimto.rpg.players.IActiveCharacter;
import cz.neumimto.rpg.skills.PlayerSkillContext;
import cz.neumimto.rpg.skills.SkillNodes;
import cz.neumimto.rpg.skills.SkillResult;
import cz.neumimto.rpg.skills.mods.SkillContext;
import cz.neumimto.rpg.skills.parents.Targetted;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleOptions;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.event.cause.entity.damage.DamageTypes;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.util.Color;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

/**
 * Created by NeumimTo on 20.8.2017.
 */
@ResourceLoader.Skill("ntrpg:harmtouch")
public class Harmtouch extends Targetted {

	@Inject
	private EntityService entityService;

	public void init() {
		super.init();
		settings.addNode(SkillNodes.DAMAGE, 5000, 100);
		setDamageType(DamageTypes.MAGIC);
		setIcon(ItemTypes.BLAZE_ROD);
	}

	@Override
	public void castOn(Living target, IActiveCharacter source, PlayerSkillContext info, SkillContext skillContext) {
		SkillDamageSourceBuilder builder = new SkillDamageSourceBuilder();
		builder.fromSkill(this);
		IEntity e = entityService.get(target);
		builder.setTarget(e);
		builder.setCaster(source);
		SkillDamageSource s = builder.build();
		float damage = skillContext.getFloatNodeValue(SkillNodes.DAMAGE);
		boolean damage1 = e.getEntity().damage(damage, s);
		if (damage1) {
			Vector3d r = source.getEntity().getRotation();
			Vector3d dir = Quaterniond.fromAxesAnglesDeg(r.getX(), -r.getY(), r.getZ()).getDirection();
			Location<World> location = e.getEntity().getLocation();
			location.getExtent().spawnParticles(ParticleEffect.builder()
							.option(ParticleOptions.COLOR, Color.ofRgb(207, 23, 255))
							.option(ParticleOptions.QUANTITY, 3)
							.velocity(dir.normalize())
							.build(),
					e.getEntity().getLocation().getPosition()
			);

			location.getExtent().spawnParticles(ParticleEffect.builder()
							.option(ParticleOptions.COLOR, Color.RED)
							.option(ParticleOptions.QUANTITY, 5)
							.velocity(dir.normalize().mul(1.5))
							.build(),
					e.getEntity().getLocation().getPosition());
		}
		skillContext.next(source, info, SkillResult.OK);
	}
}
