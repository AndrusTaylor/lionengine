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
package com.b3dgs.lionengine.game;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.b3dgs.lionengine.Animation;
import com.b3dgs.lionengine.Constant;
import com.b3dgs.lionengine.LionEngineException;
import com.b3dgs.lionengine.io.Xml;

/**
 * Represents the animations data from a configurer node.
 * 
 * @see Animation
 */
public final class AnimationConfig
{
    /** Animation node name. */
    public static final String ANIMATION = Constant.XML_PREFIX + "animation";
    /** Animation attribute name. */
    public static final String ANIMATION_NAME = "name";
    /** Animation attribute start. */
    public static final String ANIMATION_START = "start";
    /** Animation attribute end. */
    public static final String ANIMATION_END = "end";
    /** Animation attribute speed. */
    public static final String ANIMATION_SPEED = "speed";
    /** Animation attribute reversed. */
    public static final String ANIMATION_REVERSED = "reversed";
    /** Animation attribute repeat. */
    public static final String ANIMATION_REPEAT = "repeat";

    /**
     * Create the animation data from configurer.
     * 
     * @param configurer The configurer reference.
     * @return The animations configuration instance.
     * @throws LionEngineException If unable to read data.
     */
    public static AnimationConfig imports(Configurer configurer)
    {
        return imports(configurer.getRoot());
    }

    /**
     * Create the animation data from configurer.
     * 
     * @param root The root reference.
     * @return The animations configuration instance.
     * @throws LionEngineException If unable to read data.
     */
    public static AnimationConfig imports(Xml root)
    {
        final Map<String, Animation> animations = new HashMap<String, Animation>(0);
        for (final Xml node : root.getChildren(ANIMATION))
        {
            final String anim = node.readString(ANIMATION_NAME);
            final Animation animation = createAnimation(node);
            animations.put(anim, animation);
        }
        return new AnimationConfig(animations);
    }

    /**
     * Create animation data from node.
     * Example:
     * 
     * <pre>
     * {@code<lionengine:animation name="walk" start="1" end="2" step="1" speed="0.25" reversed="false"
     * repeat="true"/>}
     * </pre>
     * 
     * @param node The animation node.
     * @return The animation instance.
     * @throws LionEngineException If error when reading animation data.
     */
    public static Animation createAnimation(Xml node)
    {
        final String name = node.readString(ANIMATION_NAME);
        final int start = node.readInteger(ANIMATION_START);
        final int end = node.readInteger(ANIMATION_END);
        final double speed = node.readDouble(ANIMATION_SPEED);
        final boolean reversed = node.readBoolean(ANIMATION_REVERSED);
        final boolean repeat = node.readBoolean(ANIMATION_REPEAT);

        return new Animation(name, start, end, speed, reversed, repeat);
    }

    /**
     * Create an XML node from an animation.
     * 
     * @param root The node root.
     * @param animation The animation reference.
     * @throws LionEngineException If error on writing.
     */
    public static void exports(Xml root, Animation animation)
    {
        final Xml node = root.createChild(ANIMATION);
        node.writeString(ANIMATION_NAME, animation.getName());
        node.writeInteger(ANIMATION_START, animation.getFirst());
        node.writeInteger(ANIMATION_END, animation.getLast());
        node.writeDouble(ANIMATION_SPEED, animation.getSpeed());
        node.writeBoolean(ANIMATION_REVERSED, animation.getReverse());
        node.writeBoolean(ANIMATION_REPEAT, animation.getRepeat());
    }

    /** Animations map. */
    private final Map<String, Animation> animations;

    /**
     * Disabled constructor.
     */
    private AnimationConfig()
    {
        throw new LionEngineException(LionEngineException.ERROR_PRIVATE_CONSTRUCTOR);
    }

    /**
     * Load animations from configuration media.
     * 
     * @param animations The animations mapping.
     */
    private AnimationConfig(Map<String, Animation> animations)
    {
        this.animations = animations;
    }

    /**
     * Clear the animations data.
     */
    public void clear()
    {
        animations.clear();
    }

    /**
     * Get an animation data from its name.
     * 
     * @param name The animation name.
     * @return The animation reference.
     * @throws LionEngineException If the animation with the specified name is not found.
     */
    public Animation getAnimation(String name)
    {
        final Animation animation = animations.get(name);
        if (animation == null)
        {
            throw new LionEngineException("Animation not found: ", name);
        }
        return animation;
    }

    /**
     * Check if animation exists.
     * 
     * @param name The animation name.
     * @return <code>true</code> if exists, <code>false</code> else.
     */
    public boolean hasAnimation(String name)
    {
        return animations.containsKey(name);
    }

    /**
     * Get all animations.
     * 
     * @return The animations list.
     */
    public Collection<Animation> getAnimations()
    {
        return animations.values();
    }
}
