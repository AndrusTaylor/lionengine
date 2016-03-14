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
package com.b3dgs.lionengine.editor.collisioncategory.properties;

import org.eclipse.core.expressions.PropertyTester;

import com.b3dgs.lionengine.editor.project.ProjectModel;
import com.b3dgs.lionengine.editor.project.tester.ObjectsTester;
import com.b3dgs.lionengine.editor.properties.PropertiesModel;
import com.b3dgs.lionengine.game.collision.tile.CollisionCategoryConfig;
import com.b3dgs.lionengine.game.collision.tile.CollisionFormulaConfig;

/**
 * Test the properties node existence.
 */
public final class PropertiesCollisionCategoryTester extends PropertyTester
{
    /** Can add formula. */
    private static final String PROPERTY_FORMULA_ADD = "addFormula";
    /** Can remove formula. */
    private static final String PROPERTY_FORMULA_REMOVE = "removeFormula";
    /** Can edit formula. */
    private static final String PROPERTY_FORMULA_EDIT = "editFormula";

    /**
     * Check result depending of selection.
     * 
     * @param model The properties model.
     * @param data The selection reference.
     * @param property The property to check.
     * @return <code>true</code> if valid, <code>false</code> else.
     */
    private static boolean check(PropertiesModel model, Object data, String property)
    {
        final boolean result;
        if (PROPERTY_FORMULA_ADD.equals(property))
        {
            result = CollisionFormulaConfig.FORMULAS.equals(data);
        }
        else if (PROPERTY_FORMULA_REMOVE.equals(property))
        {
            result = CollisionFormulaConfig.FORMULA.equals(data);
        }
        else if (PROPERTY_FORMULA_EDIT.equals(property))
        {
            result = CollisionFormulaConfig.FORMULA.equals(data);
        }
        else if (ObjectsTester.isObjectFile(ProjectModel.INSTANCE.getSelection()))
        {
            if (PROPERTY_FORMULA_ADD.equals(property))
            {
                result = !model.hasProperty(CollisionCategoryConfig.CATEGORY);
            }
            else if (PROPERTY_FORMULA_REMOVE.equals(property) && CollisionCategoryConfig.CATEGORY.equals(data))
            {
                result = model.hasProperty(CollisionCategoryConfig.CATEGORY);
            }
            else
            {
                result = false;
            }
        }
        else
        {
            result = false;
        }
        return result;
    }

    /**
     * Create tester.
     */
    public PropertiesCollisionCategoryTester()
    {
        super();
    }

    /*
     * PropertyTester
     */

    @Override
    public boolean test(Object receiver, String property, Object[] args, Object expectedValue)
    {
        final PropertiesModel model = PropertiesModel.INSTANCE;
        if (!model.isEmpty())
        {
            final Object data = model.getSelectedData();
            return check(model, data, property);
        }
        return false;
    }
}