package com.akabex86.features;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.reflections.Reflections;

/*
 * V-1.0
 */
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
        	feature.onLoad();
        	Bukkit.getLogger().info("Feature %s loaded!".formatted(feature.getClass().getSimpleName()));
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
            } else {
            	Bukkit.getLogger().log(Level.WARNING, "Failed to declare %s as feature! Did it import the Feature interface?".formatted(featureClass.getSimpleName()));
            }
        } catch (ReflectiveOperationException e) {
        	Bukkit.getLogger().log(Level.SEVERE, "Failed to instantiate feature: %s".formatted(featureClass.getName()));
        }
        return Optional.empty();
    }
    
}
