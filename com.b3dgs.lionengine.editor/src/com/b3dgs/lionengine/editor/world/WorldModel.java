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
package com.b3dgs.lionengine.editor.world;

import com.b3dgs.lionengine.game.Camera;
import com.b3dgs.lionengine.game.map.MapTile;
import com.b3dgs.lionengine.game.map.MapTileGame;
import com.b3dgs.lionengine.game.map.Minimap;
import com.b3dgs.lionengine.game.object.ComponentRenderer;
import com.b3dgs.lionengine.game.object.ComponentUpdater;
import com.b3dgs.lionengine.game.object.Factory;
import com.b3dgs.lionengine.game.object.Handler;
import com.b3dgs.lionengine.game.object.Services;

/**
 * Contains the objects of the world.
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 */
public class WorldModel
{
    /** World model. */
    public static final WorldModel INSTANCE = new WorldModel();

    /** Services reference. */
    private final Services services = new Services();
    /** Factory reference. */
    private final Factory factory = services.create(Factory.class);
    /** Camera reference. */
    private final Camera camera = services.create(Camera.class);
    /** Map reference. */
    private final MapTile map = services.create(MapTileGame.class);
    /** Minimap reference. */
    private final Minimap minimap = new Minimap(map);
    /** Selected palette. */
    private Enum<?> palette = PaletteType.POINTER_OBJECT;

    /**
     * Constructor.
     */
    protected WorldModel()
    {
        final Handler handler = new Handler();
        handler.addUpdatable(new ComponentUpdater());
        handler.addRenderable(new ComponentRenderer());
        services.add(handler);

        final Selection selection = new Selection(services);
        services.add(selection);

        final ObjectControl objectControl = new ObjectControl(services);
        services.add(objectControl);
    }

    /**
     * Set the selected palette.
     * 
     * @param palette The selected palette.
     */
    public void setSelectedPalette(Enum<?> palette)
    {
        this.palette = palette;
    }

    /**
     * Get the services reference.
     * 
     * @return The services reference.
     */
    public Services getServices()
    {
        return services;
    }

    /**
     * Get the factory reference.
     * 
     * @return The factory reference.
     */
    public Factory getFactory()
    {
        return factory;
    }

    /**
     * Get the camera reference.
     * 
     * @return The camera reference.
     */
    public Camera getCamera()
    {
        return camera;
    }

    /**
     * Get the map reference.
     * 
     * @return The map reference.
     */
    public MapTile getMap()
    {
        return map;
    }

    /**
     * Get the minimap reference.
     * 
     * @return The minimap reference.
     */
    public Minimap getMinimap()
    {
        return minimap;
    }

    /**
     * Get the selected palette.
     * 
     * @return The selected palette.
     */
    public Enum<?> getSelectedPalette()
    {
        return palette;
    }

    /**
     * Check if current palette is the specified type.
     * 
     * @param type The expected type.
     * @return <code>true</code> if right type, <code>false</code> else.
     */
    public boolean isPalette(PaletteType type)
    {
        return palette.equals(type);
    }
}