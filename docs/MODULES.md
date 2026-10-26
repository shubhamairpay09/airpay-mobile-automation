# Maven modules (mobile)

| `pom.xml` | Role |
|-----------|------|
| **Root** | Parent: dependency versions, `airpay.reports.root` → repo `target/` |
| **`utilities/pom.xml`** | Shared framework JAR (`airpay.product=utilities` for unit tests) |
| **`airpay_capital/pom.xml`** | Capital Android E2E + a11y (`airpay.product=capital`) |

## Report layout

```
target/
├── capital/                           ← per-product runtime output
│   ├── logs/
│   ├── screenshots/failures/
│   ├── allure-results/
│   └── accessibility_testing_result/
├── cucumber-reports/airpay_capital/
├── surefire-reports/airpay_capital/
└── build/<artifactId>/                ← Maven compile output
```

**Allure CLI:** installed once at repo `.allure/` (`scripts/reports/open-allure.bat capital`).

## Reactor modules

| Module | Purpose | Example run |
|--------|---------|-------------|
| `utilities` | Driver, hooks, config, a11y | `mvn -pl utilities test` |
| `airpay_capital` | Capital app (26 functional + a11y) | `scripts\capital\run.bat` |
| `airpay_money` | airpay money app (login smoke) | `scripts\money\run.bat` |

## `airpay_capital` package map

| Package | Contents |
|---------|----------|
| `screens.*` | Screen objects (`BaseScreen` → PageFactory) |
| `flows` | Multi-screen journeys |
| `steps` | Cucumber step definitions |
| `data` | Test data + UI message constants |
| `runner` | `CapitalRunner` (TestNG + Cucumber) |

Glue packages: `com.airpay.capital` + `com.airpay.mobile.utilities`.
