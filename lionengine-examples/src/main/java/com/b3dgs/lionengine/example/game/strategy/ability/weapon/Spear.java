/*
 * Copyright (C) 2013-2014 Byron 3D Games Studio (www.b3dgs.com) Pierre-Alexandre (contact@b3dgs.com)
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package com.b3dgs.lionengine.example.game.strategy.ability.weapon;

import com.b3dgs.lionengine.core.Media;
import com.b3dgs.lionengine.example.game.strategy.ability.entity.Entity;
import com.b3dgs.lionengine.example.game.strategy.ability.launcher.FactoryLauncher;
import com.b3dgs.lionengine.example.game.strategy.ability.launcher.LauncherProjectile;
import com.b3dgs.lionengine.example.game.strategy.ability.launcher.SpearLauncher;
import com.b3dgs.lionengine.game.ContextGame;
import com.b3dgs.lionengine.game.SetupGame;

/**
 * Spear weapon implementation.
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 */
public final class Spear
        extends Weapon
{
    /** Class media. */
    public static final Media MEDIA = Weapon.getConfig(Spear.class);

    /** Launcher instance. */
    private LauncherProjectile launcher;

    /**
     * Constructor.
     * 
     * @param setup The setup reference.
     */
    public Spear(SetupGame setup)
    {
        super(setup);
    }

    /*
     * Weapon
     */

    @Override
    public void prepare(ContextGame context)
    {
        super.prepare(context);
        launcher = context.getService(FactoryLauncher.class).create(SpearLauncher.MEDIA);
        launcher.setOwner(this);
        launcher.setCanHitTargetOnly(true);
    }

    @Override
    public void notifyAttackEnded(int damages, Entity target)
    {
        super.notifyAttackEnded(damages, target);
        launcher.launch(target);
    }
}
