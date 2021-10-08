package cz.neumimto.rpg.common.scripting;

import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.polyglot.PolyglotImpl;
import cz.neumimto.rpg.common.entity.IEntity;
import cz.neumimto.rpg.common.entity.players.IActiveCharacter;
import cz.neumimto.rpg.common.logging.Log;
import cz.neumimto.rpg.common.skills.PlayerSkillContext;
import cz.neumimto.rpg.common.skills.SkillResult;
import cz.neumimto.rpg.common.utils.Java12FieldUtils;
import org.graalvm.polyglot.*;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;

public class GraalVmScriptEngine extends AbstractRpgScriptEngine {

    private Engine engine;

    //not thread sync
    private Context context;

    private Value bindings;

    //I could use even tsc
    @Override
    public void prepareEngine() {
        Iterator<AbstractPolyglotImpl> iterator = ServiceLoader.load(AbstractPolyglotImpl.class).iterator();
        if (!iterator.hasNext()) {
            try {
                Constructor<PolyglotImpl> constructor = PolyglotImpl.class.getConstructor();
                constructor.setAccessible(true);
                PolyglotImpl polyglot = constructor.newInstance();
                Class<?> aClass = Class.forName("org.graalvm.polyglot.Engine$APIAccessImpl");
                Constructor<?> constructor1 = aClass.getDeclaredConstructor();
                constructor1.setAccessible(true);
                Object o = constructor1.newInstance();
                polyglot.setConstructors((AbstractPolyglotImpl.APIAccess) o);
                Map<String, String> options = new HashMap<>();
                options.put("js.nashorn-compat", "true");
                try {
                    Field creating_child_realm = JSRealm.class.getDeclaredField("CREATING_CHILD_REALM");
                    creating_child_realm.setAccessible(true);
                    Java12FieldUtils.removeFinalMod(creating_child_realm);
                    creating_child_realm.set(null, new ThreadLocal<>());
                    new JSRealm(null, null);
                } catch (Throwable t) {
                    t.printStackTrace();
                }

                engine = polyglot.buildEngine(null, null, null,
                        options, true, true, false, null, null, HostAccess.ALL);

            } catch (Throwable t) {
                t.printStackTrace();
                return;
            }
        } else {
            engine = Engine.create();
        }
        Context.Builder contextBuilder = Context.newBuilder()
                .allowExperimentalOptions(true)
                .allowHostClassLookup(s -> true)
                .option("js.nashorn-compat", "true")
                .allowHostAccess(HostAccess.ALL)
                .engine(engine);
        context = contextBuilder.build();
        Path path = mergeScriptFiles();
        prepareBindings((s, o) -> context.getBindings("js").putMember(s, o));
        try {
            context.eval(Source.newBuilder("js", path.toFile()).build());
            bindings = context.getBindings("js");

            ScriptLib lib = getLib();

            Map<String, Map> skillHandlers = (Map<String, Map>) ((Object) lib.getSkillHandlers());
            if (skillHandlers != null) {
                for (Map.Entry<String, Map> e : skillHandlers.entrySet()) {
                    String key = e.getKey();
                    Map<String, Function<Object[], Object>> mapAndFnc = e.getValue();

                    SkillScriptHandlers handler = null;
                    if (mapAndFnc.containsKey("onCast")) {
                        handler = new GRLVM_Active(mapAndFnc.get("onCast"));
                    } else if (mapAndFnc.containsKey("castOnTarget")) {
                        handler = new GRLVM_Targetted(mapAndFnc.get("castOnTarget"));
                    } else if (mapAndFnc.containsKey("init")) {
                        handler = new GRLVM_Passive(mapAndFnc.get("init"));
                    } else {
                        Log.warn("unknown object " + key);
                        continue;
                    }

                    skillService.registerSkillHandler(key, handler);

                    int i = 0;
                }
            }

        } catch (IOException e) {
            Log.error("Could not read script file " + path, e);
        }
    }

    @Override
    public Object fn(String functionName, Object... args) {
        return bindings.execute(functionName).execute(args);
    }

    @Override
    public Object fn(String functionName) {
        return bindings.execute(functionName).execute();
    }

    @Override
    public <T> T eval(String expr, Class<T> t) {
        return context.eval(Source.create("js", expr)).as(t);
    }

    @Override
    public <T> T extract(Object o, String key, T def) {
        return null;
    }

    public Engine getEngine() {
        return engine;
    }


    protected ScriptLib getLib() {
        return context.getBindings("js").getMember("lib").as(ScriptLib.class);
    }


    static class GRLVM_Active implements SkillScriptHandlers.Active {
        private final Function<Object[], Object> fnc;

        GRLVM_Active(Function<Object[], Object> fnc) {
            this.fnc = fnc;
        }

        public SkillResult onCast(IActiveCharacter caster, PlayerSkillContext context) {
            return (SkillResult) fnc.apply(new Object[]{caster, context});
        }
    }

    static class GRLVM_Targetted implements SkillScriptHandlers.Targetted {
        private final Function<Object[], Object> fnc;

        GRLVM_Targetted(Function<Object[], Object> fnc) {
            this.fnc = fnc;
        }

        public SkillResult castOnTarget(IActiveCharacter caster, PlayerSkillContext context, IEntity target) {
            return (SkillResult) fnc.apply(new Object[]{caster, context, target});
        }
    }

    static class GRLVM_Passive implements SkillScriptHandlers.Passive {
        private final Function<Object[], Object> fnc;

        GRLVM_Passive(Function<Object[], Object> fnc) {
            this.fnc = fnc;
        }

        public SkillResult init(IActiveCharacter caster, PlayerSkillContext context) {
            return (SkillResult) fnc.apply(new Object[]{caster, context});
        }
    }

    public interface ScriptLib {
        Map<String, Value> getSkillHandlers();

        List<Value> getGlobalEffects();

        List<Value> getEventListeners();
    }

}
