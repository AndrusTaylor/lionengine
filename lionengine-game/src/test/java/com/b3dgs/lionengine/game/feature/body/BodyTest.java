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
package com.b3dgs.lionengine.game.feature.body;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.b3dgs.lionengine.Constant;
import com.b3dgs.lionengine.game.Force;
import com.b3dgs.lionengine.game.feature.FeaturableModel;
import com.b3dgs.lionengine.game.feature.Identifiable;
import com.b3dgs.lionengine.game.feature.IdentifiableModel;
import com.b3dgs.lionengine.game.feature.Transformable;
import com.b3dgs.lionengine.game.feature.TransformableModel;
import com.b3dgs.lionengine.util.UtilTests;

/**
 * Test the body assignable.
 */
public class BodyTest
{
    private final Body body = new BodyModel();
    private final FeaturableModel object = new FeaturableModel();
    private final Transformable transformable = object.addFeatureAndGet(new TransformableModel());

    /**
     * Prepare test.
     */
    @Before
    public void prepare()
    {
        object.addFeature(new IdentifiableModel());
    }

    /**
     * Clean test.
     */
    @After
    public void clean()
    {
        object.getFeature(Identifiable.class).notifyDestroyed();
    }

    /**
     * Test the gravity on body.
     */
    @Test
    public void testGravity()
    {
        transformable.teleport(0, 6.0);

        body.prepare(object);
        body.setMass(2.0);
        body.setDesiredFps(50);

        Assert.assertEquals(2.0, body.getMass(), UtilTests.PRECISION);
        Assert.assertEquals(2.0 * Constant.GRAVITY_EARTH, body.getWeight(), UtilTests.PRECISION);

        body.setGravity(3.0);

        Assert.assertEquals(2.0, body.getMass(), UtilTests.PRECISION);
        Assert.assertEquals(6.0, body.getWeight(), UtilTests.PRECISION);

        body.setGravityMax(8.0);
        body.setVectors(new Force(0.0, 0.0));

        Assert.assertEquals(6.0, transformable.getOldY(), UtilTests.PRECISION);
        Assert.assertEquals(6.0, transformable.getY(), UtilTests.PRECISION);

        body.update(1.0);

        Assert.assertEquals(6.0, transformable.getOldY(), UtilTests.PRECISION);
        Assert.assertEquals(5.85, transformable.getY(), UtilTests.PRECISION);

        body.update(1.0);

        Assert.assertEquals(5.85, transformable.getOldY(), UtilTests.PRECISION);
        Assert.assertEquals(5.55, transformable.getY(), UtilTests.PRECISION);
    }

    /**
     * Test the gravity reset.
     */
    @Test
    public void testResetGravity()
    {
        transformable.teleport(0, 6.0);

        body.prepare(object);
        body.setMass(2.0);
        body.setGravity(3.0);
        body.setGravityMax(8.0);
        body.setDesiredFps(50);
        body.setVectors(new Force(0.0, 0.0));
        body.update(1.0);

        Assert.assertEquals(6.0, transformable.getOldY(), UtilTests.PRECISION);
        Assert.assertEquals(5.85, transformable.getY(), UtilTests.PRECISION);

        body.resetGravity();
        body.update(1.0);

        Assert.assertEquals(5.85, transformable.getOldY(), UtilTests.PRECISION);
        Assert.assertEquals(5.7, transformable.getY(), UtilTests.PRECISION);
    }

    /**
     * Test the gravity fps.
     */
    @Test
    public void testFps()
    {
        transformable.teleport(0, 6.0);

        body.prepare(object);
        body.setMass(1.0);
        body.setGravity(1.0);
        body.setDesiredFps(50);
        body.setVectors(new Force(0.0, -1.0));
        body.update(1.0);

        Assert.assertEquals(6.0, transformable.getOldY(), UtilTests.PRECISION);
        Assert.assertEquals(4.95, transformable.getY(), UtilTests.PRECISION);

        body.update(0.5);

        Assert.assertEquals(4.95, transformable.getOldY(), 0.01);
        Assert.assertEquals(4.41, transformable.getY(), 0.01);
    }
}
