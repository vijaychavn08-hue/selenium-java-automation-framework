# Test Execution Reference

## Prerequisites
- JDK 17 (LTS)
- Apache Maven 3.8+
- Google Chrome or Mozilla Firefox

## Maven Execution Commands

### 1. Run Complete Test Suite (Headless)
```bash
mvn clean test -Dheadless=true
```

### 2. Run Complete Test Suite (Visible Browser)
```bash
mvn clean test -Dheadless=false
```

### 3. Run Smoke Suite Only
```bash
mvn clean test -Dgroups=smoke -Dheadless=true
```

### 4. Run Regression Suite Only
```bash
mvn clean test -Dgroups=regression -Dheadless=true
```

### 5. Run with Firefox Browser
```bash
mvn clean test -Dbrowser=firefox -Dheadless=true
```

### 6. Generate & Open Allure Report
```bash
# Serve interactive report in browser
mvn allure:serve

# Generate static HTML report
mvn allure:report
```
