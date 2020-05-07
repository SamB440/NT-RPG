package cz.neumimto.rpg.api.skills.scripting;

import cz.neumimto.rpg.api.Rpg;
import cz.neumimto.rpg.api.entity.players.IActiveCharacter;
import cz.neumimto.rpg.api.logging.Log;
import cz.neumimto.rpg.api.skills.ISkillType;
import cz.neumimto.rpg.api.skills.PlayerSkillContext;
import cz.neumimto.rpg.api.skills.SkillResult;
import cz.neumimto.rpg.api.skills.types.ActiveSkill;
import cz.neumimto.rpg.api.skills.types.ScriptSkill;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

import javax.script.Bindings;
import javax.script.CompiledScript;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import java.util.List;
import java.util.Optional;

/**
 * Created by NeumimTo on 3.9.2018.
 */
public class ActiveScriptSkill extends ActiveSkill<IActiveCharacter> implements ScriptSkill<ScriptExecutorSkill> {

    private ScriptExecutorSkill executor;

    private ScriptSkillModel model;

    private CompiledScript compiledScript;

    @Override
    public SkillResult cast(IActiveCharacter character, PlayerSkillContext context) {
        Bindings bindings = new SimpleBindings();
        bindings.put("_caster", character);
        bindings.put("_context", context);
        try {
            //todo ScriptObjectMirror ?
            SkillResult result = (SkillResult) compiledScript.eval(bindings);
            return result == null ? SkillResult.OK : result;
        } catch (ScriptException s) {
            Log.error("Could not execute JS skill script ", s);
            return SkillResult.OK; //just apply cooldowns anyway
        }
    }

    @Override
    public void setScript(CompiledScript compile) {
        this.compiledScript = compile;
    }

    @Override
    public void setExecutor(ScriptExecutorSkill ses) {
        this.executor = ses;
    }

    @Override
    public ScriptSkillModel getModel() {
        return model;
    }

    public void setModel(ScriptSkillModel model) {
        this.model = model;
        setDamageType(model.getDamageType());
        setCatalogId(model.getId());
        List<String> configTypes = model.getSkillTypes();

        if (configTypes != null) {
            for (String configType : configTypes) {
                Optional<ISkillType> skillType = Rpg.get().getSkillService().getSkillType(configType);
                if (skillType.isPresent()) {
                    addSkillType(skillType.get());
                } else {
                    Log.warn("Unknown skill type " + configType);
                }
            }
        }
    }

    @Override
    public String getTemplateName() {
        return "templates/active.js";
    }
}
