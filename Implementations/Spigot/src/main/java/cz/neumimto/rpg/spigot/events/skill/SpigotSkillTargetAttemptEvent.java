/*  Copyright (c) 2015, NeumimTo https://github.com/NeumimTo
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package cz.neumimto.rpg.spigot.events.skill;

import cz.neumimto.rpg.common.entity.IEntity;
import cz.neumimto.rpg.common.events.skill.SkillTargetAttemptEvent;
import cz.neumimto.rpg.spigot.entities.ISpigotEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class SpigotSkillTargetAttemptEvent extends SpigotAbstractSkillEvent implements SkillTargetAttemptEvent, Cancellable {

    private IEntity target;
    private IEntity caster;
    private boolean cancelled;

    @Override
    public IEntity getCaster() {
        return caster;
    }

    @Override
    public void setCaster(IEntity caster) {
        this.caster = caster;
    }

    @Override
    public IEntity getTarget() {
        return target;
    }

    @Override
    public void setTarget(IEntity target) {
        this.target = target;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean state) {
        cancelled = state;
    }

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
}
