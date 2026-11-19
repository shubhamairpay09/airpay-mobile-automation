# Scripts

Each app has its own accessibility bat and its own functional bat.

| App | Accessibility | Functional |
|-----|----------------|------------|
| Money | `scripts\money\a11y.bat` | `scripts\money\functional.bat` |
| Capital | `scripts\capital\a11y.bat` | `scripts\capital\functional.bat` |
| Vyaapaar | `scripts\vyaapaar\a11y.bat` | none (no functional suite) |

Optional first argument is a phone serial when more than one phone is plugged in.

`_lib` is shared setup. It is not a third test runner.

Each bat checks JDK 21 or newer, Maven, Android platform-tools, build-tools, Node.js, Appium, and the UiAutomator2 driver. `C:\Program Files\Java\jdk-27.0.1.0.3` is used when it is installed. Anything missing is downloaded into `%LOCALAPPDATA%\airpay-mobile-tools`. Appium is started when nothing is listening on `http://127.0.0.1:4723`. Set `RESTART_APPIUM=Yes` to replace an Appium that was started before `ANDROID_HOME` existed.
