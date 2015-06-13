/*
 * Copyright (C) 2013-2015 Byron 3D Games Studio (www.b3dgs.com) Pierre-Alexandre (contact@b3dgs.com)
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
package com.b3dgs.lionengine.core.awt;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Keyboard implementation.
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 */
final class KeyboardAwt
        implements Keyboard, KeyListener
{
    /** No key code. */
    private static final int NO_KEY_CODE = -1;
    /** No key code value. */
    private static final Integer NO_KEY_CODE_VALUE = Integer.valueOf(NO_KEY_CODE);
    /** Empty key name. */
    private static final char EMPTY_KEY_NAME = ' ';

    /** Actions pressed listeners. */
    private final Map<Integer, List<EventAction>> actionsPressed = new HashMap<>();
    /** Actions released listeners. */
    private final Map<Integer, List<EventAction>> actionsReleased = new HashMap<>();
    /** List of keys. */
    private final Collection<Integer> keys = new HashSet<>();
    /** Pressed states. */
    private final Collection<Integer> pressed = new HashSet<>();
    /** Last key code. */
    private volatile Integer lastCode = NO_KEY_CODE_VALUE;
    /** Last key name. */
    private volatile char lastKeyName = EMPTY_KEY_NAME;
    /** Left key. */
    private Integer left = LEFT;
    /** Right key. */
    private Integer right = RIGHT;
    /** Up key. */
    private Integer up = UP;
    /** Down key. */
    private Integer down = DOWN;

    /**
     * Internal constructor.
     */
    KeyboardAwt()
    {
        lastKeyName = ' ';
        lastCode = Integer.valueOf(-1);
    }

    /*
     * Keyboard
     */

    @Override
    public void addActionPressed(Integer key, EventAction action)
    {
        final List<EventAction> list;
        if (actionsPressed.get(key) == null)
        {
            list = new ArrayList<>();
            actionsPressed.put(key, list);
        }
        else
        {
            list = actionsPressed.get(key);
        }
        list.add(action);
    }

    @Override
    public void addActionReleased(Integer key, EventAction action)
    {
        final List<EventAction> list;
        if (actionsReleased.get(key) == null)
        {
            list = new ArrayList<>();
            actionsReleased.put(key, list);
        }
        else
        {
            list = actionsReleased.get(key);
        }
        list.add(action);
    }

    @Override
    public void removeActionsPressed()
    {
        actionsPressed.clear();
    }

    @Override
    public void removeActionsReleased()
    {
        actionsReleased.clear();
    }

    @Override
    public boolean isPressed(Integer key)
    {
        return keys.contains(key);
    }

    @Override
    public boolean isPressedOnce(Integer key)
    {
        if (keys.contains(key))
        {
            if (!pressed.contains(key))
            {
                pressed.add(key);
                return true;
            }
        }
        return false;
    }

    @Override
    public Integer getKeyCode()
    {
        return lastCode;
    }

    @Override
    public char getKeyName()
    {
        return lastKeyName;
    }

    @Override
    public boolean used()
    {
        return !keys.isEmpty();
    }

    /*
     * InputDeviceDirectional
     */

    @Override
    public void setHorizontalControlPositive(Integer code)
    {
        right = code;
    }

    @Override
    public void setHorizontalControlNegative(Integer code)
    {
        left = code;
    }

    @Override
    public void setVerticalControlPositive(Integer code)
    {
        up = code;
    }

    @Override
    public void setVerticalControlNegative(Integer code)
    {
        down = code;
    }

    @Override
    public double getHorizontalDirection()
    {
        if (isPressed(left))
        {
            return -1;
        }
        else if (isPressed(right))
        {
            return 1;
        }
        return 0;
    }

    @Override
    public double getVerticalDirection()
    {
        if (isPressed(down))
        {
            return -1;
        }
        else if (isPressed(up))
        {
            return 1;
        }
        return 0;
    }

    /*
     * KeyListener
     */

    @Override
    public void keyPressed(KeyEvent event)
    {
        lastKeyName = event.getKeyChar();
        lastCode = Integer.valueOf(event.getKeyCode());
        if (!keys.contains(lastCode))
        {
            keys.add(lastCode);
        }

        if (actionsPressed.containsKey(lastCode))
        {
            final List<EventAction> actions = actionsPressed.get(lastCode);
            for (final EventAction current : actions)
            {
                current.action();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent event)
    {
        lastKeyName = EMPTY_KEY_NAME;
        lastCode = NO_KEY_CODE_VALUE;

        final Integer key = Integer.valueOf(event.getKeyCode());
        keys.remove(key);
        pressed.remove(key);

        if (actionsPressed.containsKey(key))
        {
            final List<EventAction> actions = actionsReleased.get(key);
            for (final EventAction current : actions)
            {
                current.action();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent event)
    {
        // Nothing to do
    }
}
