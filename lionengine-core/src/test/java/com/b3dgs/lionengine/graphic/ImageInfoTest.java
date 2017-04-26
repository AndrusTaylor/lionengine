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
package com.b3dgs.lionengine.graphic;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.b3dgs.lionengine.Constant;
import com.b3dgs.lionengine.LionEngineException;
import com.b3dgs.lionengine.Media;
import com.b3dgs.lionengine.Verbose;
import com.b3dgs.lionengine.core.Medias;
import com.b3dgs.lionengine.util.UtilReflection;
import com.b3dgs.lionengine.util.UtilTests;

/**
 * Test the image info class.
 */
public class ImageInfoTest
{
    /**
     * Prepare test.
     */
    @BeforeClass
    public static void setUp()
    {
        Medias.setLoadFromJar(ImageInfoTest.class);
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
     * Test the image failure
     * 
     * @param media The image media.
     */
    private static void testImageInfoFailure(Media media)
    {
        try
        {
            ImageInfo.get(media);
            Assert.fail();
        }
        catch (final LionEngineException exception)
        {
            // Success
            Assert.assertNotNull(exception);
        }
    }

    /**
     * Test the image info from its type.
     * 
     * @param type The expected image type.
     * @param number The number of different files.
     */
    private static void testImageInfo(ImageFormat type, int number)
    {
        for (int i = 0; i < number; i++)
        {
            final String name;
            if (i == 0)
            {
                name = "image";
            }
            else
            {
                name = "image" + i;
            }
            final Media media = Medias.create(name + "." + type.name().toLowerCase(Locale.ENGLISH));
            final ImageHeader info = ImageInfo.get(media);

            Assert.assertEquals(64, info.getWidth());
            Assert.assertEquals(32, info.getHeight());
            Assert.assertEquals(type, info.getFormat());
            Assert.assertTrue(ImageInfo.isImage(media));
        }
    }

    /**
     * Test the constructor.
     * 
     * @throws Exception If error.
     */
    @Test(expected = LionEngineException.class)
    public void testConstructor() throws Exception
    {
        UtilTests.testPrivateConstructor(ImageInfo.class);
    }

    /**
     * Test image failure cases.
     */
    @Test
    public void testImageFailure()
    {
        testImageInfoFailure(null);
        testImageInfoFailure(Medias.create(Constant.EMPTY_STRING));
        testImageInfoFailure(Medias.create("image_error"));
        testImageInfoFailure(Medias.create("image.tga"));
        testImageInfoFailure(Medias.create("image_error1.gif"));
        testImageInfoFailure(Medias.create("image_error2.gif"));
        testImageInfoFailure(Medias.create("image_error1.jpg"));
        testImageInfoFailure(Medias.create("image_error2.jpg"));
        testImageInfoFailure(Medias.create("image_error3.jpg"));
        testImageInfoFailure(Medias.create("image_error1.png"));
        testImageInfoFailure(Medias.create("image_error2.png"));
        testImageInfoFailure(Medias.create("image_error1.bmp"));
        testImageInfoFailure(Medias.create("image_error1.tiff"));
        testImageInfoFailure(Medias.create("image_error2.tiff"));
        testImageInfoFailure(Medias.create("image_error3.tiff"));
        testImageInfoFailure(Medias.create("image_error4.tiff"));
        testImageInfoFailure(Medias.create("image_error5.tiff"));
        testImageInfoFailure(Medias.create("image_error6.tiff"));
        testImageInfoFailure(Medias.create("image_error7.tiff"));
        Verbose.info("*********************************** EXPECTED VERBOSE ***********************************");
        Assert.assertFalse(ImageInfo.isImage(Medias.create("image_error7.tiff")));
        Verbose.info("****************************************************************************************");
    }

    /**
     * Test image info functions.
     */
    @Test
    public void testImageInfo()
    {
        testImageInfo(ImageFormat.PNG, 1);
        testImageInfo(ImageFormat.GIF, 1);
        testImageInfo(ImageFormat.BMP, 1);
        testImageInfo(ImageFormat.JPG, 3);
        testImageInfo(ImageFormat.TIFF, 2);

        final ImageHeader info = ImageInfo.get(Medias.create("image.tif"));
        Assert.assertEquals(64, info.getWidth());
        Assert.assertEquals(32, info.getHeight());
        Assert.assertEquals(ImageFormat.TIFF, info.getFormat());

        try
        {
            Assert.assertNull(ImageInfo.get(Medias.create("image2.tiff")));
        }
        catch (final LionEngineException exception)
        {
            Assert.assertNotNull(exception);
        }
    }

    /**
     * Test skipped error tool.
     * 
     * @throws IOException The expected exception.
     * @throws Throwable If error.
     */
    @Test(expected = IOException.class)
    public void testSkippedError() throws IOException, Throwable
    {
        final Method method = ImageHeaderReaderModel.class.getDeclaredMethod("checkSkippedError",
                                                                             Long.TYPE,
                                                                             Integer.TYPE);
        final boolean back = method.isAccessible();
        UtilReflection.setAccessible(method, true);
        try
        {
            method.invoke(ImageInfo.class, Long.valueOf(1), Integer.valueOf(0));
        }
        catch (final InvocationTargetException exception)
        {
            final Throwable cause = exception.getCause();
            if (cause instanceof IOException)
            {
                throw (IOException) cause;
            }
            throw exception;
        }
        finally
        {
            UtilReflection.setAccessible(method, back);
        }
    }
}
