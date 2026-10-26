# Config (mobile monorepo)

Same layout as `airpay_automation_gitlab` web repo.

| File | Purpose |
|------|---------|
| `defaults.properties` | Shared Appium, timeouts, a11y defaults |
| `<product>.properties` | Per-app package, APK path, test mobiles (`capital.properties`) |
| `secrets.properties` | Gitignored — UDID, OTP, scenario mobiles |
| `ci-overrides.properties` | Written by Jenkins before `mvn test` |
| `environment.template.properties` | Copy hints for `secrets.properties` |

`ConfigLoader` loads **only the active product file** (`-Dairpay.product` / module POM), not every `*.properties`.

## Capital keys (`config/capital.properties`)

| Key | Description |
|-----|-------------|
| `capital.app.package` | Android application id |
| `capital.app.apk.path` | Local APK path |
| `capital.test.otp` | Static OTP (default `1234`) |
| `capital.test.mobile.withOffers` | Account with loan offers |
| `capital.test.mobile.noOffer` | No-offer account |
| `capital.test.mobile.lowCibil` | Low CIBIL account |
| `android.udid` | Device UDID (override in secrets) |

## Money keys (`config/money.properties`)

| Key | Description |
|-----|-------------|
| `money.app.package` | Android application id (`com.finmapp.fininfinity`) |
| `money.app.activity` | Launcher activity |
| `money.app.apk.path` | Local APK (`apps/airpay_money/airpay-money-production.apk`) |
| `money.test.otp` | Static OTP (default `1234`) |
| `money.test.mobile` | Optional fixed login mobile |
