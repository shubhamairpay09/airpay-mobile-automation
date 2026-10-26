# Scripts (mobile monorepo)

Same pattern as `airpay_automation_gitlab` — **Java + batch only** (no Python).

| Path | Purpose |
|------|---------|
| `_lib/prepare-device.bat` | Start Appium if needed, record the phone, and confirm the project app is installed |
| `_lib/run-mobile-product.bat` | Shared Maven runner (tags, env, a11y flags). Uses `@a11y` only — never `@ally`. |
| `capital/run.bat` | Run Airpay Capital functional tests |
| `capital/a11y-run.bat` | Scan Airpay Capital and write the accessibility scorecard |
| `money/run.bat` | Run airpay money functional tests |
| `money/a11y-run.bat` | Scan the logged-in airpay money app and write the accessibility scorecard |
| `vyaapaar/a11y-run.bat` | Scan airpay vyaapaar and write the accessibility scorecard |
| `vyaapaar/run.bat` | Starts `vyaapaar/a11y-run.bat` |
| `reports/open-allure.bat` | Serve Allure for `target/<product>/allure-results` |
| `reports/clean-local-artifacts.bat` | Remove leftover `airpay_*/target/` module dumps |
| `reports/clean-target-root.bat` | Remove ad-hoc `.py`/`.json`/`.xml` junk from `target/` root |

Functional and accessibility batch files start Appium when it is not already running. The chosen phone is saved at `target/<product>/device-info.txt`.
