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
package com.b3dgs.lionengine.example.game.projectile;

import com.b3dgs.lionengine.Media;
import com.b3dgs.lionengine.Origin;
import com.b3dgs.lionengine.core.Medias;
import com.b3dgs.lionengine.drawable.Drawable;
import com.b3dgs.lionengine.drawable.Sprite;
import com.b3dgs.lionengine.game.collision.object.Collidable;
import com.b3dgs.lionengine.game.collision.object.CollidableModel;
import com.b3dgs.lionengine.game.feature.FeaturableModel;
import com.b3dgs.lionengine.game.feature.Service;
import com.b3dgs.lionengine.game.feature.SetupSurface;
import com.b3dgs.lionengine.game.feature.displayable.DisplayableModel;
import com.b3dgs.lionengine.game.feature.identifiable.Identifiable;
import com.b3dgs.lionengine.game.feature.launchable.Launchable;
import com.b3dgs.lionengine.game.feature.launchable.LaunchableModel;
import com.b3dgs.lionengine.game.feature.refreshable.RefreshableModel;
import com.b3dgs.lionengine.game.feature.transformable.Transformable;
import com.b3dgs.lionengine.game.feature.transformable.TransformableModel;
import com.b3dgs.lionengine.graphic.Viewer;

/**
 * Projectile implementation.
 */
class Projectile extends FeaturableModel
{
    /** Media. */
    public static final Media PULSE = Medias.create("Pulse.xml");

    @Service private Viewer viewer;

    /**
     * Constructor.
     * 
     * @param setup The setup reference.
     */
    public Projectile(SetupSurface setup)
    {
        super();

        final Transformable transformable = addFeatureAndGet(new TransformableModel());
        final Launchable launchable = addFeatureAndGet(new LaunchableModel());
        final Collidable collidable = addFeatureAndGet(new CollidableModel(setup));
        collidable.setOrigin(Origin.MIDDLE);
        collidable.setGroup(0);

        final Sprite sprite = Drawable.loadSprite(setup.getSurface());
        sprite.setOrigin(Origin.MIDDLE);

        addFeature(new RefreshableModel(extrp ->
        {
            launchable.update(extrp);
            sprite.setLocation(viewer, transformable);
            if (!viewer.isViewable(transformable, 0, 0))
            {
                getFeature(Identifiable.class).destroy();
            }
        }));

        addFeature(new DisplayableModel(g ->
        {
            sprite.render(g);
            collidable.render(g);
        }));
    }
}
