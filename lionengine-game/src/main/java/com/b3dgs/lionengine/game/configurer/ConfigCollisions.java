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
package com.b3dgs.lionengine.game.configurer;

import java.util.HashMap;
import java.util.Map;

import com.b3dgs.lionengine.Check;
import com.b3dgs.lionengine.LionEngineException;
import com.b3dgs.lionengine.game.Collision;
import com.b3dgs.lionengine.stream.XmlNode;

/**
 * Represents the collisions data from a configurer node.
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 */
public class ConfigCollisions
{
    /** Collision node name. */
    public static final String COLLISION = Configurer.PREFIX + "collision";
    /** Collision attribute name. */
    public static final String COLLISION_NAME = "name";
    /** Collision attribute offset x. */
    public static final String COLLISION_OFFSETX = "offsetX";
    /** Collision attribute offset y. */
    public static final String COLLISION_OFFSETY = "offsetY";
    /** Collision attribute width. */
    public static final String COLLISION_WIDTH = "width";
    /** Collision attribute height. */
    public static final String COLLISION_HEIGHT = "height";
    /** Collision attribute mirror. */
    public static final String COLLISION_MIRROR = "mirror";

    /**
     * Create the size node.
     * 
     * @param configurer The configurer reference.
     * @return The config collisions instance.
     * @throws LionEngineException If unable to read node or not a valid integer.
     */
    public static ConfigCollisions create(Configurer configurer)
    {
        final Map<String, Collision> collisions = new HashMap<>(0);
        for (final XmlNode node : configurer.getRoot().getChildren(ConfigCollisions.COLLISION))
        {
            final String coll = node.readString(ConfigCollisions.COLLISION_NAME);
            final Collision collision = ConfigCollisions.createCollision(node);
            collisions.put(coll, collision);
        }
        return new ConfigCollisions(collisions);
    }

    /**
     * Create an collision from its node.
     * 
     * @param node The collision node.
     * @return The collision instance.
     * @throws LionEngineException If error when reading collision data.
     */
    public static Collision createCollision(XmlNode node) throws LionEngineException
    {
        final int offsetX = node.readInteger(ConfigCollisions.COLLISION_OFFSETX);
        final int offsetY = node.readInteger(ConfigCollisions.COLLISION_OFFSETY);
        final int width = node.readInteger(ConfigCollisions.COLLISION_WIDTH);
        final int height = node.readInteger(ConfigCollisions.COLLISION_HEIGHT);
        final boolean mirror = node.readBoolean(ConfigCollisions.COLLISION_MIRROR);
        return new Collision(offsetX, offsetY, width, height, mirror);
    }

    /** Collisions map. */
    private final Map<String, Collision> collisions;

    /**
     * Load collisions from configuration media.
     * 
     * @param collisions The collisions mapping.
     * @throws LionEngineException If error when opening the media.
     */
    private ConfigCollisions(Map<String, Collision> collisions) throws LionEngineException
    {
        this.collisions = collisions;
    }

    /**
     * Clear the collisions data.
     */
    public void clear()
    {
        collisions.clear();
    }

    /**
     * Get a collision data from its name.
     * 
     * @param name The collision name.
     * @return The collision reference.
     * @throws LionEngineException If the collision with the specified name is not found.
     */
    public Collision getCollision(String name) throws LionEngineException
    {
        final Collision collision = collisions.get(name);
        Check.notNull(collision);
        return collision;
    }

    /**
     * Get all collisions.
     * 
     * @return The collisions list.
     */
    public Map<String, Collision> getCollisions()
    {
        return collisions;
    }
}