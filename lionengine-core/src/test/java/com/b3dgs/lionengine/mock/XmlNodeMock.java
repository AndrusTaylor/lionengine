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
package com.b3dgs.lionengine.mock;

import java.util.Collection;
import java.util.Map;

import com.b3dgs.lionengine.stream.XmlNode;

/**
 * Mock node xml.
 */
public final class XmlNodeMock implements XmlNode
{
    /** Float precision. */
    public static final float FLOAT_PRECISION = 0.0001f;
    /** Double precision. */
    public static final double DOUBLE_PRECISION = 0.000000000000001;
    /** Boolean value. */
    public static final boolean BOOL_VALUE = true;
    /** Byte value. */
    public static final byte BYTE_VALUE = 1;
    /** Char value. */
    public static final char CHAR_VALUE = 2;
    /** Short value. */
    public static final short SHORT_VALUE = 3;
    /** Int value. */
    public static final int INT_VALUE = 4;
    /** Float value. */
    public static final float FLOAT_VALUE = 5.1f;
    /** Long value. */
    public static final long LONG_VALUE = 6L;
    /** Double value. */
    public static final double DOUBLE_VALUE = 7.1;
    /** String value. */
    public static final String STRING_VALUE = "string";

    /**
     * Constructor.
     */
    public XmlNodeMock()
    {
        super();
    }

    /*
     * XmlNode
     */

    @Override
    public XmlNode createChild(String child)
    {
        return new XmlNodeMock();
    }

    @Override
    public void setText(String text)
    {
        // Nothing to do
    }

    @Override
    public boolean readBoolean(String attribute)
    {
        return false;
    }

    @Override
    public boolean readBoolean(boolean defaultValue, String attribute)
    {
        return false;
    }

    @Override
    public byte readByte(String attribute)
    {
        return 0;
    }

    @Override
    public byte readByte(byte defaultValue, String attribute)
    {
        return 0;
    }

    @Override
    public short readShort(String attribute)
    {
        return 0;
    }

    @Override
    public short readShort(short defaultValue, String attribute)
    {
        return 0;
    }

    @Override
    public int readInteger(String attribute)
    {
        return 0;
    }

    @Override
    public int readInteger(int defaultValue, String attribute)
    {
        return 0;
    }

    @Override
    public long readLong(String attribute)
    {
        return 0;
    }

    @Override
    public long readLong(long defaultValue, String attribute)
    {
        return 0;
    }

    @Override
    public float readFloat(String attribute)
    {
        return 0;
    }

    @Override
    public float readFloat(float defaultValue, String attribute)
    {
        return 0;
    }

    @Override
    public double readDouble(String attribute)
    {
        return 0;
    }

    @Override
    public double readDouble(double defaultValue, String attribute)
    {
        return 0;
    }

    @Override
    public String readString(String attribute)
    {
        return null;
    }

    @Override
    public String readString(String defaultValue, String attribute)
    {
        return null;
    }

    @Override
    public void add(XmlNode node)
    {
        // Nothing to do
    }

    @Override
    public void writeByte(String attribute, byte content)
    {
        // Nothing to do
    }

    @Override
    public void writeShort(String attribute, short content)
    {
        // Nothing to do
    }

    @Override
    public void writeInteger(String attribute, int content)
    {
        // Nothing to do
    }

    @Override
    public void writeLong(String attribute, long content)
    {
        // Nothing to do
    }

    @Override
    public void writeFloat(String attribute, float content)
    {
        // Nothing to do
    }

    @Override
    public void writeDouble(String attribute, double content)
    {
        // Nothing to do
    }

    @Override
    public void writeString(String attribute, String content)
    {
        // Nothing to do
    }

    @Override
    public void writeBoolean(String attribute, boolean content)
    {
        // Nothing to do
    }

    @Override
    public void removeAttribute(String attribute)
    {
        // Nothing to do
    }

    @Override
    public void removeChild(String child)
    {
        // Nothing to do
    }

    @Override
    public void removeChild(XmlNode child)
    {
        // Nothing to do
    }

    @Override
    public void removeChildren(String children)
    {
        // Nothing to do
    }

    @Override
    public String getNodeName()
    {
        return "mock";
    }

    @Override
    public String getText()
    {
        return null;
    }

    @Override
    public XmlNode getChild(String name)
    {
        return null;
    }

    @Override
    public Collection<XmlNode> getChildren(String name)
    {
        return null;
    }

    @Override
    public Collection<XmlNode> getChildren()
    {
        return null;
    }

    @Override
    public Map<String, String> getAttributes()
    {
        return null;
    }

    @Override
    public boolean hasAttribute(String attribute)
    {
        return false;
    }

    @Override
    public boolean hasChild(String child)
    {
        return false;
    }
}
