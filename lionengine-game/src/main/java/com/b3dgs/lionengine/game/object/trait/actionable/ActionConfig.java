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
package com.b3dgs.lionengine.game.object.trait.actionable;

import com.b3dgs.lionengine.Check;
import com.b3dgs.lionengine.LionEngineException;
import com.b3dgs.lionengine.game.Configurer;
import com.b3dgs.lionengine.stream.Xml;
import com.b3dgs.lionengine.stream.XmlNode;

/**
 * Represents the action data from a configurer.
 * 
 * @see com.b3dgs.lionengine.game.object.trait.actionable.Actionable
 */
public final class ActionConfig
{
    /** Action node name. */
    public static final String NODE_ACTION = Configurer.PREFIX + "action";
    /** Action attribute name. */
    public static final String ATT_NAME = "name";
    /** Action attribute description. */
    public static final String ATT_DESCRIPTION = "description";
    /** Action attribute x. */
    public static final String ATT_X = "x";
    /** Action attribute y. */
    public static final String ATT_Y = "y";
    /** Action attribute width. */
    public static final String ATT_WIDTH = "width";
    /** Action attribute height. */
    public static final String ATT_HEIGHT = "height";

    /**
     * Import the action data from node.
     *
     * @param root The root node reference.
     * @return The action data.
     * @throws LionEngineException If unable to read node.
     */
    public static ActionConfig imports(XmlNode root)
    {
        final XmlNode nodeAction = root.getChild(NODE_ACTION);
        final String name = nodeAction.readString(ATT_NAME);
        final String description = nodeAction.readString(ATT_DESCRIPTION);
        final int x = nodeAction.readInteger(ATT_X);
        final int y = nodeAction.readInteger(ATT_Y);
        final int width = nodeAction.readInteger(ATT_WIDTH);
        final int height = nodeAction.readInteger(ATT_HEIGHT);

        return new ActionConfig(name, description, x, y, width, height);
    }

    /**
     * Export the action node from data.
     *
     * @param config The config reference.
     * @return The action node.
     * @throws LionEngineException If unable to save.
     */
    public static XmlNode exports(ActionConfig config)
    {
        final XmlNode nodeAction = Xml.create(NODE_ACTION);
        nodeAction.writeString(ATT_NAME, config.getName());
        nodeAction.writeString(ATT_DESCRIPTION, config.getDescription());
        nodeAction.writeInteger(ATT_X, config.getX());
        nodeAction.writeInteger(ATT_Y, config.getY());
        nodeAction.writeInteger(ATT_WIDTH, config.getWidth());
        nodeAction.writeInteger(ATT_HEIGHT, config.getHeight());

        return nodeAction;
    }

    /** Action name. */
    private final String name;
    /** Action description. */
    private final String description;
    /** Horizontal location on screen. */
    private final int x;
    /** Vertical location on screen. */
    private final int y;
    /** Width on screen. */
    private final int width;
    /** Height on screen. */
    private final int height;

    /**
     * Create action from configuration media.
     *
     * @param name The action name.
     * @param description The action description.
     * @param x The horizontal location on screen.
     * @param y The vertical location on screen.
     * @param width The button width.
     * @param height The button height.
     * @throws LionEngineException If <code>null</code> argument.
     */
    public ActionConfig(String name, String description, int x, int y, int width, int height)
    {
        Check.notNull(name);
        Check.notNull(description);

        this.name = name;
        this.description = description;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Get the action name.
     *
     * @return The action name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Get the action description.
     *
     * @return The action description.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Get the button horizontal location.
     *
     * @return The button horizontal location.
     */
    public int getX()
    {
        return x;
    }

    /**
     * Get the button vertical location.
     *
     * @return The button vertical location.
     */
    public int getY()
    {
        return y;
    }

    /**
     * Get the button width.
     *
     * @return The button width.
     */
    public int getWidth()
    {
        return width;
    }

    /**
     * Get the button height.
     *
     * @return The button height.
     */
    public int getHeight()
    {
        return height;
    }

    /*
     * Object
     */

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + description.hashCode();
        result = prime * result + height;
        result = prime * result + name.hashCode();
        result = prime * result + width;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null || !(obj instanceof ActionConfig))
        {
            return false;
        }
        final ActionConfig other = (ActionConfig) obj;
        return other.getDescription().equals(getDescription())
               && other.getName().equals(getName())
               && other.getX() == getX()
               && other.getY() == getY()
               && other.getWidth() == getWidth()
               && other.getHeight() == getHeight();
    }
}
