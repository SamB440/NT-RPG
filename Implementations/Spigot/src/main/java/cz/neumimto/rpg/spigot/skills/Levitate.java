package cz.neumimto.rpg.spigot.skills;

import com.google.auto.service.AutoService;
import cz.neumimto.rpg.common.ResourceLoader;
import cz.neumimto.rpg.common.effects.IEffect;
import cz.neumimto.rpg.common.skills.ISkill;
import cz.neumimto.rpg.common.skills.PlayerSkillContext;
import cz.neumimto.rpg.common.skills.ToggleableSkill;
import cz.neumimto.rpg.spigot.entities.players.ISpigotCharacter;

import javax.inject.Singleton;

@Singleton
@AutoService(ISkill.class)
@ResourceLoader.Skill("ntrpg:levitate")
public class Levitate extends ToggleableSkill<ISpigotCharacter> {

    @Override
    public String getEffectName() {
        return "";
    }

    @Override
    public IEffect constructEffect(ISpigotCharacter character, PlayerSkillContext info) {
        return null;
    }
}
