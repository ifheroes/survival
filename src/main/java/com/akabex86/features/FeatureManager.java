package com.akabex86.features;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.reflections.Reflections;

public class FeatureManager {

private final Set<Feature> registeredFeatures = new HashSet<>();
	
    public FeatureManager(String basePackage) {
        scanAndRegisterFeatures(basePackage);
    }

    private void scanAndRegisterFeatures(String basePackage) {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> annotatedFeatures = reflections.getTypesAnnotatedWith(FeatureComponent.class);
        
        annotatedFeatures.stream()
        	.map(this::safeInstantiateFeature)
        	.flatMap(Optional::stream)
        	.forEach(registeredFeatures::add);
    }

    public Set<Feature> getRegisteredFeatures() {
        return registeredFeatures;
    }

    public void initializeFeatures() {
        registeredFeatures.forEach(feature -> {
        	feature.registerCommands();
        	feature.registerEvents();
        });
    }
	
    public int getEntrys() {
    	return registeredFeatures.size();
    }
    
    private Optional<Feature> safeInstantiateFeature(Class<?> featureClass) {
        try {
        	Object instance = featureClass.getDeclaredConstructor().newInstance();
            if (instance instanceof Feature featureInstance) {
            	return Optional.of(featureInstance);
            }
        } catch (ReflectiveOperationException e) {
        	//TODO: Intergrate Logger
        	System.err.println("Failed to instantiate feature: " + featureClass.getName());
        }
        return Optional.empty();
    }
    
}
