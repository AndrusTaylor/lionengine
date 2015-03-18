/*
 * Copyright (C) 2013-2015 Byron 3D Games Studio (www.b3dgs.com) Pierre-Alexandre (contact@b3dgs.com)
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
package com.b3dgs.lionengine.game.trait.assignable;

import com.b3dgs.lionengine.LionEngineException;
import com.b3dgs.lionengine.UtilMath;
import com.b3dgs.lionengine.Viewer;
import com.b3dgs.lionengine.game.Cursor;
import com.b3dgs.lionengine.game.configurer.ConfigAction;
import com.b3dgs.lionengine.game.configurer.Configurer;
import com.b3dgs.lionengine.game.object.ObjectGame;
import com.b3dgs.lionengine.game.object.Services;
import com.b3dgs.lionengine.game.trait.TraitModel;

/**
 * Assignable implementation.
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 */
public class AssignableModel
        extends TraitModel
        implements Assignable
{
    /** Cursor reference. */
    private final Cursor cursor;
    /** Viewer reference. */
    private final Viewer viewer;
    /** Mouse click number to assign action. */
    private int clickAssign;
    /** Assign used. */
    private Assign assign;

    /**
     * Create an assignable model.
     * <p>
     * The {@link Configurer} must provide a valid configuration compatible with {@link ConfigAction}.
     * </p>
     * <p>
     * The {@link Services} must provide the following services:
     * </p>
     * <ul>
     * <li>{@link Cursor}</li>
     * <li>{@link Viewer}</li>
     * </ul>
     * 
     * @param owner The owner reference.
     * @param configurer The configurer reference.
     * @param services The services reference.
     * @throws LionEngineException If wrong configurer or missing {@link Services}.
     */
    public AssignableModel(ObjectGame owner, Configurer configurer, Services services) throws LionEngineException
    {
        super(owner);
        cursor = services.get(Cursor.class);
        viewer = services.get(Viewer.class);
    }

    /*
     * Assignable
     */

    @Override
    public void update(double extrp)
    {
        if (assign != null
                && UtilMath.isBetween(cursor.getScreenX(), viewer.getViewX(), viewer.getViewX() + viewer.getWidth())
                && UtilMath.isBetween(cursor.getScreenY(), viewer.getViewY(), viewer.getViewY() + viewer.getHeight())
                && cursor.hasClickedOnce(clickAssign))
        {
            assign.assign();
        }
    }

    @Override
    public void setAssign(Assign assign)
    {
        this.assign = assign;
    }

    @Override
    public void setClickAssign(int click)
    {
        clickAssign = click;
    }
}