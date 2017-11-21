package com.github.vanovermeire.lambdas;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Checks whether environment variables are present. By instantiating this once, the lambda should run smoother when
 * invoked later (with the same container)
 */
public class EnvironmentChecker {

    private List<String> required;
    private Map<String, String> environmentVariables;

    private boolean everythingIsPresent;

    public EnvironmentChecker(List<String> required, Map<String, String> environmentVariables) {
        this.required = required;
        this.environmentVariables = environmentVariables;

        everythingIsPresent = areAllPresent();
    }

    private boolean areAllPresent() {
        return environmentVariables.keySet().stream()
                .filter(e -> required.contains(e)).count() == required.size();
    }

    public boolean isEverythingIsPresent() {
        return everythingIsPresent;
    }

    public Optional<String> getEnvironmentVariable(String key) {
        if(environmentVariables.containsKey(key)) {
            return Optional.of(environmentVariables.get(key));
        }

        return Optional.empty();
    }
}