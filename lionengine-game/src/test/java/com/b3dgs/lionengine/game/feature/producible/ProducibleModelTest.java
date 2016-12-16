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
package com.b3dgs.lionengine.game.feature.producible;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.b3dgs.lionengine.Constant;
import com.b3dgs.lionengine.Media;
import com.b3dgs.lionengine.core.Medias;
import com.b3dgs.lionengine.game.feature.Featurable;
import com.b3dgs.lionengine.game.feature.FeaturableModel;
import com.b3dgs.lionengine.game.feature.Services;
import com.b3dgs.lionengine.game.feature.Setup;
import com.b3dgs.lionengine.game.feature.identifiable.Identifiable;
import com.b3dgs.lionengine.game.feature.identifiable.IdentifiableModel;
import com.b3dgs.lionengine.test.UtilTests;

/**
 * Test the producible model.
 */
public class ProducibleModelTest
{
    /**
     * Prepare test.
     */
    @BeforeClass
    public static void setUp()
    {
        Medias.setResourcesDirectory(System.getProperty("java.io.tmpdir"));
    }

    /**
     * Clean up test.
     */
    @AfterClass
    public static void cleanUp()
    {
        Medias.setResourcesDirectory(Constant.EMPTY_STRING);
    }

    /**
     * Test the producible.
     */
    @Test
    public void testProducible()
    {
        final Services services = new Services();
        final Media media = UtilProducible.createProducibleMedia();
        final Setup setup = new Setup(media);
        final Featurable featurable = new FeaturableModel();
        featurable.addFeature(new IdentifiableModel());
        featurable.prepareFeatures(services);

        final Producible producible = new ProducibleModel(setup);
        final ProducibleListener listener = UtilProducible.createListener();
        producible.setLocation(1.0, 2.0);
        producible.prepare(featurable, services);
        producible.addListener(listener);

        Assert.assertEquals(media, producible.getMedia());
        Assert.assertEquals(1, producible.getSteps());
        Assert.assertEquals(2, producible.getWidth());
        Assert.assertEquals(3, producible.getHeight());
        Assert.assertEquals(1.0, producible.getX(), UtilTests.PRECISION);
        Assert.assertEquals(2.0, producible.getY(), UtilTests.PRECISION);
        Assert.assertTrue(producible.getListeners().contains(listener));

        producible.getFeature(Identifiable.class).notifyDestroyed();
        Assert.assertTrue(media.getFile().delete());
    }

    /**
     * Test the producible self listener.
     */
    @Test
    public void testProducibleSelf()
    {
        final Services services = new Services();
        final Media media = UtilProducible.createProducibleMedia();
        final Setup setup = new Setup(media);
        final ProducibleListenerSelf object = new ProducibleListenerSelf();
        final Producible producible = new ProducibleModel(setup);
        producible.prepare(object, services);
        object.prepareFeatures(services);

        Assert.assertTrue(producible.getListeners().contains(object));

        producible.getFeature(Identifiable.class).notifyDestroyed();
        Assert.assertTrue(media.getFile().delete());
    }

    /**
     * Test the producible listener auto add.
     */
    @Test
    public void testListenerAutoAdd()
    {
        final Media media = UtilProducible.createProducibleMedia();
        final Setup setup = new Setup(media);
        final ProducibleListenerSelf object = new ProducibleListenerSelf();
        final Producible producible = new ProducibleModel(setup);
        object.prepareFeatures(new Services());
        producible.checkListener(object);

        Assert.assertTrue(producible.getListeners().contains(object));

        object.getFeature(Identifiable.class).notifyDestroyed();
        Assert.assertTrue(media.getFile().delete());
    }
}
