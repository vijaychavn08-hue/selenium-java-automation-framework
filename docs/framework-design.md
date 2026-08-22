# Framework Design & Patterns

## Core Design Principles

1. **Page Object Model (POM)**: Every web page encapsulates its UI elements and business actions, eliminating raw DOM queries inside test logic.
2. **Component Architecture**: Reusable UI components (`HeaderComponent`, `ProductCardComponent`, `CartItemComponent`, `NavigationComponent`) reduce code duplication on pages sharing common UI elements.
3. **Factory Pattern**: `PageFactory` and `ComponentFactory` instantiate pages and components cleanly with the active thread-local WebDriver.
4. **ThreadLocal Driver Management**: `DriverManager` ensures parallel execution safety across concurrent threads.
5. **JSON Locator Repository**: Supports loading externalized locators from JSON files (`LocatorRepository`), enabling decoupled locator management.
6. **JavaScript Element Discovery**: `JavaScriptUtil` enables DOM traversal, element metrics extraction, and JavaScript-based actions.
7. **Explicit Wait Strategy**: `WaitUtil` replaces fragile thread sleeps with robust explicit synchronization.
