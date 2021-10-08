package cz.neumimto.rpg.spigot.gui;

import cz.neumimto.rpg.common.gui.SkillTreeViewModel;
import cz.neumimto.rpg.common.skills.ISkill;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpigotSkillTreeViewModel extends SkillTreeViewModel {

    static {
        factory = SpigotSkillTreeViewModel::new;
    }

    private Map<String, List<String>> buttonCache;

    public SpigotSkillTreeViewModel() {
        this.buttonCache = new HashMap<>();
    }

    @Override
    public void reset() {
        buttonCache.clear();
    }

    public List<String> getFromCache(ISkill iSkill) {
        return buttonCache.get(iSkill.getId());
    }

    public void addToCache(ISkill iSkill, List<String> lore) {
        buttonCache.put(iSkill.getId(), lore);
    }

}
