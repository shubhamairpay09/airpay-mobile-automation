@echo off
setlocal EnableDelayedExpansion
rem Airpay Vyaapaar accessibility. This is the only accessibility batch file.
rem The app session stays on the phone. Each screen is walked once.
rem This file does not delete target\vyaapaar.
rem It starts Appium itself when Appium is down, and uses whichever phone is
rem connected (set UDID=<serial> first to force one when several are attached).

cd /d "%~dp0..\.."

set "ADB=D:\Android_Studio\Sdk\platform-tools\adb.exe"
if not exist "%ADB%" set "ADB=adb"
if not defined JAVA_HOME set "JAVA_HOME=C:\Program Files\Java\jdk-21.0.11"
set "M2_HOME=C:\Tools\apache-maven-3.9.16"
set "APPIUM_URL=http://127.0.0.1:4723"
set "APPIUM_WAIT_SECS=90"
set "APP_PACKAGE=com.airpay.mahaepay_aeps"

echo.
echo === Airpay Vyaapaar accessibility ===
echo Report: target\vyaapaar\accessibility_testing_result
echo ZIP:    target\vyaapaar\accessibility_email_artifact\vyaapaar-a11y-reports.zip
echo Logs:   target\vyaapaar\logs\automation.log
echo This run does not save functional failure screenshots.
echo.

rem ---- Appium: reuse it when up, start it when down ----
powershell -NoProfile -Command "try { if ((Invoke-WebRequest -UseBasicParsing -TimeoutSec 5 %APPIUM_URL%/status).StatusCode -ne 200) { exit 1 } } catch { exit 1 }"
if errorlevel 1 (
  echo Appium is not running at %APPIUM_URL% — starting it now.
  set "APPIUM_CMD="
  for /f "delims=" %%A in ('where appium 2^>nul') do if not defined APPIUM_CMD set "APPIUM_CMD=%%A"
  if not defined APPIUM_CMD if exist "%APPDATA%\npm\appium.cmd" set "APPIUM_CMD=%APPDATA%\npm\appium.cmd"
  if not defined APPIUM_CMD (
    echo Could not find appium. Install it once with: npm install -g appium
    pause
    exit /b 1
  )
  echo Using !APPIUM_CMD!
  start "Airpay Appium" "!APPIUM_CMD!"
  echo Waiting up to %APPIUM_WAIT_SECS%s for Appium to come up...
  powershell -NoProfile -Command "$t=0; while ($t -lt %APPIUM_WAIT_SECS%) { try { if ((Invoke-WebRequest -UseBasicParsing -TimeoutSec 3 %APPIUM_URL%/status).StatusCode -eq 200) { exit 0 } } catch {}; Start-Sleep -Seconds 2; $t+=2 }; exit 1"
  if errorlevel 1 (
    echo Appium did not come up in time. Check the Appium window, then rerun this file.
    pause
    exit /b 1
  )
  echo Appium is up.
) else (
  echo Appium is already running at %APPIUM_URL%.
)

rem ---- Phone: UDID wins when set, else the first connected device ----
if defined UDID (
  "%ADB%" -s %UDID% get-state 1>nul 2>nul
  if errorlevel 1 (
    echo Phone %UDID% ^(from UDID^) is not connected.
    pause
    exit /b 1
  )
  echo Using phone from UDID: %UDID%
) else (
  set "UDID="
  for /f "skip=1 tokens=1,2" %%A in ('"%ADB%" devices 2^>nul') do (
    if "%%B"=="device" (
      if not defined UDID (
        set "UDID=%%A"
      ) else (
        echo Also connected, ignored: %%A ^(set UDID=%%A first to use it instead^)
      )
    )
  )
  if not defined UDID (
    echo No phone is connected. Connect one with USB debugging on, then rerun this file.
    pause
    exit /b 1
  )
  echo Using connected phone: !UDID!
  "%ADB%" -s !UDID! get-state 1>nul 2>nul
  if errorlevel 1 (
    echo Phone !UDID! stopped responding. Reconnect it, then rerun this file.
    pause
    exit /b 1
  )
)

rem ---- App: fail fast when the build is not on the phone ----
"%ADB%" -s %UDID% shell pm list packages 2>nul | %SystemRoot%\System32\findstr /c:"%APP_PACKAGE%" 1>nul
if errorlevel 1 (
  echo %APP_PACKAGE% is not installed on %UDID%. Install the build, then rerun.
  pause
  exit /b 1
)
echo App %APP_PACKAGE% is on the phone.

"%ADB%" -s %UDID% shell am force-stop io.appium.uiautomator2.server
"%ADB%" -s %UDID% shell am force-stop io.appium.uiautomator2.server.test

"%JAVA_HOME%\bin\java.exe" "-Dmaven.home=%M2_HOME%" "-Dmaven.multiModuleProjectDirectory=%CD%" -classpath "%M2_HOME%\boot\plexus-classworlds-2.11.0.jar" "-Dclassworlds.conf=%M2_HOME%\bin\m2.conf" org.codehaus.plexus.classworlds.launcher.Launcher test -pl airpay_vyaapaar -am "-DforkCount=0" "-Dtest=VyaapaarRunner" "-Dsurefire.failIfNoSpecifiedTests=false" "-Dcucumber.filter.tags=@VYAAPAAR_001 and @deep" "-Dairpay.product=vyaapaar" "-Dandroid.udid=%UDID%" "-Da11y.enabled=true" "-Da11y.deep.scan=true" "-Da11y.gate.strict=false"
set "EXIT_CODE=%ERRORLEVEL%"

echo.
echo Scorecard: %CD%\target\vyaapaar\accessibility_testing_result\index.html
echo ZIP:       %CD%\target\vyaapaar\accessibility_email_artifact\vyaapaar-a11y-reports.zip
echo Logs:      %CD%\target\vyaapaar\logs\automation.log
echo Cucumber:  %CD%\target\vyaapaar\cucumber-reports\cucumber.html
echo.
pause
exit /b %EXIT_CODE%
