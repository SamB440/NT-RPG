package cz.neumimto.rpg.common.events.damage;

import cz.neumimto.rpg.common.events.Cancellable;
import cz.neumimto.rpg.common.events.entity.TargetIEntityEvent;

public interface DamageIEntityLateEvent extends TargetIEntityEvent, Cancellable {

    double getDamage();

    void setDamage(double damage);

}
