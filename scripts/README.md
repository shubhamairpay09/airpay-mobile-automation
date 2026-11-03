# Scripts

Each app has its own accessibility bat and its own functional bat.

| App | Accessibility | Functional |
|-----|----------------|------------|
| Money | `scripts\money\a11y.bat` | `scripts\money\functional.bat` |
| Capital | `scripts\capital\a11y.bat` | `scripts\capital\functional.bat` |
| Vyaapaar | `scripts\vyaapaar\a11y.bat` | none (no functional suite) |

Optional first argument is a phone serial when more than one phone is plugged in.

`_lib` is shared setup (Java, Maven, Android SDK, Appium). It is not a third test runner.

Set these with no quotes, then open a new CMD: `JAVA_HOME`, `M2_HOME` (or `mvn` on PATH), `ANDROID_HOME` and `ANDROID_SDK_ROOT`. Install Appium and `appium driver install uiautomator2`.
