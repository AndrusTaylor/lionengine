/*
 * Copyright (C) 2013-2017 Byron 3D Games Studio (www.b3dgs.com) Pierre-Alexandre (contact@b3dgs.com)
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
package com.b3dgs.lionengine.game.feature.tile.map.collision;

import java.util.Collection;

import com.b3dgs.lionengine.Updatable;
import com.b3dgs.lionengine.game.Feature;

/**
 * Represents something which can enter in collision with a {@link TileCollision}.
 */
public interface TileCollidable extends Feature, Updatable
{
    /**
     * Add a tile collision listener.
     * 
     * @param listener The tile collision listener to add.
     */
    void addListener(TileCollidableListener listener);

    /**
     * Remove a tile collision listener.
     * 
     * @param listener The tile collision listener to remove.
     */
    void removeListener(TileCollidableListener listener);

    /**
     * Set the collision tile enabled flag.
     * 
     * @param enabled <code>true</code> to enable collision checking, <code>false</code> else.
     */
    void setEnabled(boolean enabled);

    /**
     * Get the collision tile category used.
     * 
     * @return The category used.
     */
    Collection<CollisionCategory> getCategories();
}
