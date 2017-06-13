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
package com.b3dgs.lionengine;

import org.junit.Assert;
import org.junit.Test;

import com.b3dgs.lionengine.util.UtilTests;

/**
 * Test the timing class.
 */
public class TimingTest
{
    /** Pause time value. */
    private static final long PAUSE = 50L;

    /**
     * Test the start timing.
     */
    @Test
    public void testStart()
    {
        final Timing timing = new Timing();
        Assert.assertFalse(timing.isStarted());
        Assert.assertEquals(0L, timing.elapsed());
        Assert.assertFalse(timing.elapsed(-1L));
        Assert.assertFalse(timing.elapsed(0L));
        Assert.assertFalse(timing.elapsed(1L));
        Assert.assertEquals(0L, timing.get());

        timing.start();
        final long time = timing.get();

        Assert.assertTrue(timing.isStarted());

        UtilTests.pause(PAUSE);

        Assert.assertTrue(String.valueOf(timing.elapsed()), timing.elapsed() >= PAUSE);
        Assert.assertTrue(String.valueOf(timing.elapsed()), timing.elapsed(PAUSE));
        Assert.assertEquals(time + " " + timing.elapsed(), time, timing.get());

        timing.start();
        UtilTests.pause(PAUSE);

        Assert.assertTrue(String.valueOf(timing.elapsed()), timing.elapsed() >= PAUSE);
        Assert.assertTrue(String.valueOf(timing.elapsed()), timing.elapsed(PAUSE));
        Assert.assertEquals(time + " " + timing.elapsed(), time, timing.get());
    }

    /**
     * Test the stop timing.
     */
    @Test
    public void testStop()
    {
        final Timing timing = new Timing();
        timing.start();
        UtilTests.pause(PAUSE);

        timing.stop();
        Assert.assertFalse(timing.isStarted());
        Assert.assertEquals(0L, timing.get());
        Assert.assertFalse(String.valueOf(timing.elapsed()), timing.elapsed(PAUSE));
    }

    /**
     * Test the pause timing.
     */
    @Test
    public void testPause()
    {
        final Timing timing = new Timing();
        timing.start();

        UtilTests.pause(PAUSE);

        timing.pause();
        final long old = timing.get();
        final long elapsed = timing.elapsed();

        UtilTests.pause(PAUSE);

        Assert.assertEquals(old + " " + timing.elapsed(), old, timing.get());
        Assert.assertEquals(elapsed + " " + timing.elapsed(), elapsed, timing.elapsed());
    }

    /**
     * Test the unpause timing.
     */
    @Test
    public void testUnpause()
    {
        final Timing timing = new Timing();
        timing.start();
        timing.pause();

        UtilTests.pause(PAUSE);

        final long old = timing.get();
        final long elapsed = timing.elapsed();
        timing.unpause();

        UtilTests.pause(PAUSE);

        Assert.assertTrue(String.valueOf(timing.elapsed()), timing.elapsed(PAUSE));
        Assert.assertTrue(timing + " " + timing.elapsed(), timing.get() > old);
        Assert.assertTrue(timing + " " + timing.elapsed(), timing.elapsed() > elapsed);
    }

    /**
     * Test the restart timing.
     */
    @Test
    public void testRestart()
    {
        final Timing timing = new Timing();
        timing.start();

        UtilTests.pause(PAUSE);

        Assert.assertTrue(String.valueOf(timing.elapsed()), timing.elapsed() >= PAUSE);

        timing.restart();

        UtilTests.pause(PAUSE);

        Assert.assertTrue(String.valueOf(timing.elapsed()), timing.elapsed() >= PAUSE);
    }

    /**
     * Test the set timing.
     */
    @Test
    public void testSet()
    {
        final Timing timing = new Timing();
        timing.set(PAUSE);

        Assert.assertTrue(timing.isStarted());
        Assert.assertTrue(String.valueOf(timing.elapsed()), timing.elapsed(PAUSE));
    }
}
