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
package com.b3dgs.lionengine.game.feature.tile.map.pathfinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.b3dgs.lionengine.Nameable;

/**
 * Represents a pathfinding category, with its group list.
 */
public class PathCategory implements Nameable
{
    /** Category name. */
    private final String name;
    /** Associated groups. */
    private final Collection<String> groups;

    /**
     * Create the category.
     * 
     * @param name The category name.
     * @param groups The associated groups.
     */
    public PathCategory(String name, Collection<String> groups)
    {
        this.name = name;
        this.groups = new ArrayList<>(groups);
    }

    /**
     * Get the associated groups name as read only.
     * 
     * @return The groups name.
     */
    public Collection<String> getGroups()
    {
        return Collections.unmodifiableCollection(groups);
    }

    /*
     * Nameable
     */

    @Override
    public String getName()
    {
        return name;
    }
}
