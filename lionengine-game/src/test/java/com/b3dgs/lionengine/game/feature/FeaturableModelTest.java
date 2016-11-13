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
package com.b3dgs.lionengine.game.feature;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.b3dgs.lionengine.Constant;
import com.b3dgs.lionengine.LionEngineException;
import com.b3dgs.lionengine.Media;
import com.b3dgs.lionengine.core.Medias;
import com.b3dgs.lionengine.stream.Xml;
import com.b3dgs.lionengine.stream.XmlNode;
import com.b3dgs.lionengine.util.UtilReflection;

/**
 * Test the featurable model class.
 */
public class FeaturableModelTest
{
    /**
     * Prepare test.
     */
    @BeforeClass
    public static void setUp()
    {
        Medias.setResourcesDirectory(System.getProperty("java.io.tmpdir"));
    }

    /**
     * Clean up test.
     */
    @AfterClass
    public static void cleanUp()
    {
        Medias.setResourcesDirectory(Constant.EMPTY_STRING);
    }

    /**
     * Test the featurable model with compatible feature.
     */
    @Test
    public void testFeaturableCompatible()
    {
        final Featurable featurable = new FeaturableModel();
        final MyFeatureInterface feature = new MyFeature();
        featurable.addFeature(feature);

        Assert.assertTrue(featurable.hasFeature(MyFeatureInterface.class));
        Assert.assertTrue(featurable.hasFeature(MyFeature.class));

        featurable.prepareFeatures(new Services());

        Assert.assertEquals(feature, featurable.getFeature(MyFeature.class));
        for (final Feature current : featurable.getFeatures())
        {
            Assert.assertEquals(feature, current);
        }
        for (final Class<? extends Feature> type : featurable.getFeaturesType())
        {
            Assert.assertTrue(MyFeatureInterface.class.isAssignableFrom(type));
        }
    }

    /**
     * Test the featurable model with not compatible interface.
     */
    @Test
    public void testFeaturableNotcompatible()
    {
        final Featurable featurable = new FeaturableModel();
        final MyFeatureNotCompatible feature = new MyFeatureNotCompatible();
        featurable.addFeature(feature);

        Assert.assertTrue(featurable.hasFeature(MyFeatureNotCompatible.class));

        featurable.prepareFeatures(new Services());

        Assert.assertEquals(feature, featurable.getFeature(MyFeatureNotCompatible.class));
        for (final Feature current : featurable.getFeatures())
        {
            Assert.assertEquals(feature, current);
        }
        for (final Class<? extends Feature> type : featurable.getFeaturesType())
        {
            Assert.assertTrue(MyFeatureNotCompatible.class.isAssignableFrom(type));
        }
    }

    /**
     * Test the service with annotation.
     */
    @Test
    public void testServiceAnnotation()
    {
        final Featurable featurable = new FeaturableModel();
        final AtomicReference<Factory> filledService = new AtomicReference<Factory>();
        final AtomicReference<Object> filledObject = new AtomicReference<Object>();
        final AtomicReference<MyFeatureInterface> filledFeature = new AtomicReference<MyFeatureInterface>();
        final Feature feature = new FeatureModel()
        {
            private @Service Factory factory;
            private @Service Object object;
            private @Service MyFeatureInterface feature;

            @Override
            public void prepare(FeatureProvider provider, Services services)
            {
                super.prepare(provider, services);
                filledService.set(factory);
                filledObject.set(object);
                filledFeature.set(feature);
            }
        };
        featurable.addFeature(feature);

        final Services services = new Services();
        final Factory factory = services.create(Factory.class);
        final MyFeatureInterface featureModel = new MyFeature();
        featurable.addFeature(featureModel);
        featurable.prepareFeatures(services);

        Assert.assertEquals(factory, filledService.get());
        Assert.assertEquals(factory, filledObject.get());
        Assert.assertEquals(featureModel, filledFeature.get());
    }

