package org.oxerr.viagogo.client.cached.redisson;

import java.lang.reflect.Method;
import java.util.Random;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
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
			) {
				log.info("Processing: {}", clazz::getName);

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
								Random.class,
								new Random(1),
								new Random(1)
							)
							.suppress(
								Warning.NONFINAL_FIELDS,
								Warning.STRICT_INHERITANCE,
								Warning.BIGDECIMAL_EQUALITY
							)
							.verify();
					} else {
						log.warn("{}", clazz);
					}
				} catch (Exception e) {
					log.error("Failed to verify class: {}", clazz, e);
				}
			}
		}
	}

}
