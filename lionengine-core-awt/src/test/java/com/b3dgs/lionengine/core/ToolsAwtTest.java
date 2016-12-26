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

import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.b3dgs.lionengine.LionEngineException;
import com.b3dgs.lionengine.Media;
import com.b3dgs.lionengine.graphic.ColorRgba;
import com.b3dgs.lionengine.util.UtilStream;
import com.b3dgs.lionengine.util.UtilTests;

/**
 * Test the tools class.
 */
public class ToolsAwtTest
{
    /**
     * Prepare test.
     */
    @BeforeClass
    public static void prepare()
    {
        Medias.setLoadFromJar(ToolsAwtTest.class);
    }

    /**
     * Clean up test.
     */
    @AfterClass
    public static void cleanUp()
    {
        Medias.setLoadFromJar(null);
    }

    /**
     * Test the constructor.
     * 
     * @throws Exception If error.
     */
    @Test(expected = LionEngineException.class)
    public void testConstructor() throws Exception
    {
        UtilTests.testPrivateConstructor(ToolsAwt.class);
    }

    /**
     * Test the utility.
     * 
     * @throws IOException If error.
     */
    @Test
    public void testUtility() throws IOException
    {
        final BufferedImage image = ToolsAwt.createImage(100, 100, Transparency.OPAQUE);
        Assert.assertNotNull(image);
        Assert.assertNotNull(ToolsAwt.getRasterBuffer(image, 1, 1, 1, 1, 1, 1, 1));
        Assert.assertNotNull(ToolsAwt.flipHorizontal(image));
        Assert.assertNotNull(ToolsAwt.flipVertical(image));
        Assert.assertNotNull(ToolsAwt.resize(image, 10, 10));
        Assert.assertNotNull(ToolsAwt.rotate(image, 90));
        Assert.assertNotNull(ToolsAwt.splitImage(image, 1, 1));
        Assert.assertNotNull(ToolsAwt.applyMask(image, ColorRgba.BLACK.getRgba()));
        Assert.assertNotNull(ToolsAwt.applyMask(image, ColorRgba.WHITE.getRgba()));

        final Media media = Medias.create("image.png");

        InputStream input = null;
        try
        {
            input = media.getInputStream();
            final BufferedImage buffer = ToolsAwt.getImage(input);
            Assert.assertNotNull(buffer);
            Assert.assertNotNull(ToolsAwt.getImageData(image));
            ToolsAwt.optimizeGraphicsQuality(buffer.createGraphics());
        }
        finally
        {
            UtilStream.close(input);
        }

        Assert.assertNotNull(ToolsAwt.createHiddenCursor());
    }

    /**
     * Test the copy.
     */
    @Test
    public void testCopy()
    {
        final BufferedImage image = ToolsAwt.createImage(100, 100, Transparency.TRANSLUCENT);
        final BufferedImage copy = ToolsAwt.copyImage(image, Transparency.OPAQUE);
        Assert.assertEquals(image.getWidth(), copy.getWidth());
    }

    /**
     * Test the save.
     * 
     * @throws IOException If error.
     */
    @Test
    public void testSave() throws IOException
    {
        final Media media = Medias.create("image.png");

        InputStream input = null;
        try
        {
            input = media.getInputStream();
            final BufferedImage image = ToolsAwt.getImage(input);
            Assert.assertNotNull(image);

            final Media save = Medias.create(File.createTempFile("temp", ".tmp").getPath());

            OutputStream output = null;
            try
            {
                output = save.getOutputStream();
                ToolsAwt.saveImage(image, output);
            }
            finally
            {
                UtilStream.close(output);
            }
            Assert.assertTrue(save.getFile().exists());
            Assert.assertTrue(save.getFile().delete());
            Assert.assertFalse(save.getFile().exists());
        }
        finally
        {
            UtilStream.close(input);
        }
    }

    /**
     * Test the get fail.
     * 
     * @throws IOException If error.
     */
    @Test(expected = LionEngineException.class)
    public void testGetFail() throws IOException
    {
        final Media media = Medias.create("image.xml");

        InputStream input = null;
        try
        {
            input = media.getInputStream();
            final BufferedImage image = ToolsAwt.getImage(input);
            Assert.assertNotNull(image);
        }
        finally
        {
            UtilStream.close(input);
        }
    }

    /**
     * Test the get fail IO.
     * 
     * @throws IOException If error.
     */
    @Test(expected = IOException.class)
    public void testGetIoFail() throws IOException
    {
        final Media media = Medias.create("raster.xml");

        InputStream input = null;
        try
        {
            input = media.getInputStream();
            final BufferedImage image = ToolsAwt.getImage(input);
            Assert.assertNotNull(image);
        }
        finally
        {
            UtilStream.close(input);
        }
    }
}
