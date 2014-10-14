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
package com.b3dgs.lionengine.editor.properties;

import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * Describe the properties model.
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 */
public enum PropertiesModel
{
    /** Properties explorer model. */
    INSTANCE;

    /** Properties tree. */
    private Tree properties;

    /**
     * Set the properties tree.
     * 
     * @param properties The properties tree.
     */
    public void setTree(Tree properties)
    {
        this.properties = properties;
    }

    /**
     * Get the properties root folder.
     * 
     * @return The properties root folder.
     */
    public Tree getTree()
    {
        return properties;
    }

    /**
     * Check if properties are empty.
     * 
     * @return <code>true</code> if empty, <code>false</code> else.
     */
    public boolean isEmpty()
    {
        return properties.getItems().length == 0;
    }

    /**
     * Check if property node exists.
     * 
     * @param property The property to check.
     * @return <code>true</code> if property defined, <code>false</code> else.
     */
    public boolean hasProperty(String property)
    {
        for (final TreeItem item : properties.getItems())
        {
            if (property.equals(item.getData()))
            {
                return true;
            }
        }
        return false;
    }
}