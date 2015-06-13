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
package com.b3dgs.lionengine.editor.properties.collision.handler;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.widgets.Tree;

import com.b3dgs.lionengine.editor.UtilEclipse;
import com.b3dgs.lionengine.editor.properties.PropertiesPart;
import com.b3dgs.lionengine.game.Collision;
import com.b3dgs.lionengine.game.configurer.ConfigCollisions;
import com.b3dgs.lionengine.game.configurer.Configurer;

/**
 * Enable collisions handler.
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 */
public class CollisionsEnableHandler
{
    /**
     * Execute the handler.
     */
    @Execute
    @SuppressWarnings("static-method")
    public void execute()
    {
        final PropertiesPart part = UtilEclipse.getPart(PropertiesPart.ID, PropertiesPart.class);
        final Tree properties = part.getTree();
        final Configurer configurer = (Configurer) properties.getData();
        final Collision collision = new Collision("default", 0, 0, 0, 0, false);
        configurer.getRoot().add(ConfigCollisions.createNode(collision));
        configurer.save();
        part.setInput(properties, configurer);
    }
}