    /**
     * Test the service with annotation twice loaded.
     */
    @Test
    public void testServiceAnnotationTwice()
    {
        final Featurable featurable = new FeaturableModel();
        final AtomicReference<Factory> filledService = new AtomicReference<Factory>();
        final Feature feature = new FeatureModel()
        {
            private @Service Factory factory;

            @Override
            public void prepare(FeatureProvider provider, Services services)
            {
                super.prepare(provider, services);
                filledService.set(factory);
            }
        };
        featurable.addFeature(feature);

        final Services services = new Services();
        final Factory factory = services.create(Factory.class);
        final MyFeatureInterface featureModel = new MyFeature();
        featurable.addFeature(featureModel);

        featurable.prepareFeatures(services);

        Assert.assertEquals(factory, filledService.get());

        filledService.set(null);

        featurable.addFeature(feature);
        featurable.prepareFeatures(services);

        Assert.assertEquals(factory, filledService.get());
    }

    /**
     * Test the service with annotation and service not found.
     */
    @Test(expected = LionEngineException.class)
    public void testServiceAnnotationNotFound()
    {
        final Featurable featurable = new FeaturableModel();
        final AtomicReference<String> unfilledType = new AtomicReference<String>();
        final Feature feature = new FeatureModel()
        {
            private @Service String type;

            @Override
            public void prepare(FeatureProvider provider, Services services)
            {
                super.prepare(provider, services);
                unfilledType.set(type);
            }
        };
        featurable.addFeature(feature);
        featurable.prepareFeatures(new Services());
    }

    /**
     * Test the feature itself.
     */
    @Test
    public void testFeatureItself()
    {
        final FeatureItself object = new FeatureItself();
        object.prepare(object, new Services());

        Assert.assertFalse(object.hasFeature(FeatureItself.class));
    }

    /**
     * Test the set field not accessible
     * 
     * @throws Throwable If error.
     */
    @Test(expected = LionEngineException.class)
    public void testSetFieldNotAccessible() throws Throwable
    {
        final FeatureItself featurable = new FeatureItself();
        try
        {
            final Method method = FeaturableModel.class.getDeclaredMethod("setField",
                                                                          Field.class,
                                                                          Object.class,
                                                                          Services.class,
                                                                          Class.class);
            UtilReflection.setAccessible(method, true);
            method.invoke(featurable,
                          featurable.getClass().getDeclaredField("object"),
                          featurable,
                          new Services(),
                          Object.class);
        }
        catch (final InvocationTargetException exception)
        {
            Assert.assertTrue(exception.getCause().getCause() instanceof IllegalAccessException);
            throw exception.getCause();
        }
    }

    /**
     * Test the add features
     */
    @Test
    public void testAddFeatures()
    {
        final Media media = Medias.create("Features.xml");

        final XmlNode root = Xml.create(FeaturableConfig.NODE_FEATURABLE);
        final XmlNode unknown = root.createChild(FeaturableConfig.NODE_FEATURE);
        unknown.setText(MyFeature.class.getName());

        Xml.save(root, media);

        final Featurable featurable = new FeaturableModel();
        featurable.addFeatures(new Setup(media));

        Assert.assertEquals(MyFeature.class.getName(), featurable.getFeatures().iterator().next().getClass().getName());
    }

    /**
     * Mock feature.
     */
    private static interface MyFeatureInterface extends Feature
    {
        // Mock
    }

    /**
     * Mock feature.
     */
    private static class MyFeature extends FeatureModel implements MyFeatureInterface
    {
        // Mock
    }

    /**
     * Mock feature.
     */
    private static class MyFeatureNotCompatible extends FeatureModel implements Serializable
    {
        private static final long serialVersionUID = 1L;
    }

    /**
     * Mock feature itself.
     */
    private static class FeatureItself extends FeaturableModel implements Feature
    {
        @SuppressWarnings("unused") private Object object;

        @Override
        public void prepare(FeatureProvider provider, Services services)
        {
            // Mock
        }

        @Override
        public void checkListener(Object listener)
        {
            // Mock
        }
    }
}
