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
package com.b3dgs.lionengine.graphic;

import com.b3dgs.lionengine.LionEngineException;
import com.b3dgs.lionengine.Localizable;
import com.b3dgs.lionengine.Origin;
import com.b3dgs.lionengine.Resource;
import com.b3dgs.lionengine.Shape;
import com.b3dgs.lionengine.Viewer;

/**
 * It allows images loading and rendering. Images can't be resized and can't use any filters.
 * <p>
 * Example:
 * </p>
 * 
 * <pre>
 * // Load
 * final Image image = Drawable.loadImage(Medias.create(&quot;image.png&quot;));
 * image.setPosition(10, 50);
 * 
 * // Render
 * image.render(g);
 * </pre>
 */
public interface Image extends Resource, Shape, Renderable
{
    /**
     * Load surface. This function must be called if the surface is loaded from a file, else the surface will never be
     * prepared. It will only load data from source to memory.
     * <p>
     * Must be called only one time. {@link #prepare()} must be then called.
     * </p>
     * <p>
     * Images loaded with a reference of an existing {@link ImageBuffer}.
     * </p>
     * 
     * @throws LionEngineException If an error occurred when reading the image or already loaded.
     */
    @Override
    void load();

    /**
     * Prepare loaded surface to be displayed. Must be called only one time, after {@link #load()}. It will
     * prepare memory data to be displayed.
     * 
     * @throws LionEngineException If error on preparing.
     */
    void prepare();

    /**
     * Set the origin location type, related to surface area. The type will affect the defined location and the
     * rendering point.
     * 
     * @param origin The origin type
     */
    void setOrigin(Origin origin);

    /**
     * Set the location on screen.
     * 
     * @param x The horizontal location.
     * @param y The vertical location.
     */
    void setLocation(double x, double y);

    /**
     * Set the location on screen from a viewer viewpoint.
     * 
     * @param viewer The viewer reference.
     * @param localizable The localizable reference.
     */
    void setLocation(Viewer viewer, Localizable localizable);

    /**
     * Get the surface which represents the image.
     * 
     * @return The image descriptor reference (<code>null</code> if not loaded).
     */
    ImageBuffer getSurface();
}
