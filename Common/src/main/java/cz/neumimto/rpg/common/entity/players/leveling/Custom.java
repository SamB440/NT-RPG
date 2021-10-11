package cz.neumimto.rpg.common.entity.players.leveling;

import com.electronwill.nightconfig.core.conversion.Path;

import java.util.List;

/**
 * Created by NeumimTo on 26.1.2019.
 */
public class Custom extends AbstractLevelProgression {

    @Path("Levels")
    private List<Integer> lvlMgrs;

    //because configurate and lightbend/config are extremly useless libraries once you stopE doing stupid hello fucking world applications.
    @Override
    public double[] initCurve() {
        double[] doubles = lvlMgrs.stream().map(a -> a.doubleValue()).mapToDouble(Double::doubleValue).toArray();
        lvlMgrs = null;
        return doubles;
    }

    @Override
    public int getMaxLevel() {
        return getLevelMargins().length;
    }
}
