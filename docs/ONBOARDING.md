# Onboarding — Airpay Mobile Automation

## Prerequisites

- JDK 21, Maven 3.9+
- Appium 2+ with UiAutomator2: `appium driver install uiautomator2`
- Android device: USB debugging, `adb devices` shows `device`
- APK path set in `config/capital.properties`

## First-time setup

1. Clone repo and open at root (`airpay-mobile-automation/`).
2. Copy `config/secrets.properties.example` → `config/secrets.properties`.
3. Set `android.udid` and optional scenario mobiles in `secrets.properties`.
4. Build framework: `mvn -pl utilities -am install -DskipTests`

## Run Capital locally

Terminal 1:

```cmd
appium
```

Terminal 2:

```cmd
scripts\capital\run.bat
```

Edit knobs at the top of `scripts/capital/run.bat` (`SUITE`, `ENV`, `EXTRA_TAGS`, `CLEAN_PREVIOUS_REPORTS`).

## Reports

| Artifact | Path |
|----------|------|
| Allure | `target/capital/allure-results` → `scripts\reports\open-allure.bat capital` |
| Cucumber HTML | `target/cucumber-reports/airpay_capital/cucumber.html` |
| A11y scorecard | `target/capital/accessibility_testing_result/index.html` |
| Logs | `target/capital/logs/automation.log` |
| Failure screenshots | `target/capital/screenshots/failures/` |

## Add a second Android app

1. `cp -r airpay_capital airpay_<product>` (or copy folder in IDE).
2. Rename Maven artifact in `airpay_<product>/pom.xml`; set `<airpay.product><product></airpay.product>`.
3. Add module to root `pom.xml` `<modules>`.
4. Refactor Java package `com.airpay.capital` → `com.airpay.<product>`.
5. Create `config/<product>.properties`.
6. Create `scripts/<product>/run.bat` from `scripts/capital/run.bat`.
7. Copy `jenkins/_template/Jenkinsfile` → `jenkins/<product>/Jenkinsfile`; set `MODULE`, `PRODUCT`, `PRODUCT_TAG`.
8. Tag all features `@airpay-<product>`.

## Screen objects

Locators live in Java `screens/` classes using `@AndroidFindBy` / `@iOSXCUITFindBy` — not JSON files. Extend `BaseScreen` for shared waits and gestures.
