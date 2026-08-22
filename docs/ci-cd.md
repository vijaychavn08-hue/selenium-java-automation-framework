# Continuous Integration (CI/CD) Pipeline

## Overview
The CI pipeline is automated using GitHub Actions (`.github/workflows/selenium-tests.yml`).

## Workflow Workflow Trigger & Execution
- Triggers on every **push** and **pull request** to `main` or `master`.
- Manually triggerable via GitHub `workflow_dispatch`.
- Steps:
  1. Clones repository.
  2. Sets up JDK 17 (Temurin) with Maven caching.
  3. Executes headless tests: `mvn -B clean test -Dheadless=true`.
  4. Uploads Surefire test reports, Cucumber HTML reports, failure screenshots, and Allure results.
