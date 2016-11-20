/*
 * Copyright (C) 2013-2016 Byron 3D Games Studio (www.b3dgs.com) Pierre-Alexandre (contact@b3dgs.com)
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
package com.b3dgs.lionengine.game.collision.object;

import java.util.Collection;
import java.util.List;

import com.b3dgs.lionengine.Origin;
import com.b3dgs.lionengine.Updatable;
import com.b3dgs.lionengine.game.feature.Feature;
import com.b3dgs.lionengine.geom.Rectangle;
import com.b3dgs.lionengine.graphic.Renderable;

/**
 * Represents something which can enter in collision with another.
 */
public interface Collidable extends Feature, Updatable, Renderable, CollidableListener
{
    /**
     * Add a collision listener.
     * 
     * @param listener The listener reference.
     */
    void addListener(CollidableListener listener);

    /**
     * Add a collision to use.
     * 
     * @param collision The collision to add.
     */
    void addCollision(Collision collision);

    /**
     * Add a collidable to ignore list.
     * 
     * @param collidable The collidable to ignore on {@link #collide(Collidable)}.
     */
    void addIgnore(Collidable collidable);

    /**
     * Check if the collidable entered in collision with another one.
     * 
     * @param collidable The collidable reference.
     * @return The collision found if collide, <code>null</code> if none.
     */
    Collision collide(Collidable collidable);

    /**
     * Set the origin to use.
     * 
     * @param origin The origin to use.
     */
    void setOrigin(Origin origin);

    /**
     * Set the collision enabled flag.
     * 
     * @param enabled <code>true</code> to enable collision checking, <code>false</code> else.
     */
    void setEnabled(boolean enabled);

    /**
     * Set the collision visibility.
     * 
     * @param visible <code>true</code> if visible, <code>false</code> else.
     */
    void setCollisionVisibility(boolean visible);

    /**
     * Get the declared collisions.
     * 
     * @return The declared collisions.
     */
    Collection<Collision> getCollisions();

    /**
     * Get the collisions bounds.
     * 
     * @return The collisions bounds.
     */
    List<Rectangle> getCollisionBounds();
}
