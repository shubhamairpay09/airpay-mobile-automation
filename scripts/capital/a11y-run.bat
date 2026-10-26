@echo off
setlocal
rem Airpay Capital accessibility. This is the only accessibility batch file.
rem It logs in and scans Home, Profile, Notifications, Info, and every product
rem apply path, including Salaried and Self-employed.
rem This file does not delete target\capital.

cd /d "%~dp0..\.."

if not defined JAVA_HOME set "JAVA_HOME=C:\Program Files\Java\jdk-21.0.11"
set "M2_HOME=C:\Tools\apache-maven-3.9.16"

echo.
echo === Airpay Capital accessibility ===
echo Report: target\capital\accessibility_testing_result
echo ZIP:    target\capital\accessibility_email_artifact\capital-a11y-reports.zip
echo Logs:   target\capital\logs\automation.log
echo This run does not save functional failure screenshots.
echo.

call scripts\_lib\prepare-device.bat capital
if errorlevel 1 (
  pause
  exit /b 1
)
set "UDID="
for /f "usebackq tokens=1,* delims==" %%A in ("target\capital\prepare.env") do set "%%A=%%B"

"%JAVA_HOME%\bin\java.exe" "-Dmaven.home=%M2_HOME%" "-Dmaven.multiModuleProjectDirectory=%CD%" -classpath "%M2_HOME%\boot\plexus-classworlds-2.11.0.jar" "-Dclassworlds.conf=%M2_HOME%\bin\m2.conf" org.codehaus.plexus.classworlds.launcher.Launcher test -pl airpay_capital -am "-DforkCount=0" "-Dtest=CapitalRunner" "-Dsurefire.failIfNoSpecifiedTests=false" "-Dcucumber.filter.tags=@CAPITAL_A11Y_001 and @deep" "-Dairpay.product=capital" "-Dandroid.udid=%UDID%" "-Da11y.enabled=true" "-Da11y.deep.scan=true" "-Da11y.gate.strict=false"
set "EXIT_CODE=%ERRORLEVEL%"

echo.
echo Scorecard: %CD%\target\capital\accessibility_testing_result\index.html
echo ZIP:       %CD%\target\capital\accessibility_email_artifact\capital-a11y-reports.zip
echo Logs:      %CD%\target\capital\logs\automation.log
echo Cucumber:  %CD%\target\capital\cucumber-reports\cucumber.html
echo.
pause
exit /b %EXIT_CODE%
