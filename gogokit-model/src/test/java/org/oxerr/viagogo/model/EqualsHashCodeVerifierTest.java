package org.oxerr.viagogo.model;

import java.lang.reflect.Method;
import java.time.OffsetDateTime;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.oxerr.viagogo.model.response.catalog.EmbeddedCategory;
import org.oxerr.viagogo.model.response.catalog.Event;
import org.oxerr.viagogo.model.response.catalog.Venue;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

class EqualsHashCodeVerifierTest {

	private final Logger log = LogManager.getLogger();

	@Test
	void verifyEqualsAndHashCodeForAllClasses() {
		String basePackage = this.getClass().getPackageName();

		// Use Reflections library to scan all classes under the specified package
		Reflections reflections = new Reflections(basePackage, Scanners.SubTypes.filterResultsBy(c -> true));
		Set<Class<?>> allClasses = reflections.getSubTypesOf(Object.class);

		// Iterate over all classes and verify equals and hashCode
		for (Class<?> clazz : allClasses) {
			if (!clazz.isInterface()
				&& !clazz.isEnum()
				&& !clazz.isAnnotation()
				&& !clazz.isAnonymousClass()
				&& !clazz.isMemberClass()
				&& !clazz.getName().endsWith("Test")
				&& clazz != SeatingDetail.class
				&& clazz != Venue.class
			) {
				try {
					Method equalsMethod = clazz.getMethod("equals", Object.class);
					Method hashCodeMethod = clazz.getMethod("hashCode");

					// Check if the methods are overridden from Object
					boolean overridesEquals = equalsMethod.getDeclaringClass() != Object.class;
					boolean overridesHashCode = hashCodeMethod.getDeclaringClass() != Object.class;

					// Only verify if both equals and hashCode are implemented
					if (overridesEquals && overridesHashCode) {
						log.info("Verifying: {}", clazz::getName);
						EqualsVerifier.forClass(clazz)
							.usingGetClass()
							.withPrefabValues(
								EmbeddedCategory.class,
								new EmbeddedCategory(1, "first instance", "first instance"),
								new EmbeddedCategory(2, "second instance", "second instance")
							)
							.withPrefabValues(
								Event.class,
								new Event("first instance", OffsetDateTime.now()),
								new Event("second instance", OffsetDateTime.now())
							)
							.suppress(
								Warning.NONFINAL_FIELDS,
								Warning.STRICT_INHERITANCE,
								Warning.BIGDECIMAL_EQUALITY
							)
							.verify();
					}
				} catch (Exception e) {
					log.error("Failed to verify class: {}", clazz, e);
				}
			}
		}
	}

}
