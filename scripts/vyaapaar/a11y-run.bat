@echo off
setlocal EnableDelayedExpansion
rem Airpay Vyaapaar accessibility. This is the only accessibility batch file.
rem The app session stays on the phone. Each screen is walked once.
rem Each run wipes target\vyaapaar and builds a fresh report (set FRESH_REPORT=No to merge instead).
rem It starts Appium itself when Appium is down, and uses whichever phone is
rem connected (set UDID=<serial> first to force one when several are attached).
rem Login wall first: the login screen is scanned in detail, then the run signs in
rem with the username/password login. Signed in, report names follow the home
rem sections: Featured Services/PAN Application, Money Transfer/..., Non Channels/...,
rem Recharge & Billpay/..., Other Services/..., then Services, History, and Profile.

cd /d "%~dp0..\.."

set "ADB=D:\Android_Studio\Sdk\platform-tools\adb.exe"
if not exist "%ADB%" set "ADB=adb"
set "APPIUM_URL=http://127.0.0.1:4723"
set "APPIUM_WAIT_SECS=90"
set "APP_PACKAGE=com.airpay.mahaepay_aeps"

rem ---- Java / Maven: use this machine's install, not a hard-coded PC path ----
call :resolveJava
if errorlevel 1 (
  echo Java was not found. Install JDK 17+ and set JAVA_HOME, then rerun.
  pause
  exit /b 1
)
call :resolveMaven
if errorlevel 1 (
  echo Maven was not found. Install Apache Maven and set M2_HOME or MAVEN_HOME, or put mvn on PATH, then rerun.
  echo Expected boot jar under %%M2_HOME%%\boot\plexus-classworlds-*.jar
  pause
  exit /b 1
)
echo JAVA_HOME=%JAVA_HOME%
echo M2_HOME=%M2_HOME%
echo Maven launcher jar=%CLASSWORLDS_JAR%

echo.
echo === Airpay Vyaapaar accessibility ===
echo Report: target\vyaapaar\accessibility_testing_result
echo ZIP:    target\vyaapaar\accessibility_email_artifact\vyaapaar-a11y-reports.zip
echo Logs:   target\vyaapaar\logs\automation.log
echo This run does not save functional failure screenshots.
echo.

rem ---- Appium CLI + UiAutomator2: the server on :4723 must be THIS appium ----
set "APPIUM_BIN="
for /f "delims=" %%A in ('where appium 2^>nul') do if not defined APPIUM_BIN set "APPIUM_BIN=%%A"
if not defined APPIUM_BIN if exist "%APPDATA%\npm\appium.cmd" set "APPIUM_BIN=%APPDATA%\npm\appium.cmd"
if not defined APPIUM_BIN (
  echo Could not find appium. Install it once with: npm install -g appium
  echo Then install the Android driver: appium driver install uiautomator2
  pause
  exit /b 1
)
echo Using Appium CLI: !APPIUM_BIN!
"!APPIUM_BIN!" driver list --installed 2>nul | %SystemRoot%\System32\findstr /i /c:"uiautomator2" 1>nul
if errorlevel 1 (
  echo.
  echo UiAutomator2 driver is not installed for this Appium CLI.
  echo Install it once, then rerun this file:
  echo   appium driver install uiautomator2
  echo.
  pause
  exit /b 1
)
echo Appium UiAutomator2 driver is installed for this CLI.

