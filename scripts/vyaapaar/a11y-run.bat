@echo off
setlocal
rem Airpay Vyaapaar accessibility. This is the only accessibility batch file.
rem The app session stays on the phone. Each screen is walked once.
rem This file does not delete target\vyaapaar.

cd /d "%~dp0..\.."

if not defined JAVA_HOME set "JAVA_HOME=C:\Program Files\Java\jdk-21.0.11"
set "M2_HOME=C:\Tools\apache-maven-3.9.16"

echo.
echo === Airpay Vyaapaar accessibility ===
echo Report: target\vyaapaar\accessibility_testing_result
echo ZIP:    target\vyaapaar\accessibility_email_artifact\vyaapaar-a11y-reports.zip
echo Logs:   target\vyaapaar\logs\automation.log
echo This run does not save functional failure screenshots.
echo.

call scripts\_lib\prepare-device.bat vyaapaar
if errorlevel 1 (
  pause
  exit /b 1
)
set "UDID="
for /f "usebackq tokens=1,* delims==" %%A in ("target\vyaapaar\prepare.env") do set "%%A=%%B"

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
