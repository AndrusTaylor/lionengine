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
package com.b3dgs.lionengine.game.feature.tile.map.pathfinding;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.b3dgs.lionengine.LionEngineException;
import com.b3dgs.lionengine.Media;
import com.b3dgs.lionengine.game.FeatureProvider;
import com.b3dgs.lionengine.game.Services;
import com.b3dgs.lionengine.game.Tiled;
import com.b3dgs.lionengine.game.feature.FeatureModel;
import com.b3dgs.lionengine.game.feature.tile.Tile;
import com.b3dgs.lionengine.game.feature.tile.map.MapTile;
import com.b3dgs.lionengine.game.feature.tile.map.MapTileGroup;
import com.b3dgs.lionengine.game.feature.tile.map.MapTileGroupModel;
import com.b3dgs.lionengine.util.UtilMath;

/**
 * Map tile path model implementation.
 */
public class MapTilePathModel extends FeatureModel implements MapTilePath
{
    /** Categories list. */
    private final Map<String, PathCategory> categories = new HashMap<String, PathCategory>();
    /** Map reference. */
    private MapTile map;
    /** Map group reference. */
    private MapTileGroup mapGroup;

    /**
     * Create a map tile path.
     * <p>
     * The {@link Services} must provide the following services:
     * </p>
     * <ul>
     * <li>{@link MapTile}</li>
     * </ul>
     * 
     * @throws LionEngineException If services not found.
     */
    public MapTilePathModel()
    {
        super();
    }

    /**
     * Get the closest unused location around the area. The returned tile is not blocking, nor used by an object.
     * 
     * @param mover The object moving on map.
     * @param stx The starting horizontal tile index.
     * @param sty The starting vertical tile index.
     * @param stw The source location width in tile.
     * @param sth The source location height in tile.
     * @param dtx The ending horizontal tile index.
     * @param dty The ending vertical tile index.
     * @param dtw The destination location width in tile.
     * @param dth The destination location height in tile.
     * @param radius The search radius.
     * @return The closest tile found.
     */
    private CoordTile getClosestAvailableTile(Pathfindable mover,
                                              int stx,
                                              int sty,
                                              int stw,
                                              int sth,
                                              int dtx,
                                              int dty,
                                              int dtw,
                                              int dth,
                                              int radius)
    {
        int closestX = 0;
        int closestY = 0;
        double dist = Double.MAX_VALUE;
        int size = 1;
        boolean found = false;
        while (!found)
        {
            for (int tx = stx - size; tx <= stx + size; tx++)
            {
                for (int ty = sty - size; ty <= sty + size; ty++)
                {
                    if (isAreaAvailable(mover, tx, ty, stw, sth, null))
                    {
                        final double d = UtilMath.getDistance(tx, ty, stw, sth, dtx, dty, dtw, dth);
                        if (d < dist)
                        {
                            dist = d;
                            closestX = tx;
                            closestY = ty;
                            found = true;
                        }
                    }
                }
            }
            size++;
            if (size >= radius)
            {
                return null;
            }
        }
        return new CoordTile(closestX, closestY);
    }

    /**
     * Get the group category.
     * 
     * @param group The group name.
     * @return The category name (<code>null</code> if undefined).
     */
    private String getCategory(String group)
    {
        for (final PathCategory category : categories.values())
        {
            if (category.getGroups().contains(group))
            {
                return category.getName();
            }
        }
        return null;
    }

    /*
     * MapTilePath
     */

    @Override
    public void prepare(FeatureProvider provider, Services services)
    {
        super.prepare(provider, services);

        map = services.get(MapTile.class);
        mapGroup = map.getFeature(MapTileGroupModel.class);
    }

    @Override
    public void loadPathfinding(Media pathfindingConfig)
    {
        final Collection<PathCategory> config = PathfindingConfig.imports(pathfindingConfig);
        categories.clear();
        for (final PathCategory category : config)
        {
            categories.put(category.getName(), category);
        }
        for (int ty = 0; ty < map.getInTileHeight(); ty++)
        {
            for (int tx = 0; tx < map.getInTileWidth(); tx++)
            {
                final Tile tile = map.getTile(tx, ty);
                if (tile != null)
                {
                    final String group = mapGroup.getGroup(tile);
                    final String category = getCategory(group);
                    final TilePath tilePath = new TilePathModel(category);
                    tile.addFeature(tilePath);
                }
            }
        }
    }

    @Override
    public void addObjectId(int tx, int ty, Integer id)
    {
        final Tile tile = map.getTile(tx, ty);
        if (tile != null)
        {
            final TilePath tilePath = tile.getFeature(TilePath.class);
            tilePath.addObjectId(id);
        }
    }

