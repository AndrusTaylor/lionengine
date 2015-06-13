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
package com.b3dgs.lionengine.drawable;

import com.b3dgs.lionengine.LionEngineException;
import com.b3dgs.lionengine.core.Graphic;
import com.b3dgs.lionengine.core.Media;

/**
 * ParallaxedSprites are used for parallax effect (2.5D perspective). It cuts a sprite surface into an array of lines.
 * They are scaled using a trapeze representation, for the perspective effect:
 * 
 * <pre>
 *   &#47;======\
 *  &#47;========\
 * &#47;==========\
 * </pre>
 * <p>
 * Usage should be as following:
 * <ul>
 * <li>Create the instance with {@link Drawable#loadSpriteParallaxed(Media, int, int, int)}</li>
 * <li>Scale if necessary with {@link #stretch(int, int)}</li>
 * <li>Call {@link #load(boolean)}</li>
 * <li>Then other functions can be used.</li>
 * </ul>
 * </p>
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 */
public interface SpriteParallaxed
{
    /**
     * Load surface and prepare it to be displayed. This function must be called if the surface is loaded from a file,
     * else the surface will never be prepared.
     * 
     * @param alpha Set <code>true</code> to enable alpha, <code>false</code> else.
     * @throws LionEngineException If an error occurred when reading the image.
     */
    void load(boolean alpha) throws LionEngineException;

    /**
     * Works as scale, but using different width and height factor. Using different values, the ratio won't be kept, and
     * the sprite will be different !
     * 
     * @param percentWidth The percent value for scaling width (> 0).
     * @param percentHeight The percent value for scaling height (> 0).
     */
    void stretch(int percentWidth, int percentHeight);

    /**
     * Render a line of parallax to the specified coordinates.
     * 
     * @param g The graphic output.
     * @param line The line to render (>= 0).
     * @param x The abscissa.
     * @param y The ordinate.
     */
    void render(Graphic g, int line, int x, int y);

    /**
     * Get a parallax line width.
     * 
     * @param line The desired line (>= 0).
     * @return The line width.
     */
    int getLineWidth(int line);

    /**
     * Get the element width.
     * 
     * @return The element width.
     */
    int getWidth();

    /**
     * Get the element height.
     * 
     * @return The element height.
     */
    int getHeight();
}
