# Airpay Mobile Automation

Java 21 + Maven + Cucumber + TestNG + Appium (UiAutomator2) + Allure.

Multi-app **monorepo** — same layout as `airpay_automation_gitlab` (web): shared `utilities`, one Maven module per Android app, central `config/`, product reports under `target/<product>/`.

## Repository layout

```
airpay-mobile-automation/
├── config/                    defaults + capital.properties + secrets
├── utilities/                 shared Appium framework
├── airpay_capital/            Capital Android app (first module)
├── scripts/capital/run.bat    local runner
├── jenkins/capital/           CI pipeline
├── docs/                      ARCHITECTURE, MODULES, TAGS, ONBOARDING
└── target/capital/            logs, allure, screenshots, a11y (gitignored)
```

## Apps

| Module | Tag | Package |
|--------|-----|---------|
| `airpay_capital` | `@airpay-capital` | `com.example.airpay_capital` |
| `airpay_money` | `@airpay-money` | `com.finmapp.fininfinity` |
| `airpay_vyaapaar` | `@airpay-vyaapaar` | `com.airpay.mahaepay_aeps` |

## Prerequisites

- JDK 21, Maven 3.9+
- Appium 2+ + `uiautomator2` driver (`appium driver install uiautomator2`)
- Physical Android device (`adb devices`)
- APK path in `config/capital.properties`

## Quick start

1. Copy `config/secrets.properties.example` → `config/secrets.properties` and set `android.udid`.
2. Terminal 1: `appium`
3. Terminal 2:

```cmd
scripts\capital\run.bat
scripts\money\run.bat
```

Or Maven directly:

```cmd
mvn -pl utilities -am install -DskipTests
mvn -pl airpay_capital -am test "-Dcucumber.filter.tags=@airpay-capital and @functional"
```

## Test suites (Capital)

| Suite | Tags | Cases |
|-------|------|-------|
| Login | `@CAPITAL_001` | Mobile login + OTP |
| Functional | `@functional` | CAPITAL_001–026 (5 products × 5 scenarios) |
| A11y | `@a11y` | Page-source accessibility scan |

Edit `scripts/capital/run.bat` for `SUITE=smoke|functional|a11y` and `EXTRA_TAGS`.

**Test data:** random 10-digit mobile per run; OTP `1234` (`capital.test.otp`). Scenario-specific mobiles: `capital.test.mobile.withOffers`, `.noOffer`, `.lowCibil` in `secrets.properties`.

## Screen objects

Locators live on the matching Java `*Screen` class (`@AndroidFindBy` / `@iOSXCUITFindBy`). Not JSON, not Python, not `data/xpaths/`. Same rule as GitLab `*Page` classes.

**Capital** — `airpay_capital/.../screens/`

```
auth/  home/  apply/  product/  info/
```

**Money** — `airpay_money/.../screens/`

```
auth/  home/  profile/  calculators/  finsight/  product/  menu/
```

## Reports (GitLab layout — all under `target/<product>/`)

```
target/
├── capital/ | money/
│   ├── logs/
│   ├── screenshots/failures/
│   ├── allure-results/
│   ├── accessibility_testing_result/   ← a11y HTML + CSV + TXT (Java A11yScorecardWriter)
│   └── accessibility_email_artifact/
├── cucumber-reports/airpay_<module>/
├── surefire-reports/airpay_<module>/
└── build/<artifactId>/
```

No Python scripts. No JSON/XML locator files — locators are Java `@AndroidFindBy` in `screens/`.  
Do not add `.py`, `locators-*.json`, or `appium-*.xml` under `target/` — use `scripts\reports\clean-target-root.bat` if they reappear.

## Add another Android app

1. Copy `airpay_capital/` → `airpay_<product>/`
2. Add module to root `pom.xml`
3. Add `config/<product>.properties`
4. Add `scripts/<product>/run.bat` and `jenkins/<product>/Jenkinsfile`
5. Tag features `@airpay-<product>`

See [docs/ONBOARDING.md](docs/ONBOARDING.md) and [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md).

## Documentation

| Doc | Contents |
|-----|----------|
| [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md) | Repo tree, runtime flow, new app checklist |
| [docs/MODULES.md](docs/MODULES.md) | Maven modules and report paths |
| [docs/TAGS.md](docs/TAGS.md) | Cucumber tags and Jenkins mapping |
| [docs/ONBOARDING.md](docs/ONBOARDING.md) | First-time setup |
| [config/README.md](config/README.md) | Config keys |
