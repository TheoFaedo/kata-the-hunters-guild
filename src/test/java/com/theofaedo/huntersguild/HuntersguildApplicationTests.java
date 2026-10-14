package com.theofaedo.huntersguild;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;

@SpringBootTest
class HuntersguildApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void servicesShouldNotDependOnControllers() {
        JavaClasses classes = new ClassFileImporter()
                .importPackages("com.theofaedo.huntersguild");

        noClasses()
                .that().resideInAPackage("..service..")
                .should().dependOnClassesThat().resideInAPackage("..controllers..")
                .check(classes);

    }
}
