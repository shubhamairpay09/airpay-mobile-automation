@echo off
setlocal
rem Airpay Money accessibility. This is the only accessibility batch file.
rem It walks the logged-in app: Profile settings, General settings, products
rem and filters, FinSight, then Calculators.
rem Change profile picture, Change PIN, Log Out Yes, Send Message, and payments are not tapped.
rem Existing pages in the scorecard are kept. This file does not delete target\money.

cd /d "%~dp0..\.."

if not defined JAVA_HOME set "JAVA_HOME=C:\Program Files\Java\jdk-21.0.11"
set "M2_HOME=C:\Tools\apache-maven-3.9.16"

echo.
echo === Airpay Money accessibility ===
echo Order:  Profile settings, General settings, Products and filters, FinSight, Calculators
echo Report: target\money\accessibility_testing_result
echo ZIP:    target\money\accessibility_email_artifact\money-a11y-reports.zip
echo Logs:   target\money\logs\automation.log
echo This run does not save functional failure screenshots.
echo The phone should already be logged in.
echo.

call scripts\_lib\prepare-device.bat money
if errorlevel 1 (
  pause
  exit /b 1
)
set "UDID="
for /f "usebackq tokens=1,* delims==" %%A in ("target\money\prepare.env") do set "%%A=%%B"

"%JAVA_HOME%\bin\java.exe" "-Dmaven.home=%M2_HOME%" "-Dmaven.multiModuleProjectDirectory=%CD%" -classpath "%M2_HOME%\boot\plexus-classworlds-2.11.0.jar" "-Dclassworlds.conf=%M2_HOME%\bin\m2.conf" org.codehaus.plexus.classworlds.launcher.Launcher test -pl airpay_money -am "-DforkCount=0" "-Dtest=MoneyRunner" "-Dsurefire.failIfNoSpecifiedTests=false" "-Dcucumber.filter.tags=@MONEY_001 and @deep" "-Dairpay.product=money" "-Dandroid.udid=%UDID%" "-Da11y.enabled=true" "-Da11y.deep.scan=true" "-Da11y.gate.strict=false"
set "EXIT_CODE=%ERRORLEVEL%"

echo.
echo Scorecard: %CD%\target\money\accessibility_testing_result\money-scorecard.html
echo Index:     %CD%\target\money\accessibility_testing_result\index.html
echo ZIP:       %CD%\target\money\accessibility_email_artifact\money-a11y-reports.zip
echo Logs:      %CD%\target\money\logs\automation.log
echo Cucumber:  %CD%\target\money\cucumber-reports\cucumber.html
echo.
pause
exit /b %EXIT_CODE%
