package com.github.vanovermeire.lambdas;

import com.google.common.collect.Lists;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EnvironmentCheckerTest {

    private EnvironmentChecker environmentChecker;

    @Test
    public void testNoRequiredValueNoEnvVars() {
        ArrayList<String> required = Lists.newArrayList();
        Map<String, String> env = new HashMap<>();

        environmentChecker = new EnvironmentChecker(required, env);

        assertThat(environmentChecker.isEverythingIsPresent()).isTrue();
    }

    @Test
    public void testRequiredValueButNoEnvVars() {
        ArrayList<String> required = Lists.newArrayList("requiredEnvVar");
        Map<String, String> env = new HashMap<>();

        environmentChecker = new EnvironmentChecker(required, env);

        assertThat(environmentChecker.isEverythingIsPresent()).isFalse();
    }

    @Test
    public void testRequiredValueAndEnvVar() {
        ArrayList<String> required = Lists.newArrayList("requiredEnvVar");
        Map<String, String> env = new HashMap<>();
        env.put("requiredEnvVar", "hasAValue");

        environmentChecker = new EnvironmentChecker(required, env);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(environmentChecker.isEverythingIsPresent()).isTrue();
        softly.assertThat(environmentChecker.getEnvironmentVariable("requiredEnvVar")).contains("hasAValue");
        softly.assertAll();
    }

    @Test
    public void testRequiredValueAndMultipleEnvVar() {
        ArrayList<String> required = Lists.newArrayList("requiredEnvVar");
        Map<String, String> env = new HashMap<>();
        env.put("requiredEnvVar", "hasAValue");
        env.put("anotherVar", "someValue");

        environmentChecker = new EnvironmentChecker(required, env);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(environmentChecker.isEverythingIsPresent()).isTrue();
        softly.assertThat(environmentChecker.getEnvironmentVariable("requiredEnvVar")).contains("hasAValue");
        softly.assertAll();
    }

    @Test
    public void testMulitpleRequiredValuesAndMultipleEnvVar() {
        ArrayList<String> required = Lists.newArrayList("requiredEnvVar", "anotherRequired");
        Map<String, String> env = new HashMap<>();
        env.put("requiredEnvVar", "hasAValue");
        env.put("anotherVar", "someValue");
        env.put("anotherRequired", "anotherValue");

        environmentChecker = new EnvironmentChecker(required, env);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(environmentChecker.isEverythingIsPresent()).isTrue();
        softly.assertThat(environmentChecker.getEnvironmentVariable("requiredEnvVar")).contains("hasAValue");
        softly.assertThat(environmentChecker.getEnvironmentVariable("anotherRequired")).contains("anotherValue");
        softly.assertAll();
    }
}
