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
package com.b3dgs.lionengine.core.filter;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.b3dgs.lionengine.Media;
import com.b3dgs.lionengine.core.Medias;
import com.b3dgs.lionengine.graphic.FactoryGraphicMock;
import com.b3dgs.lionengine.graphic.Graphics;
import com.b3dgs.lionengine.graphic.ImageBuffer;

/**
 * Test the Blur filter.
 */
public class FilterBlurTest
{
    /** Image media. */
    private static Media media;

    /**
     * Prepare test.
     */
    @BeforeClass
    public static void setUp()
    {
        Medias.setLoadFromJar(FilterBlurTest.class);
        Graphics.setFactoryGraphic(new FactoryGraphicMock());

        media = Medias.create("image.png");
    }

    /**
     * Clean up test.
     */
    @AfterClass
    public static void cleanUp()
    {
        Medias.setLoadFromJar(null);
        Graphics.setFactoryGraphic(null);
    }

    /**
     * Test the Hq2x filter
     */
    @Test
    public void testHq2x()
    {
        final ImageBuffer image = Graphics.getImageBuffer(media);
        int i = 0;
        for (int y = 0; y < image.getHeight(); y++)
        {
            for (int x = 0; x < image.getWidth(); x++)
            {
                i++;
                if (y < 10)
                {
                    image.setRgb(x, y, i % 2);
                }
                else if (y < 20)
                {
                    image.setRgb(x, y, i % 3);
                }
                else if (y < 32)
                {
                    image.setRgb(x, y, i % 5);
                }
            }
        }
        final FilterBlur blur = new FilterBlur();
        final ImageBuffer filtered = blur.filter(image);

        Assert.assertNotEquals(image, filtered);
        Assert.assertNotNull(blur.getTransform(1.0, 1.0));
        Assert.assertEquals(image.getWidth(), filtered.getWidth());
        Assert.assertEquals(image.getHeight(), filtered.getHeight());

        blur.setAlpha(false);
        Assert.assertNotNull(blur.filter(image));

        blur.setEdgeMode(FilterBlur.WRAP_EDGES);
        Assert.assertNotNull(blur.filter(image));

        blur.setRadius(1.0f);
        Assert.assertNotNull(blur.filter(image));
    }

    /**
     * Test the Hq2x filter with no pixel.
     */
    @Test
    public void testNoPixel()
    {
        final FilterBlur blur = new FilterBlur();
        final ImageBuffer image = Graphics.createImageBuffer(0, 0);
        final ImageBuffer filtered = blur.filter(image);

        Assert.assertNotNull(filtered);
        Assert.assertEquals(image.getWidth(), filtered.getWidth());
        Assert.assertEquals(image.getHeight(), filtered.getHeight());
    }

    /**
     * Test the Hq2x filter with single pixel.
     */
    @Test
    public void testSinglePixel()
    {
        final FilterBlur blur = new FilterBlur();
        final ImageBuffer image = Graphics.createImageBuffer(1, 1);
        final ImageBuffer filtered = blur.filter(image);

        Assert.assertEquals(image, filtered);
    }

    /**
     * Test the Hq2x filter with low width.
     */
    @Test
    public void testLowWidth()
    {
        final FilterBlur blur = new FilterBlur();
        final ImageBuffer image = Graphics.createImageBuffer(1, 3);
        final ImageBuffer filtered = blur.filter(image);

        Assert.assertEquals(image, filtered);
    }

    /**
     * Test the Hq2x filter with low height.
     */
    @Test
    public void testLowHeight()
    {
        final FilterBlur blur = new FilterBlur();
        final ImageBuffer image = Graphics.createImageBuffer(3, 1);
        final ImageBuffer filtered = blur.filter(image);

        Assert.assertEquals(image, filtered);
    }
}
