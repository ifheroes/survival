package com.akabex86.features;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

public class FeatureManager {

	private final Map<Class<?>, Feature> registeredFeatures = new HashMap<>();

    public FeatureManager(String basePackage) {
        scanAndRegisterFeatures(basePackage);
    }

    private void scanAndRegisterFeatures(String basePackage) {
        Reflections reflections = new Reflections(basePackage);

        Set<Class<? extends Feature>> featureClasses = reflections.getSubTypesOf(Feature.class);
        Set<Class<?>> annotatedFeatures = reflections.getTypesAnnotatedWith(FeatureComponent.class);
        
        Set<Class<?>> allFeatures = new HashSet<>();
        
        allFeatures.addAll(featureClasses);
        allFeatures.addAll(annotatedFeatures);

        for (Class<?> featureClass : allFeatures) {
            try {
                Object instance = featureClass.getDeclaredConstructor().newInstance();
                if (instance instanceof Feature) {
                    registeredFeatures.put(featureClass, (Feature) instance);
                }
            } catch (Exception e) {
                System.err.println("Failed to instantiate feature: " + featureClass.getName());
                e.printStackTrace();
            }
        }
    }

    public List<Feature> getRegisteredFeatures() {
        return new ArrayList<>(registeredFeatures.values());
    }

    public void initializeFeatures() {
        registeredFeatures.values().forEach(feature -> {
        	feature.registerCommands();
        	feature.registerEvents();
        });
    }
	
}