rem Restart Appium with the same CLI so :4723 is not an old/other Appium without the driver.
rem Set KEEP_APPIUM=Yes to skip the restart when you are sure the running server matches.
if /i not "%KEEP_APPIUM%"=="Yes" (
  echo Restarting Appium on %APPIUM_URL% with this CLI ^(set KEEP_APPIUM=Yes to skip^)...
  powershell -NoProfile -Command "$ErrorActionPreference='SilentlyContinue'; Get-NetTCPConnection -LocalPort 4723 -State Listen | ForEach-Object { Stop-Process -Id $_.OwningProcess -Force }; Get-CimInstance Win32_Process | Where-Object { $_.Name -match 'node|appium' -and $_.CommandLine -match 'appium' } | ForEach-Object { Stop-Process -Id $_.ProcessId -Force }"
  timeout /t 2 /nobreak >nul
  start "Airpay Appium" "!APPIUM_BIN!"
  echo Waiting up to %APPIUM_WAIT_SECS%s for Appium to come up...
  powershell -NoProfile -Command "$t=0; while ($t -lt %APPIUM_WAIT_SECS%) { try { if ((Invoke-WebRequest -UseBasicParsing -TimeoutSec 3 %APPIUM_URL%/status).StatusCode -eq 200) { exit 0 } } catch {}; Start-Sleep -Seconds 2; $t+=2 }; exit 1"
  if errorlevel 1 (
    echo Appium did not come up in time. Check the Appium window, then rerun this file.
    pause
    exit /b 1
  )
  echo Appium is up with the CLI that has UiAutomator2.
) else (
  powershell -NoProfile -Command "try { if ((Invoke-WebRequest -UseBasicParsing -TimeoutSec 5 %APPIUM_URL%/status).StatusCode -ne 200) { exit 1 } } catch { exit 1 }"
  if errorlevel 1 (
    echo KEEP_APPIUM=Yes but Appium is not running at %APPIUM_URL%.
    pause
    exit /b 1
  )
  echo Keeping existing Appium at %APPIUM_URL%.
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

rem ---- Fresh report: wipe the previous run unless asked to keep it ----
if /i not "%FRESH_REPORT%"=="No" (
  if exist "%CD%\target\vyaapaar" (
    echo Removing previous report at target\vyaapaar ...
    rmdir /s /q "%CD%\target\vyaapaar"
  )
  echo Fresh report: target\vyaapaar will be built from this run.
) else (
  echo Keeping previous report at target\vyaapaar ^(merge mode^).
)

if defined CLASSWORLDS_JAR (
  "%JAVA_HOME%\bin\java.exe" "-Dmaven.home=%M2_HOME%" "-Dmaven.multiModuleProjectDirectory=%CD%" -classpath "%CLASSWORLDS_JAR%" "-Dclassworlds.conf=%M2_HOME%\bin\m2.conf" org.codehaus.plexus.classworlds.launcher.Launcher test -pl airpay_vyaapaar -am "-DforkCount=0" "-Dtest=VyaapaarRunner" "-Dsurefire.failIfNoSpecifiedTests=false" "-Dcucumber.filter.tags=@VYAAPAAR_001 and @deep" "-Dairpay.product=vyaapaar" "-Dandroid.udid=%UDID%" "-Da11y.enabled=true" "-Da11y.deep.scan=true" "-Da11y.gate.strict=false" "-Da11y.login.password=true" "-Da11y.skip.fill=true"
) else (
  call mvn test -pl airpay_vyaapaar -am "-DforkCount=0" "-Dtest=VyaapaarRunner" "-Dsurefire.failIfNoSpecifiedTests=false" "-Dcucumber.filter.tags=@VYAAPAAR_001 and @deep" "-Dairpay.product=vyaapaar" "-Dandroid.udid=%UDID%" "-Da11y.enabled=true" "-Da11y.deep.scan=true" "-Da11y.gate.strict=false" "-Da11y.login.password=true" "-Da11y.skip.fill=true"
)
set "EXIT_CODE=%ERRORLEVEL%"

echo.
echo Scorecard: %CD%\target\vyaapaar\accessibility_testing_result\index.html
echo ZIP:       %CD%\target\vyaapaar\accessibility_email_artifact\vyaapaar-a11y-reports.zip
echo Logs:      %CD%\target\vyaapaar\logs\automation.log
echo Cucumber:  %CD%\target\vyaapaar\cucumber-reports\cucumber.html
echo.
pause
exit /b %EXIT_CODE%

:resolveJava
if defined JAVA_HOME if exist "%JAVA_HOME%\bin\java.exe" exit /b 0
if exist "C:\Program Files\Java\jdk-21.0.11\bin\java.exe" (
  set "JAVA_HOME=C:\Program Files\Java\jdk-21.0.11"
  exit /b 0
)
if exist "C:\Program Files\Java\jdk-21\bin\java.exe" (
  set "JAVA_HOME=C:\Program Files\Java\jdk-21"
  exit /b 0
)
if exist "C:\Program Files\Eclipse Adoptium\jdk-21.0.11-hotspot\bin\java.exe" (
  set "JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.11-hotspot"
  exit /b 0
)
for /f "delims=" %%J in ('where java 2^>nul') do (
  for %%D in ("%%~dpJ..") do (
    if exist "%%~fD\bin\java.exe" (
      set "JAVA_HOME=%%~fD"
      exit /b 0
    )
  )
)
exit /b 1

:resolveMaven
set "CLASSWORLDS_JAR="
if not defined M2_HOME if defined MAVEN_HOME set "M2_HOME=%MAVEN_HOME%"
if not defined M2_HOME if exist "C:\Tools\apache-maven-3.9.16\bin\mvn.cmd" set "M2_HOME=C:\Tools\apache-maven-3.9.16"
if not defined M2_HOME if exist "C:\Program Files\Apache\maven\bin\mvn.cmd" set "M2_HOME=C:\Program Files\Apache\maven"
if not defined M2_HOME (
  for /f "delims=" %%M in ('where mvn 2^>nul') do (
    for %%D in ("%%~dpM..") do (
      if exist "%%~fD\bin\mvn.cmd" (
        set "M2_HOME=%%~fD"
        goto :mavenHomeSet
      )
    )
  )
)
:mavenHomeSet
if defined M2_HOME (
  for %%F in ("%M2_HOME%\boot\plexus-classworlds-*.jar") do (
    if exist "%%~fF" (
      set "CLASSWORLDS_JAR=%%~fF"
      goto :mavenOk
    )
  )
)
where mvn >nul 2>nul
if not errorlevel 1 (
  set "CLASSWORLDS_JAR="
  exit /b 0
)
exit /b 1
:mavenOk
exit /b 0
