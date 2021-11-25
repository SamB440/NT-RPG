package cz.neumimto.rpg.common.scripting.mechanics;

import com.google.auto.service.AutoService;
import cz.neumimto.nts.annotations.ScriptMeta.Function;
import cz.neumimto.nts.annotations.ScriptMeta.Handler;
import cz.neumimto.nts.annotations.ScriptMeta.NamedParam;
import cz.neumimto.rpg.common.entity.IEntity;
import cz.neumimto.rpg.common.entity.IEntityType;
import cz.neumimto.rpg.common.logging.Log;
import cz.neumimto.rpg.common.skills.PlayerSkillContext;
import jdk.jfr.Name;

import java.util.logging.Logger;

@AutoService(NTScriptProxy.class)
public class SkillCommons implements NTScriptProxy {

    private static Logger logger = Logger.getLogger("NTS-DEBUG");

    @Handler
    @Function("config_value")
    public double configValue(
            @NamedParam("c|ctx") PlayerSkillContext skillContext,
            @NamedParam("k|key") String key
    ) {
        return skillContext.getDoubleNodeValue(key);
    }

    @Handler
    @Function("exists")
    public boolean exists(@NamedParam("o|obj|var") Object o) {
        return o != null;
    }

    @Handler
    @Function("print")
    public void print(@NamedParam("o|obj|var") Object o) {
        logger.info(String.valueOf(o));
    }

    @Handler
    @Function("printd")
    public void printd(@NamedParam("d|n")double d) {
        logger.info(""+d);
    }
}
