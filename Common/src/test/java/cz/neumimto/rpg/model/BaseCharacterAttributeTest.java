package cz.neumimto.rpg.model;

import cz.neumimto.rpg.common.model.BaseCharacterAttribute;
import cz.neumimto.rpg.common.model.CharacterBase;

/**
 * Created by NeumimTo on 8.10.2016.
 */
public class BaseCharacterAttributeTest extends TimestampEntityTest implements BaseCharacterAttribute {

    private Long id;
    private CharacterBase characterBase;
    private String name;
    private int level;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public CharacterBase getCharacterBase() {
        return characterBase;
    }

    @Override
    public void setCharacterBase(CharacterBase characterBase) {
        this.characterBase = characterBase;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BaseCharacterAttributeTest that = (BaseCharacterAttributeTest) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 1236411;
    }
}
