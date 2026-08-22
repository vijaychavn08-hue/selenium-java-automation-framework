# Advanced Selenium Java Automation Framework

![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)
![Selenium](https://img.shields.io/badge/Selenium%20WebDriver-4.25.0-43B02A?logo=selenium)
![Maven](https://img.shields.io/badge/Maven-3.8+-C71A36?logo=apachemaven)
![TestNG](https://img.shields.io/badge/TestNG-7.10.2-red)
![Cucumber](https://img.shields.io/badge/Cucumber%20BDD-7.18.1-brightgreen?logo=cucumber)
![Allure](https://img.shields.io/badge/Allure-2.29.0-blue?logo=qameta)
![CI Pipeline](https://img.shields.io/badge/CI-GitHub%20Actions-2088FF?logo=githubactions)
![License](https://img.shields.io/badge/License-MIT-yellow)

A production-grade, portfolio-ready web UI test automation framework built using **Java 17**, **Selenium WebDriver 4**, **TestNG**, **Cucumber BDD**, **Page Object Model**, **Component Architecture**, **Factory Pattern**, **JSON Locator Repository**, **JavaScript Element Discovery**, **Allure Reporting**, and **GitHub Actions**.

---

## 🌟 Framework Highlights

- **Clean Layered Architecture**: Strict separation of concerns between Drivers, Configuration, Page Objects, UI Components, Step Definitions, and Test Runners.
- **ThreadLocal Driver Management**: Thread-safe browser lifecycle management supporting Chrome and Firefox with seamless headless mode toggling.
- **Reusable UI Component Architecture**: Encapsulates common UI elements (`HeaderComponent`, `ProductCardComponent`, `CartItemComponent`, `NavigationComponent`) into modular component objects.
- **Factory Pattern**: `PageFactory` and `ComponentFactory` provide dynamic, typed page and component object instantiation.
- **JSON Locator Repository**: Demonstrates an externalized JSON locator strategy (`LocatorRepository`) alongside standard `By` locators.
- **JavaScript Element Discovery**: Reusable `JavaScriptUtil` capable of executing external JavaScript files to extract dynamic DOM metrics and product catalogs directly from the browser.
- **Hybrid Test Orchestration**: Supports both structured **TestNG** suites (`@Test(groups={"smoke","regression"})`) and human-readable **Cucumber BDD** Gherkin scenarios.
- **Automatic Failure Screenshot Capture**: Automatically captures and attaches screenshots to Allure reports whenever a test or scenario fails.
- **Zero Logging Noise**: Integrated with SLF4J Simple Logger for clean, structured console output without SLF4J binding warnings.
- **Automated CI/CD**: Pre-configured GitHub Actions workflow (`.github/workflows/selenium-tests.yml`) executing tests in headless mode and archiving test results, screenshots, and Allure artifacts.

---

## 🏛️ Architecture

```
+-----------------------------------------------------------------------------------+
|                                Test Execution Layer                               |
|  +--------------------------------+       +------------------------------------+  |
|  |     TestNG Tests               |       |     Cucumber BDD Features          |  |
|  |  (Login, Product, Cart, JS)    |       |  (01-login to 05-negative-login)   |  |
|  +--------------------------------+       +------------------------------------+  |
+------------------------------------------+----------------------------------------+
                                           |
                                           v
+-----------------------------------------------------------------------------------+
|                                 Factory Layer                                     |
|  +--------------------------------+       +------------------------------------+  |
|  |        PageFactory             |       |        ComponentFactory            |  |
|  +--------------------------------+       +------------------------------------+  |
+------------------------------------------+----------------------------------------+
                                           |
                                           v
+-----------------------------------------------------------------------------------+
|                               Page & Component Layer                              |
|  +--------------------------------+       +------------------------------------+  |
|  |           BasePage             |       |          BaseComponent             |  |
|  |  (LoginPage, InventoryPage,    |       |  (HeaderComponent, ProductCard,   |  |
|  |   CartPage, CheckoutPage, etc.)|       |   CartItem, NavigationComponent)   |  |
|  +--------------------------------+       +------------------------------------+  |
+------------------------------------------+----------------------------------------+
                                           |
                                           v
+-----------------------------------------------------------------------------------+
|                        Core Engine, Drivers & Utilities                           |
|  +---------------------+   +---------------------+   +-------------------------+  |
|  |    DriverManager    |   |      WaitUtil       |   |    LocatorRepository    |  |
|  | (ThreadLocal Driver)|   | (Explicit / Fluent) |   |    (JSON Locators)      |  |
|  +---------------------+   +---------------------+   +-------------------------+  |
|  |   JavaScriptUtil    |   |   ScreenshotUtil    |   |        JsonUtil         |  |
|  | (DOM Discovery / JS)|   | (Allure Attachments)|   |      (Jackson Data)     |  |
|  +---------------------+   +---------------------+   +-------------------------+  |
+-----------------------------------------------------------------------------------+
```

---

## 🛠️ Technology Stack

| Technology | Purpose | Version |
| :--- | :--- | :--- |
| **Java** | Programming Language | 17 (LTS) |
| **Selenium WebDriver** | Browser Automation Library | 4.25.0 |
| **Apache Maven** | Build & Dependency Management | 3.8+ |
| **TestNG** | Test Execution & Grouping Framework | 7.10.2 |
| **Cucumber JVM** | Behavior-Driven Development Framework | 7.18.1 |
| **Jackson** | JSON Data Binding & Locator Repository | 2.18.1 |
| **Allure Framework** | Interactive Test Reporting & Attachments | 2.29.0 |
| **SLF4J Simple** | Clean Structured Logging | 1.7.36 |
| **GitHub Actions** | Automated Continuous Integration | v4 Actions |

---

## 📂 Project Structure

```
selenium-java-automation-framework/
├── .github/
│   └── workflows/
│       └── selenium-tests.yml        # GitHub Actions CI pipeline
├── docs/
│   ├── architecture.md               # Architecture details
│   ├── framework-design.md           # Design patterns guide
│   ├── execution.md                  # Test execution reference
│   └── ci-cd.md                      # CI/CD documentation
├── src/
│   ├── main/
│   │   ├── java/com/vijaychavan/
│   │   │   ├── components/           # Reusable UI Components
│   │   │   │   ├── CartItemComponent.java
│   │   │   │   ├── HeaderComponent.java
│   │   │   │   ├── NavigationComponent.java
│   │   │   │   └── ProductCardComponent.java
│   │   │   ├── framework/
│   │   │   │   ├── base/
│   │   │   │   │   ├── BaseComponent.java
│   │   │   │   │   ├── BasePage.java
│   │   │   │   │   └── BaseTest.java
│   │   │   │   ├── config/
│   │   │   │   │   └── Config.java
│   │   │   │   ├── driver/
│   │   │   │   │   └── DriverManager.java
│   │   │   │   ├── factories/
│   │   │   │   │   ├── ComponentFactory.java
│   │   │   │   │   └── PageFactory.java
│   │   │   │   ├── javascript/
│   │   │   │   │   └── JavaScriptUtil.java
│   │   │   │   └── utils/
│   │   │   │       ├── JsonUtil.java
│   │   │   │       ├── LocatorRepository.java
│   │   │   │       ├── ScreenshotUtil.java
│   │   │   │       └── WaitUtil.java
│   │   │   └── pages/                # Page Object Model classes
│   │   │       ├── CartPage.java
│   │   │       ├── CheckoutCompletePage.java
│   │   │       ├── CheckoutPage.java
│   │   │       ├── InventoryPage.java
│   │   │       ├── LoginPage.java
│   │   │       └── ProductDetailsPage.java
│   │   └── resources/
│   │       ├── config.properties     # Environment configuration
│   │       └── locators/             # JSON locator files
│   │           ├── cart.json
│   │           ├── checkout.json
│   │           ├── inventory.json
│   │           └── login.json
│   └── test/
│       ├── java/com/vijaychavan/
│       │   ├── framework/
│       │   │   └── runner/
│       │   │       └── CucumberTestRunner.java  # Cucumber TestNG runner
│       │   ├── steps/                           # Cucumber Step Definitions
│       │   │   ├── CartSteps.java
│       │   │   ├── CheckoutSteps.java
│       │   │   ├── Hooks.java
│       │   │   ├── LoginSteps.java
│       │   │   └── ProductSteps.java
│       │   └── tests/                           # Direct TestNG Test Classes
│       │       ├── CartCheckoutTest.java
│       │       ├── JavaScriptDiscoveryTest.java
│       │       ├── LoginTest.java
│       │       └── ProductCatalogTest.java
│       └── resources/
│           ├── features/                        # 5 Executable Feature files
│           │   ├── 01-login.feature
│           │   ├── 02-products.feature
│           │   ├── 03-cart.feature
│           │   ├── 04-checkout.feature
│           │   └── 05-negative-login.feature
│           ├── js/                              # External JavaScript discovery scripts
│           │   ├── dynamicElements.js
│           │   └── productDiscovery.js
│           ├── testdata/                        # Test data files
│           │   ├── checkout-data.json
│           │   └── login-data.json
│           └── simplelogger.properties          # SLF4J logging configuration
├── .gitignore                                   # Git ignored files & directories
├── LICENSE                                      # MIT License
├── pom.xml                                      # Maven project configuration
├── README.md                                    # Portfolio documentation
└── testng.xml                                   # TestNG suite configuration
```

---

## 🎯 Test Coverage

### 1. Cucumber BDD Feature Files (5 Files)

| Feature File | Scenarios Covered | Tags |
| :--- | :--- | :--- |
| **`01-login.feature`** | Standard login, header verification, logout workflow | `@login`, `@smoke`, `@regression` |
| **`02-products.feature`** | Product count, sorting (Name A-Z, Z-A, Price Low-High, High-Low), product details navigation | `@products`, `@smoke`, `@regression` |
| **`03-cart.feature`** | Add product to cart, shopping cart badge count, remove from inventory, remove from cart page | `@cart`, `@smoke`, `@regression` |
| **`04-checkout.feature`** | End-to-end checkout purchase, required field validations (First Name, Last Name, Postal Code) | `@checkout`, `@smoke`, `@regression` |
| **`05-negative-login.feature`** | Locked out user, invalid credentials, empty username, empty password validations | `@login`, `@negative`, `@smoke`, `@regression` |

### 2. Direct TestNG Test Classes

* **`LoginTest.java`**: Positive authentication, locked-out user verification, invalid credentials, and field validations.
* **`ProductCatalogTest.java`**: Product catalog rendering, sorting options, and details page round-trip navigation.
* **`CartCheckoutTest.java`**: Cart badge lifecycle, full checkout completion, and customer form validations.
* **`JavaScriptDiscoveryTest.java`**: External JavaScript-driven DOM element inspection and catalog extraction.

---

## 🚀 Getting Started

### Prerequisites
- **JDK 17** or higher installed and configured in `JAVA_HOME`.
- **Apache Maven 3.8+** installed and configured in `PATH`.
- Google Chrome or Mozilla Firefox browser installed.

### Installation
Clone the repository:
```bash
git clone https://github.com/vijaychavn08-hue/selenium-java-automation-framework.git
cd selenium-java-automation-framework
```

---

## 💻 Test Execution

### 1. Run All Tests (Headless Mode)
```bash
mvn clean test -Dheadless=true
```

### 2. Run All Tests (Visible Browser Mode)
```bash
mvn clean test -Dheadless=false
```

### 3. Run Smoke Suite
```bash
mvn clean test -Dgroups=smoke -Dheadless=true
```

### 4. Run Regression Suite
```bash
mvn clean test -Dgroups=regression -Dheadless=true
```

### 5. Run with Firefox Browser
```bash
mvn clean test -Dbrowser=firefox -Dheadless=true
```

---

## 📊 Allure Test Reporting

Generate and view the interactive Allure HTML report:
```bash
# Serve interactive report directly in browser
mvn allure:serve

# Or generate static HTML report in target/site/allure-maven-plugin/
mvn allure:report
```

Allure results are automatically generated in `target/allure-results/` including failure screenshots, test steps, and scenario execution history.

---

## 🔄 CI/CD Pipeline (GitHub Actions)

This repository includes an automated GitHub Actions workflow in `.github/workflows/selenium-tests.yml`.
On every **push** or **pull request** to `main` or `master`:
1. Checks out repository.
2. Configures JDK 17 with Maven caching.
3. Executes `mvn -B clean test -Dheadless=true`.
4. Uploads Surefire test reports, Cucumber HTML reports, failure screenshots, and Allure results as build artifacts.

---

## ⚠️ Portfolio Disclaimer

This framework executes automated tests against the public [SauceDemo](https://www.saucedemo.com/) e-commerce test website. All credentials used (`standard_user`, `secret_sauce`, `locked_out_user`) are publicly published demo test accounts. No proprietary client code, private credentials, or confidential business data is present in this repository.

---

## 👤 Author

**Vijay Chavan**
- **Role**: Senior Automation QA Engineer | SDET | Automation Lead
- **LinkedIn**: [linkedin.com/in/vijaychavhan08/](https://www.linkedin.com/in/vijaychavhan08/)
- **GitHub**: [github.com/vijaychavn08-hue](https://github.com/vijaychavn08-hue)
