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
package com.b3dgs.lionengine.editor.project.dialog.group;

import java.util.ArrayList;
import java.util.Collection;

import com.b3dgs.lionengine.core.Media;
import com.b3dgs.lionengine.editor.ObjectList;
import com.b3dgs.lionengine.editor.ObjectListListener;
import com.b3dgs.lionengine.editor.world.WorldViewModel;
import com.b3dgs.lionengine.editor.world.WorldViewRenderer;
import com.b3dgs.lionengine.game.collision.TileGroup;
import com.b3dgs.lionengine.game.collision.TileGroup.TileRef;
import com.b3dgs.lionengine.game.configurer.ConfigTileGroup;
import com.b3dgs.lionengine.game.map.MapTile;
import com.b3dgs.lionengine.stream.Stream;
import com.b3dgs.lionengine.stream.XmlNode;

/**
 * Represents the groups list, allowing to add and remove {@link TileGroup}.
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 */
public class GroupList
        extends ObjectList<TileGroup>
        implements ObjectListListener<TileGroup>
{
    /**
     * Create the group list.
     */
    public GroupList()
    {
        super(TileGroup.class);
    }

    /**
     * Load the existing groups from the object configurer.
     * 
     * @param config The config file.
     */
    public void loadGroups(Media config)
    {
        final Collection<TileGroup> groups = ConfigTileGroup.create(Stream.loadXml(config));
        loadObjects(groups);
    }

    /*
     * ObjectList
     */

    @Override
    protected TileGroup copyObject(TileGroup group)
    {
        return new TileGroup(group.getName(), group.getTiles());
    }

    @Override
    protected TileGroup createObject(String name)
    {
        return new TileGroup(name, new ArrayList<TileRef>());
    }

    /*
     * ObjectListListener
     */

    @Override
    public void notifyObjectSelected(TileGroup group)
    {
        // Nothing to do
    }

    @Override
    public void notifyObjectDeleted(TileGroup group)
    {
        final MapTile map = WorldViewModel.INSTANCE.getMap();
        final Media config = map.getGroupsConfig();
        final XmlNode node = Stream.loadXml(config);
        final Collection<XmlNode> toRemove = new ArrayList<>();
        for (final XmlNode nodeGroup : node.getChildren(ConfigTileGroup.GROUP))
        {
            if (WorldViewRenderer.groupEquals(nodeGroup.readString(ConfigTileGroup.NAME), group.getName()))
            {
                toRemove.add(nodeGroup);
            }
        }
        for (final XmlNode remove : toRemove)
        {
            node.removeChild(remove);
        }
        toRemove.clear();
        Stream.saveXml(node, config);
        map.loadGroups(config);
    }
}