package cz.neumimto.rpg.common.permissions;

import cz.neumimto.rpg.common.entity.players.IActiveCharacter;
import cz.neumimto.rpg.common.entity.players.classes.PlayerClassData;
import cz.neumimto.rpg.common.entity.players.classes.PlayerClassPermission;

import java.util.Collection;

public interface PermissionService<T extends IActiveCharacter> {

    boolean hasPermission(T character, String value);

    void removePermissions(T character, Collection<String> perms);

    void addPermissions(T character, Collection<String> perms);

    default void addAllPermissions(T character, PlayerClassData classDefinition) {
        for (PlayerClassPermission playerClassPermission : classDefinition.getClassDefinition().getPermissions()) {
            if (playerClassPermission.getLevel() <= classDefinition.getLevel()) {
                addPermissions(character, playerClassPermission.getPermissions());
            }
        }
    }


    default void addPermissions(T character, PlayerClassData classDefinition) {
        for (PlayerClassPermission playerClassPermission : classDefinition.getClassDefinition().getPermissions()) {
            if (playerClassPermission.getLevel() == classDefinition.getLevel()) {
                addPermissions(character, playerClassPermission.getPermissions());
            }
        }
    }

    default void removePermissions(T character, PlayerClassData classDefinition) {
        for (PlayerClassPermission playerClassPermission : classDefinition.getClassDefinition().getPermissions()) {
            if (playerClassPermission.getLevel() <= classDefinition.getLevel()) {
                removePermissions(character, playerClassPermission.getPermissions());
            }
        }
    }
}
