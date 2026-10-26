# Cucumber tags — Airpay Capital (mobile)

## Product tag (required on every feature)

| Tag | Meaning |
|-----|---------|
| `@airpay-capital` | All Capital scenarios (module default in `airpay_capital/pom.xml`) |
| `@capital` | Legacy alias — kept for backward compatibility |

## Suite tags

| Tag | Meaning |
|-----|---------|
| `@smoke` | Quick functional sanity |
| `@functional` | Full E2E product journeys (CAPITAL_001–026) |
| `@a11y` | Accessibility page-source scan (`features/a11y/`) |
| `@android` | Android-only (placeholder for future `@ios`) |
| `@regression` | Reserved for full regression when needed |

**Do not use `@ally`** — that is a legacy web-repo alias. Mobile uses **`@a11y` only**.

Smoke, functional, and regression runs automatically add `not @a11y` so deep accessibility crawls never run unless `SUITE=a11y`.

## Scenario IDs

| Tag | Scenario |
|-----|----------|
| `@CAPITAL_001` | Login with mobile number |
| `@CAPITAL_002`–`006` | Personal Loan |
| `@CAPITAL_007`–`011` | Home Loan |
| `@CAPITAL_012`–`016` | Loan Against Property |
| `@CAPITAL_017`–`021` | Gold Loan |
| `@CAPITAL_022`–`026` | Credit Card |

## Local runs

```cmd
rem Via script (recommended)
scripts\capital\run.bat

rem Or Maven directly (Appium must be running)
mvn -pl airpay_capital -am test "-Dcucumber.filter.tags=@airpay-capital and @functional"
mvn -pl airpay_capital -am test "-Dcucumber.filter.tags=@CAPITAL_001"
mvn -pl airpay_capital -am test "-Dcucumber.filter.tags=@airpay-capital and @a11y"
```

## Jenkins (`jenkins/capital/Jenkinsfile`)

| SUITE | Resolved tags |
|-------|----------------|
| `smoke` | `@airpay-capital and @smoke and not @a11y` |
| `functional` | `@airpay-capital and @functional and not @a11y` |
| `a11y` | `@airpay-capital and @a11y` |
| `custom` | `CUCUMBER_TAGS` parameter |

---

## airpay money (`airpay_money`)

| Tag | Meaning |
|-----|---------|
| `@airpay-money` | All Money scenarios (module default) |
| `@money` | Legacy alias |
| `@MONEY_001` | Login with mobile number |

```cmd
scripts\money\run.bat
mvn -pl airpay_money -am test "-Dcucumber.filter.tags=@airpay-money and @smoke"
```
