# Scripts (mobile monorepo)

Same pattern as `airpay_automation_gitlab` — **Java + batch only** (no Python).

| Path | Purpose |
|------|---------|
| `_lib/prepare-device.bat` | Start Appium if needed, record the phone, confirm app installed |
| `_lib/resolve-java-maven.bat` | Shared Java/Maven detection (Money + Vyaapaar). Handles quoted JAVA_HOME, `\bin` mistake, Oracle javapath |
| `_lib/run-mobile-product.bat` | Shared Maven runner (tags, env, a11y flags) |
| `capital/run.bat` | Capital functional tests |
| `capital/a11y-run.bat` | Capital accessibility scorecard |
| `money/run.bat` | Money functional tests |
| `money/a11y-run.bat` | **Only** Money a11y bat (any PC). Default = deep. Optional: `two [udid]` |
| `vyaapaar/a11y-run.bat` | **Only** Vyaapaar a11y bat (any PC). Optional: `deep` / `two` / `fromhere` `[udid]` |
| `vyaapaar/run.bat` | Starts `vyaapaar/a11y-run.bat` |
| `reports/open-allure.bat` | Serve Allure for `target/<product>/allure-results` |
| `reports/clean-local-artifacts.bat` | Remove leftover `airpay_*/target/` module dumps |
| `reports/clean-target-root.bat` | Remove ad-hoc junk from `target/` root |

Java, Maven, ADB, and Appium are resolved from env / PATH (not hard-coded to one machine).

Phone used is saved at `target/<product>/device-info.txt`.
