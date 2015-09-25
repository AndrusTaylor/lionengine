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
package com.b3dgs.lionengine;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Utility class related to java reflection.
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 */
public final class UtilReflection
{
    /** Constructor error. */
    private static final String ERROR_CONSTRUCTOR = "Unable to create the following type: ";
    /** Field error. */
    private static final String ERROR_FIELD = "Unable to access to the following field: ";
    /** Method error. */
    private static final String ERROR_METHOD = "Unable to access to the following method: ";

    /**
     * Create a class instance with its parameters.
     * 
     * @param <T> The element type used.
     * @param type The class type to instantiate.
     * @param paramTypes The class base type for each parameter.
     * @param params The constructor parameters.
     * @return The class instance.
     * @throws NoSuchMethodException If no constructor found.
     * @throws LionEngineException If unable to create the instance or type is <code>null</code>.
     */
    public static <T> T create(Class<?> type, Class<?>[] paramTypes, Object... params)
            throws NoSuchMethodException, LionEngineException
    {
        Check.notNull(type);
        try
        {
            final Constructor<?> constructor = getCompatibleConstructor(type, paramTypes);
            final boolean accessible = constructor.isAccessible();
            setAccessible(constructor, true);
            @SuppressWarnings("unchecked")
            final T object = (T) constructor.newInstance(params);
            if (constructor.isAccessible() != accessible)
            {
                setAccessible(constructor, accessible);
            }
            return object;
        }
        catch (final NoSuchMethodException exception)
        {
            throw exception;
        }
        catch (final IllegalArgumentException exception)
        {
            throw new LionEngineException(exception, ERROR_CONSTRUCTOR + type);
        }
        catch (final InstantiationException exception)
        {
            throw new LionEngineException(exception, ERROR_CONSTRUCTOR + type);
        }
        catch (final IllegalAccessException exception)
        {
            throw new LionEngineException(exception, ERROR_CONSTRUCTOR + type);
        }
        catch (final InvocationTargetException exception)
        {
            throw new LionEngineException(exception, ERROR_CONSTRUCTOR + type);
        }
    }

    /**
     * Get the parameter types as array.
     * 
     * @param arguments The arguments list.
     * @return The arguments type array.
     */
    public static Class<?>[] getParamTypes(Object... arguments)
    {
        final Collection<Object> types = new ArrayList<Object>();
        for (final Object argument : arguments)
        {
            types.add(argument.getClass());
        }
        final Class<?>[] typesArray = new Class<?>[types.size()];
        return types.toArray(typesArray);
    }

    /**
     * Get a compatible constructor with the following parameters.
     * 
     * @param type The class type.
     * @param paramTypes The parameters types.
     * @return The constructor found.
     * @throws NoSuchMethodException If no constructor found.
     */
    public static Constructor<?> getCompatibleConstructor(Class<?> type, Class<?>[] paramTypes)
            throws NoSuchMethodException
    {
        for (final Constructor<?> current : type.getDeclaredConstructors())
        {
            final Class<?>[] constructorTypes = current.getParameterTypes();
            if (constructorTypes.length == paramTypes.length)
            {
                boolean found = true;
                for (int i = 0; i < paramTypes.length; i++)
                {
                    if (!constructorTypes[i].isAssignableFrom(paramTypes[i]))
                    {
                        found = false;
                        break;
                    }
                }
                if (found)
                {
                    return current;
                }
            }
        }
        throw new NoSuchMethodException("No compatible constructor found for "
                                        + type.getName()
                                        + " with: "
                                        + Arrays.asList(paramTypes));
    }

    /**
     * Get method and call its return value with parameters.
     * 
     * @param <T> The object type.
     * @param object The object caller.
     * @param name The method name.
     * @param params The method parameters.
     * @return The value returned.
     */
    public static <T> T getMethod(Object object, String name, Object... params)
    {
        Check.notNull(object);
        Check.notNull(name);
        try
        {
            final Class<?> clazz = getClass(object);
            final Method method = clazz.getDeclaredMethod(name, getParamTypes(params));
            final boolean accessible = method.isAccessible();
            if (!accessible)
            {
                setAccessible(method, true);
            }
            @SuppressWarnings("unchecked")
            final T value = (T) method.invoke(object, params);
            if (method.isAccessible() != accessible)
            {
                setAccessible(method, accessible);
            }
            return value;
        }
        catch (final NoSuchMethodException exception)
        {
            throw new LionEngineException(exception, ERROR_METHOD, name);
        }
        catch (final IllegalArgumentException exception)
        {
            throw new LionEngineException(exception, ERROR_METHOD, name);
        }
        catch (final IllegalAccessException exception)
        {
            throw new LionEngineException(exception, ERROR_METHOD, name);
        }
        catch (final InvocationTargetException exception)
        {
            throw new LionEngineException(exception, ERROR_METHOD, name);
        }
    }

    /**
     * Get the field by reflection.
     * 
     * @param <T> The field type.
     * @param object The object to use.
     * @param name The field name.
     * @return The field found.
     * @throws LionEngineException If field not found.
     */
    public static <T> T getField(Object object, String name) throws LionEngineException
    {
        Check.notNull(object);
        Check.notNull(name);
        try
        {
            final Class<?> clazz = getClass(object);
            final Field field = clazz.getDeclaredField(name);
            final boolean accessible = field.isAccessible();
            if (!accessible)
            {
                setAccessible(field, true);
            }
            @SuppressWarnings("unchecked")
            final T value = (T) field.get(object);
            if (field.isAccessible() != accessible)
            {
                setAccessible(field, accessible);
            }
            return value;
        }
        catch (final NoSuchFieldException exception)
        {
            throw new LionEngineException(exception, ERROR_FIELD, name);
        }
        catch (final IllegalArgumentException exception)
        {
            throw new LionEngineException(exception, ERROR_FIELD, name);
        }
        catch (final IllegalAccessException exception)
        {
            throw new LionEngineException(exception, ERROR_FIELD, name);
        }
    }

    /**
     * Set the object accessibility with an access controller.
     * 
     * @param object The accessible object.
     * @param accessible <code>true</code> if accessible, <code>false</code> else.
     */
    public static void setAccessible(final AccessibleObject object, final boolean accessible)
    {
        java.security.AccessController.doPrivileged(new java.security.PrivilegedAction<Void>()
        {
            @Override
            public Void run()
            {
                object.setAccessible(accessible);
                return null;
            }
        });
    }

    /**
     * Get the object class.
     * 
     * @param object The object reference.
     * @return The object class (or object itself if already a class).
     */
    private static Class<?> getClass(Object object)
    {
        if (object instanceof Class)
        {
            return (Class<?>) object;
        }
        return object.getClass();
    }

    /**
     * Private constructor.
     */
    private UtilReflection()
    {
        throw new LionEngineException(LionEngineException.ERROR_PRIVATE_CONSTRUCTOR);
    }
}