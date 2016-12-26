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
package com.b3dgs.lionengine.core;

import java.awt.image.BufferedImage;

import org.junit.Assert;
import org.junit.Test;

import com.b3dgs.lionengine.graphic.ColorRgba;
import com.b3dgs.lionengine.graphic.ImageBuffer;
import com.b3dgs.lionengine.graphic.Transparency;

/**
 * Test the image buffer class.
 */
public class ImageBufferAwtTest
{
    /**
     * Test the image.
     */
    @Test
    public void testImage()
    {
        final BufferedImage buffer = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        final ImageBuffer image = ToolsAwt.getImageBuffer(buffer);

        Assert.assertNotNull(image.createGraphic());
        image.prepare();
        Assert.assertEquals(buffer, image.getSurface());

        Assert.assertEquals(ColorRgba.BLACK.getRgba(), image.getRgb(0, 0));
        Assert.assertNotNull(image.getRgb(0, 0, 1, 1, new int[1], 0, 0));
        Assert.assertEquals(Transparency.OPAQUE, image.getTransparency());
        Assert.assertEquals(buffer.getWidth(), image.getWidth());
        Assert.assertEquals(buffer.getHeight(), image.getHeight());

        image.setRgb(0, 0, ColorRgba.BLUE.getRgba());
        Assert.assertEquals(ColorRgba.BLUE.getRgba(), image.getRgb(0, 0));
        image.setRgb(0, 0, 0, 0, new int[1], 0, 0);

        image.dispose();
    }
}