    @Override
    public void removeObjectId(int tx, int ty, Integer id)
    {
        final Tile tile = map.getTile(tx, ty);
        if (tile != null)
        {
            final TilePath tilePath = tile.getFeature(TilePath.class);
            tilePath.removeObjectId(id);
        }
    }

    @Override
    public Collection<Integer> getObjectsId(int tx, int ty)
    {
        final Tile tile = map.getTile(tx, ty);
        if (tile != null)
        {
            final TilePath tilePath = tile.getFeature(TilePath.class);
            return tilePath.getObjectsId();
        }
        return Collections.emptyList();
    }

    @Override
    public Tile getTile(Tiled tiled)
    {
        return map.getTile(tiled.getInTileX(), tiled.getInTileY());
    }

    @Override
    public boolean isBlocked(Pathfindable mover, int tx, int ty, boolean ignoreObjectsId)
    {
        // Blocked if outside map range
        if (ty >= 0 && tx >= 0 && ty < map.getInTileHeight() && tx < map.getInTileWidth())
        {
            // Check if all objects id are non blocking
            if (!ignoreObjectsId)
            {
                final Collection<Integer> ids = getObjectsId(tx, ty);
                int ignoredCount = 0;
                for (final Integer id : ids)
                {
                    if (mover.isIgnoredId(id))
                    {
                        ignoredCount++;
                    }
                }
                if (ignoredCount < ids.size())
                {
                    return true;
                }
            }
            // Check if tile is blocking
            final Tile tile = map.getTile(tx, ty);
            if (tile != null)
            {
                final TilePath tilePath = tile.getFeature(TilePath.class);
                return mover.isBlocking(tilePath.getCategory());
            }
        }
        return true;
    }

    @Override
    public double getCost(Pathfindable mover, int tx, int ty)
    {
        final Tile tile = map.getTile(tx, ty);
        if (tile != null)
        {
            final TilePath tilePath = tile.getFeature(TilePath.class);
            return mover.getCost(tilePath.getCategory());
        }
        return 0.0;
    }

    @Override
    public Collection<String> getCategories()
    {
        return categories.keySet();
    }

    @Override
    public CoordTile getFreeTileAround(Pathfindable mover, Tiled tiled, int radius)
    {
        return getFreeTileAround(mover,
                                 tiled.getInTileX(),
                                 tiled.getInTileY(),
                                 tiled.getInTileWidth(),
                                 tiled.getInTileHeight(),
                                 radius);
    }

    @Override
    public CoordTile getFreeTileAround(Pathfindable mover, int tx, int ty, int tw, int th, int radius)
    {
        int size = 0;
        boolean search = true;
        while (search)
        {
            for (int ctx = tx - size; ctx <= tx + size; ctx++)
            {
                for (int cty = ty - size; cty <= ty + size; cty++)
                {
                    if (isAreaAvailable(mover, ctx, cty, tw, th, null))
                    {
                        return new CoordTile(ctx, cty);
                    }
                }
            }
            size++;
            if (size > radius)
            {
                search = false;
            }
        }
        return null;
    }

    @Override
    public CoordTile getClosestAvailableTile(Pathfindable mover, Tiled to, int radius)
    {
        return getClosestAvailableTile(mover,
                                       mover.getInTileX(),
                                       mover.getInTileY(),
                                       mover.getInTileWidth(),
                                       mover.getInTileHeight(),
                                       to.getInTileX(),
                                       to.getInTileY(),
                                       to.getInTileWidth(),
                                       to.getInTileHeight(),
                                       radius);
    }

    @Override
    public CoordTile getClosestAvailableTile(Pathfindable mover, int stx, int sty, int dtx, int dty, int radius)
    {
        return getClosestAvailableTile(mover, stx, sty, 1, 1, dtx, dty, 1, 1, radius);
    }

    @Override
    public boolean isAreaAvailable(Pathfindable mover, int tx, int ty, int tw, int th, Integer ignoreObjectId)
    {
        for (int cty = ty; cty < ty + th; cty++)
        {
            for (int ctx = tx; ctx < tx + tw; ctx++)
            {
                final Collection<Integer> ids = getObjectsId(ctx, cty);
                final Tile tile = map.getTile(ctx, cty);
                if (tile != null)
                {
                    final TilePath tilePath = tile.getFeature(TilePath.class);
                    if (mover.isBlocking(tilePath.getCategory())
                        || ignoreObjectId != null && !ids.isEmpty() && !ids.contains(ignoreObjectId))
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
