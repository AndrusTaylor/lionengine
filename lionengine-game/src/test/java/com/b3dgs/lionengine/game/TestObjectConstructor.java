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
package com.b3dgs.lionengine.game;

import com.b3dgs.lionengine.core.Core;
import com.b3dgs.lionengine.core.Graphic;
import com.b3dgs.lionengine.core.Media;

/**
 * Object game test.
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 */
public final class TestObjectConstructor
        extends ObjectGame
{
    /** Media class. */
    public static final Media MEDIA = Core.MEDIA.create("src", "test", "resources", "TestObjectConstructor.xml");

    /**
     * Constructor.
     * 
     * @param setup The setup reference.
     */
    public TestObjectConstructor(SetupGame setup)
    {
        super(null);
    }

    /*
     * ObjectGame
     */

    @Override
    public void prepare(ContextGame context)
    {
        // Prepare
    }

    @Override
    public void update(double extrp)
    {
        // Update
    }

    @Override
    public void render(Graphic g, CameraGame camera)
    {
        // Render
    }
}
