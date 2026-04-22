# SauceDemo Automation Framework with AI Helpers

This is a Selenium + TestNG framework for SauceDemo with two practical AI-style features:

1. **Self-healing locators** using ordered fallback locator strategies.
2. **AI failure summary** using smart rule-based diagnosis and artifact generation.

## Stack
- Java 17
- Selenium 4
- TestNG
- WebDriverManager
- Maven

## Why this design?
A lot of self-healing frameworks need proxy servers, Docker, or extra infrastructure. This project keeps the first version simple and interview-friendly while still showing the idea clearly.

## Features
### 1) Self-healing locators
Each important UI element is searched using:
- a primary locator
- fallback locators
- healing report generation when fallback succeeds

Example from `LoginPage`:
```java
healingEngine.findElement(
    "Login Button",
    healingEngine.candidates(
        By.id("login-button"),
        By.cssSelector("input[data-test='login-button']"),
        By.xpath("//input[@type='submit']")
    )
).click();
```

If the primary locator breaks and a fallback works, the framework stores a healing report.

### 2) AI failure summary
On test failure, the listener:
- captures screenshot
- classifies the failure
- writes a human-friendly summary to `test-output/ai-summary`

It currently detects categories like:
- Locator Failure
- Sync Issue
- Assertion Failure
- DOM Refresh Issue
- General UI Failure

## Project structure
```text
src/main/java/com/rishabh/framework
├── ai
├── base
├── driver
├── listeners
├── pages
└── utils

src/test/java/com/rishabh/framework/tests
```

## Run tests
```bash
mvn clean test
```

## Run in headless mode
Update `src/main/resources/config.properties`:
```properties
headless=true
```

## Output
After execution, check:
- `test-output/screenshots`
- `test-output/ai-summary`

## Next upgrades
### Real AI failure summarization
- OpenAI / Azure OpenAI / Gemini API integration
- send stack trace + DOM snippet + last action + screenshot path
- receive summarized root cause and suggested fix

### Advanced self-healing
- Healenium
- similarity scoring using DOM attributes
- historical locator memory in JSON/DB
- auto-updating page object suggestions

## Primary references
- Selenium Page Object Model: https://www.selenium.dev/documentation/test_practices/encouraged/page_object_models/
- Selenium locators: https://www.selenium.dev/documentation/webdriver/elements/locators/
- Selenium waits: https://www.selenium.dev/documentation/webdriver/waits/
- TestNG docs: https://testng.org/
- WebDriverManager: https://github.com/bonigarcia/webdrivermanager
- Healenium overview: https://healenium.io/docs/overview
