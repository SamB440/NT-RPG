package cz.neumimto.rpg;

import com.electronwill.nightconfig.core.conversion.ObjectConverter;
import com.electronwill.nightconfig.core.file.FileConfig;
import com.google.inject.Injector;
import cz.neumimto.rpg.api.entity.players.IActiveCharacter;
import cz.neumimto.rpg.api.skills.scripting.ScriptSkillModel;
import cz.neumimto.rpg.api.skills.types.ActiveSkill;
import cz.neumimto.rpg.common.skills.mech.DamageMechanic;
import cz.neumimto.rpg.common.skills.scripting.Caster;
import cz.neumimto.rpg.common.skills.scripting.CustomSkillGenerator;
import cz.neumimto.rpg.common.skills.scripting.SkillArgument;
import cz.neumimto.rpg.common.skills.scripting.SkillMechanic;
import cz.neumimto.rpg.junit.CharactersExtension;
import cz.neumimto.rpg.junit.NtRpgExtension;
import cz.neumimto.rpg.junit.TestGuiceModule;
import name.falgout.jeffrey.testing.junit.guice.GuiceExtension;
import name.falgout.jeffrey.testing.junit.guice.IncludeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;

@ExtendWith({CharactersExtension.class, GuiceExtension.class, NtRpgExtension.class})
@IncludeModule(TestGuiceModule.class)
public class CustomSkillGeneratorTests {

    @Inject
    private CustomSkillGenerator customSkillGenerator;

    @Inject
    private Injector injector;

    private static AtomicBoolean atomicBoolean;

    @BeforeEach
    public void before() {
        atomicBoolean = new AtomicBoolean(false);
    }

    @Test
    public void test() {
        URL resource = getClass().getClassLoader().getResource("skillgen/test01.conf");
        ScriptSkillModel model;
        try (FileConfig fileConfig = FileConfig.of(new File(resource.getPath()))) {
            fileConfig.load();

            model = new ObjectConverter().toObject(fileConfig, ScriptSkillModel::new);
            injector.getInstance(DamageMechanic.class);

            Class<ActiveSkill> i= (Class<ActiveSkill>) customSkillGenerator.generate(model);
            injector.getInstance(i).cast(null, null);
        }
    }

    @Singleton
    @SkillMechanic("ntrpg:test_mechanic")
    private static class TestMechanic {

        public void doAction(@Caster IActiveCharacter character, @SkillArgument("settings.node") float value) {

        }
    }
}
