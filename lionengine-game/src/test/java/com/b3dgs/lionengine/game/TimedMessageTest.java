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
package com.b3dgs.lionengine.game;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test timed message class.
 */
public class TimedMessageTest
{
    /**
     * Test timed message functions.
     * 
     * @throws InterruptedException If error.
     */
    @Test
    public void testTimedMessage() throws InterruptedException
    {
        final TimedMessage timedMessage = new TimedMessage(null);

        Assert.assertFalse(timedMessage.hasMessage());

        timedMessage.addMessage("test", 0, 0, 50);
        timedMessage.addMessage("test", 0, 0, 100);

        Assert.assertTrue(timedMessage.hasMessage());

        timedMessage.update(0.0);
        Thread.sleep(25);
        timedMessage.update(0.0);
        timedMessage.update(0.0);
        Thread.sleep(100);
        timedMessage.update(0.0);

        Assert.assertFalse(timedMessage.hasMessage());
    }
}
