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
package com.b3dgs.lionengine.graphic;

import com.b3dgs.lionengine.Config;
import com.b3dgs.lionengine.LionEngineException;
import com.b3dgs.lionengine.Media;

/**
 * Represents the graphic factory.
 */
public interface FactoryGraphic
{
    /**
     * Create a screen.
     * 
     * @param config The config reference.
     * @return The screen instance.
     */
    Screen createScreen(Config config);

    /**
     * Create a graphic context.
     * 
     * @return The graphic context.
     */
    Graphic createGraphic();

    /**
     * Create a transform.
     * 
     * @return The created transform.
     */
    Transform createTransform();

    /**
     * Crate a text.
     * 
     * @param fontName The font name.
     * @param size The font size (in pixel).
     * @param style The font style.
     * @return The created text.
     */
    Text createText(String fontName, int size, TextStyle style);

    /**
     * Create an image buffer.
     * 
     * @param width The image width.
     * @param height The image height.
     * @param transparency The image transparency.
     * @return The image buffer.
     */
    ImageBuffer createImageBuffer(int width, int height, Transparency transparency);

    /**
     * Get an image buffer from an image file.
     * 
     * @param media The image media.
     * @return The created image buffer from file.
     * @throws LionEngineException If an error occurred when reading the image.
     */
    ImageBuffer getImageBuffer(Media media);

    /**
     * Get an image buffer from an image buffer.
     * 
     * @param imageBuffer The image buffer.
     * @return The created image buffer from file.
     */
    ImageBuffer getImageBuffer(ImageBuffer imageBuffer);

    /**
     * Apply color mask to the image.
     * 
     * @param imageBuffer The image reference.
     * @param maskColor The color mask.
     * @return The masked image buffer.
     */
    ImageBuffer applyMask(ImageBuffer imageBuffer, ColorRgba maskColor);

    /**
     * Split an image into an array of sub image.
     * 
     * @param image The image to split.
     * @param h The number of horizontal divisions (strictly positive).
     * @param v The number of vertical divisions (strictly positive).
     * @return The splited images array (can not be empty).
     */
    ImageBuffer[] splitImage(ImageBuffer image, int h, int v);

    /**
     * Rotate input image buffer.
     * 
     * @param image The input image buffer.
     * @param angle The angle to apply in degree (0-359)
     * @return The new image buffer with angle applied.
     */
    ImageBuffer rotate(ImageBuffer image, int angle);

    /**
     * Resize input image buffer.
     * 
     * @param image The input image buffer.
     * @param width The new width.
     * @param height The new height.
     * @return The new image buffer with new size.
     */
    ImageBuffer resize(ImageBuffer image, int width, int height);

    /**
     * Apply an horizontal flip to the input image.
     * 
     * @param image The input image buffer.
     * @return The flipped image buffer as a new instance.
     */
    ImageBuffer flipHorizontal(ImageBuffer image);

    /**
     * Apply a vertical flip to the input image.
     * 
     * @param image The input image buffer.
     * @return The flipped image buffer as a new instance.
     */
    ImageBuffer flipVertical(ImageBuffer image);

    /**
     * Save an image into a file.
     * 
     * @param image The image to save.
     * @param media The output media.
     * @throws LionEngineException If an error occurred when saving the image.
     */
    void saveImage(ImageBuffer image, Media media);

    /**
     * Get raster buffer from data.
     * 
     * @param image The image buffer.
     * @param fr The first red.
     * @param fg The first green.
     * @param fb The first blue.
     * @param er The end red.
     * @param eg The end green.
     * @param eb The end blue.
     * @param refSize The reference size.
     * @return The rastered image.
     */
    ImageBuffer getRasterBuffer(ImageBuffer image, int fr, int fg, int fb, int er, int eg, int eb, int refSize);
}
