@echo off
setlocal EnableDelayedExpansion
rem Vyaapaar — live a11y for TWO different screens only (no deep crawl).
rem Default device: 0412419R50001340

cd /d "%~dp0..\.."

if not defined JAVA_HOME set "JAVA_HOME=C:\Program Files\Java\jdk-21.0.11"
set "M2_HOME=C:\Tools\apache-maven-3.9.16"

set "PREFERRED_UDID=0412419R50001340"
if not "%~1"=="" set "PREFERRED_UDID=%~1"

set "REPORT_DIR=target\vyaapaar\accessibility_testing_result"

echo.
echo === Airpay Vyaapaar a11y — TWO post-login screens ===
echo Device: %PREFERRED_UDID%
echo Report: %REPORT_DIR%
echo 1) Logs in with valid credentials from secrets.properties
echo 2) Scans post-login screen 1, then waits for you to open screen 2
echo.

if exist "%REPORT_DIR%" rmdir /S /Q "%REPORT_DIR%" 2>nul
mkdir "%REPORT_DIR%" 2>nul

call scripts\_lib\prepare-device.bat vyaapaar %PREFERRED_UDID%
if errorlevel 1 (
  pause
  exit /b 1
)
set "UDID="
for /f "usebackq tokens=1,* delims==" %%A in ("target\vyaapaar\prepare.env") do set "%%A=%%B"

echo Using UDID=%UDID% — will login first, then wait for your second post-login screen.

"%JAVA_HOME%\bin\java.exe" "-Dmaven.home=%M2_HOME%" "-Dmaven.multiModuleProjectDirectory=%CD%" -classpath "%M2_HOME%\boot\plexus-classworlds-2.11.0.jar" "-Dclassworlds.conf=%M2_HOME%\bin\m2.conf" org.codehaus.plexus.classworlds.launcher.Launcher test -pl airpay_vyaapaar -am "-DforkCount=0" "-Dtest=VyaapaarRunner" "-Dsurefire.failIfNoSpecifiedTests=false" "-Dcucumber.filter.tags=@VYAAPAAR_TWO_001 and @live-two" "-Dairpay.product=vyaapaar" "-Dandroid.udid=%UDID%" "-Da11y.enabled=true" "-Da11y.deep.scan=false" "-Da11y.capture.two=true" "-Da11y.login.password=true" "-Da11y.gate.strict=false" "-Dandroid.keep.session=true" "-Da11y.live.wait.seconds=180"
set "EXIT_CODE=%ERRORLEVEL%"

echo.
echo Scorecard: %CD%\%REPORT_DIR%\index.html
if exist "%REPORT_DIR%\vyaapaar-scorecard.html" echo Alt: %CD%\%REPORT_DIR%\vyaapaar-scorecard.html
echo Logs:      %CD%\target\vyaapaar\logs\automation.log
echo.
exit /b %EXIT_CODE%
