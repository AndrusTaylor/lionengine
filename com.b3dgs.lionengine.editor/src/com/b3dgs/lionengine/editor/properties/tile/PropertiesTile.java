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
package com.b3dgs.lionengine.editor.properties.tile;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.b3dgs.lionengine.core.Media;
import com.b3dgs.lionengine.editor.UtilEclipse;
import com.b3dgs.lionengine.editor.properties.PropertiesPart;
import com.b3dgs.lionengine.editor.properties.PropertiesProviderTile;
import com.b3dgs.lionengine.editor.world.WorldViewModel;
import com.b3dgs.lionengine.editor.world.WorldViewPart;
import com.b3dgs.lionengine.editor.world.WorldViewRenderer;
import com.b3dgs.lionengine.game.collision.TileGroup;
import com.b3dgs.lionengine.game.configurer.ConfigTileGroup;
import com.b3dgs.lionengine.game.map.MapTile;
import com.b3dgs.lionengine.game.map.Tile;
import com.b3dgs.lionengine.game.map.TileFeature;
import com.b3dgs.lionengine.stream.Stream;
import com.b3dgs.lionengine.stream.XmlNode;

/**
 * Element properties part.
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 */
public class PropertiesTile
        implements PropertiesProviderTile
{
    /** Tile group icon. */
    private static final Image ICON_GROUP = UtilEclipse.getIcon("properties", "tilegroup.png");
    /** Tile sheet icon. */
    private static final Image ICON_SHEET = UtilEclipse.getIcon("properties", "tilesheet.png");
    /** Tile number icon. */
    private static final Image ICON_NUMBER = UtilEclipse.getIcon("properties", "tilenumber.png");
    /** Tile size icon. */
    private static final Image ICON_SIZE = UtilEclipse.getIcon("properties", "tilesize.png");
    /** Tile features icon. */
    private static final Image ICON_FEATURES = UtilEclipse.getIcon("properties", "tilefeatures.png");
    /** Tile feature icon. */
    private static final Image ICON_FEATURE = UtilEclipse.getIcon("properties", "tilefeature.png");

    /**
     * Change tile group.
     * 
     * @param map The map reference.
     * @param oldGroup The old group name.
     * @param newGroup The new group name.
     * @param tile The tile reference.
     */
    static void changeTileGroup(MapTile map, String oldGroup, String newGroup, Tile tile)
    {
        final Media config = map.getGroupsConfig();
        final XmlNode node = Stream.loadXml(config);
        for (final XmlNode nodeGroup : node.getChildren(ConfigTileGroup.GROUP))
        {
            final Collection<XmlNode> toRemove = new ArrayList<>();
            if (WorldViewRenderer.groupEquals(nodeGroup.readString(ConfigTileGroup.NAME), oldGroup))
            {
                for (final XmlNode nodeTile : nodeGroup.getChildren(ConfigTileGroup.TILE))
                {
                    if (nodeTile.readInteger(ConfigTileGroup.SHEET) == tile.getSheet().intValue()
                            && nodeTile.readInteger(ConfigTileGroup.NUMBER) == tile.getNumber())
                    {
                        toRemove.add(nodeTile);
                    }
                }
                for (final XmlNode remove : toRemove)
                {
                    nodeGroup.removeChild(remove);
                }
            }
            if (WorldViewRenderer.groupEquals(nodeGroup.readString(ConfigTileGroup.NAME), newGroup))
            {
                final XmlNode tileRef = Stream.createXmlNode(ConfigTileGroup.TILE);
                tileRef.writeInteger(ConfigTileGroup.SHEET, tile.getSheet().intValue());
                tileRef.writeInteger(ConfigTileGroup.NUMBER, tile.getNumber());
                nodeGroup.add(tileRef);
            }
        }
        Stream.saveXml(node, config);
        map.loadGroups(config);

    }

    /**
     * Create the attribute group.
     * 
     * @param properties The properties tree reference.
     * @param tile The tile reference.
     */
    private static void createAttributeTileGroup(final Tree properties, final Tile tile)
    {
        final TreeItem item = new TreeItem(properties, SWT.NONE);
        PropertiesPart.createLine(item, Messages.Properties_TileGroup, tile.getGroup());
        item.setData(ConfigTileGroup.GROUP);
        item.setImage(PropertiesTile.ICON_GROUP);

        properties.addListener(SWT.MouseDoubleClick, new Listener()
        {
            @Override
            public void handleEvent(Event event)
            {
                final Point point = new Point(event.x, event.y);
                final TreeItem selection = properties.getItem(point);
                if (selection == item)
                {
                    final MapTile map = WorldViewModel.INSTANCE.getMap();
                    final Collection<TileGroup> groups = map.getGroups();
                    final Collection<String> values = new ArrayList<>();
                    for (final TileGroup group : groups)
                    {
                        values.add(group.getName());
                    }
                    final GroupChooser chooser = new GroupChooser(properties.getShell(), values);
                    chooser.open();
                    final String oldGroup = tile.getGroup();
                    final String newGroup = chooser.getChoice();
                    if (newGroup != null)
                    {
                        changeTileGroup(map, oldGroup, newGroup, tile);

                        final WorldViewPart part = UtilEclipse.getPart(WorldViewPart.ID, WorldViewPart.class);
                        part.update();
                    }
                }
            }
        });
    }

    /**
     * Create the attribute sheet number.
     * 
     * @param properties The properties tree reference.
     * @param tile The tile reference.
     */
    private static void createAttributeTileSheet(Tree properties, Tile tile)
    {
        final TreeItem item = new TreeItem(properties, SWT.NONE);
        PropertiesPart.createLine(item, Messages.Properties_TileSheet, String.valueOf(tile.getSheet()));
        item.setData(ConfigTileGroup.SHEET);
        item.setImage(PropertiesTile.ICON_SHEET);
    }

    /**
     * Create the attribute tile number.
     * 
     * @param properties The properties tree reference.
     * @param tile The tile reference.
     */
    private static void createAttributeTileNumber(Tree properties, Tile tile)
    {
        final TreeItem item = new TreeItem(properties, SWT.NONE);
        PropertiesPart.createLine(item, Messages.Properties_TileNumber, String.valueOf(tile.getNumber()));
        item.setData(ConfigTileGroup.NUMBER);
        item.setImage(PropertiesTile.ICON_NUMBER);
    }

    /**
     * Create the attribute tile size.
     * 
     * @param properties The properties tree reference.
     * @param tile The tile reference.
     */
    private static void createAttributeTileSize(Tree properties, Tile tile)
    {
        final TreeItem item = new TreeItem(properties, SWT.NONE);
        PropertiesPart.createLine(item, Messages.Properties_TileSize, tile.getWidth() + " * " + tile.getHeight());
        item.setData(ConfigTileGroup.TILE);
        item.setImage(PropertiesTile.ICON_SIZE);
    }

    /**
     * Create the attribute tile features.
     * 
     * @param properties The properties tree reference.
     * @param tile The tile reference.
     */
    private static void createAttributeTileFeatures(Tree properties, Tile tile)
    {
        final TreeItem features = new TreeItem(properties, SWT.NONE);
        features.setText(Messages.Properties_TileFeatures);
        features.setImage(PropertiesTile.ICON_FEATURES);

        for (final TileFeature feature : tile.getFeatures())
        {
            final TreeItem item = new TreeItem(features, SWT.NONE);
            item.setText(Messages.Properties_TileFeature);
            item.setImage(PropertiesTile.ICON_FEATURE);

            final Class<?> clazz = feature.getClass();
            for (final Class<?> type : clazz.getInterfaces())
            {
                if (TileFeature.class.isAssignableFrom(type))
                {
                    PropertiesPart.createLine(item, type.getSimpleName(), clazz.getName());
                }
            }
        }
    }

    /*
     * PropertiesProviderTile
     */

    @Override
    public void setInput(Tree properties, Tile tile)
    {
        createAttributeTileGroup(properties, tile);
        createAttributeTileSheet(properties, tile);
        createAttributeTileNumber(properties, tile);
        createAttributeTileSize(properties, tile);
        createAttributeTileFeatures(properties, tile);
    }
}