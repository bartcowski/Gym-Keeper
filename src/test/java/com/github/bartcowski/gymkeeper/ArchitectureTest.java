package com.github.bartcowski.gymkeeper;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class ArchitectureTest {

    @Test
    public void domainShouldNotDependOnAppOrInfrastructure() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.github.bartcowski.gymkeeper");
        noClasses().that().resideInAPackage("..domain..")
                .should().dependOnClassesThat().resideInAnyPackage("..app..", "..infrastructure..")
                .check(importedClasses);
    }

    @Test
    public void appShouldNotDependOnInfrastructure() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.github.bartcowski.gymkeeper");
        noClasses().that().resideInAPackage("..app..")
                .should().dependOnClassesThat().resideInAPackage("..infrastructure..")
                .check(importedClasses);
    }
}
