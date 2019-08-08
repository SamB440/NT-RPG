package cz.neumimto.rpg.sponge.permission;

import cz.neumimto.rpg.api.entity.players.classes.PlayerClassData;
import cz.neumimto.rpg.api.entity.players.classes.PlayerClassPermission;
import cz.neumimto.rpg.api.permissions.PermissionService;
import cz.neumimto.rpg.sponge.entities.players.ISpongeCharacter;
import org.spongepowered.api.service.permission.SubjectData;
import org.spongepowered.api.util.Tristate;

import java.util.Collection;
import java.util.Set;

public class SpongePermissionService implements PermissionService<ISpongeCharacter> {

    @Override
    public boolean hasPermission(ISpongeCharacter character, String value) {
        return character.getPlayer().hasPermission(value);
    }

    @Override
    public void removePermissions(ISpongeCharacter character, Collection<String> perms) {
        SubjectData transientSubjectData = character.getPlayer().getTransientSubjectData();
        for (String perm : perms) {
            transientSubjectData.setPermission(SubjectData.GLOBAL_CONTEXT, perm, Tristate.UNDEFINED);
        }
    }

    @Override
    public void addPermissions(ISpongeCharacter character, Collection<String> perms) {
        SubjectData transientSubjectData = character.getPlayer().getTransientSubjectData();
        for (String perm : perms) {
            transientSubjectData.setPermission(SubjectData.GLOBAL_CONTEXT, perm, Tristate.TRUE);
        }
    }

    @Override
    public void addAllPermissions(ISpongeCharacter character, PlayerClassData classDefinition) {
        for (PlayerClassPermission playerClassPermission : classDefinition.getClassDefinition().getPermissions()) {
            if (playerClassPermission.getLevel() <= classDefinition.getLevel()) {
                addPermissions(character, playerClassPermission.getPermissions());
            }
        }
    }

    @Override
    public void addPermissions(ISpongeCharacter character, PlayerClassData classDefinition) {
        for (PlayerClassPermission playerClassPermission : classDefinition.getClassDefinition().getPermissions()) {
            if (playerClassPermission.getLevel() == classDefinition.getLevel()) {
                addPermissions(character, playerClassPermission.getPermissions());
            }
        }
    }
}