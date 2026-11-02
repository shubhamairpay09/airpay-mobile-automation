@echo off
setlocal EnableDelayedExpansion
rem Deep a11y from the screen already open — edits, filters, dropdowns, next screens.
rem Default device: 0412419R50001340

cd /d "%~dp0..\.."

if not defined JAVA_HOME set "JAVA_HOME=C:\Program Files\Java\jdk-21.0.11"
set "M2_HOME=C:\Tools\apache-maven-3.9.16"

set "PREFERRED_UDID=0412419R50001340"
if not "%~1"=="" set "PREFERRED_UDID=%~1"

set "REPORT_DIR=target\vyaapaar\accessibility_testing_result"

echo.
echo === Vyaapaar a11y FROM OPEN SCREEN (deep: edits/filters/dropdowns) ===
echo Device: %PREFERRED_UDID%
echo Report: %REPORT_DIR%
echo.

if exist "%REPORT_DIR%" rmdir /S /Q "%REPORT_DIR%" 2>nul
mkdir "%REPORT_DIR%" 2>nul

call scripts\_lib\prepare-device.bat vyaapaar %PREFERRED_UDID%
if errorlevel 1 exit /b 1
set "UDID="
for /f "usebackq tokens=1,* delims==" %%A in ("target\vyaapaar\prepare.env") do set "%%A=%%B"

"%JAVA_HOME%\bin\java.exe" "-Dmaven.home=%M2_HOME%" "-Dmaven.multiModuleProjectDirectory=%CD%" -classpath "%M2_HOME%\boot\plexus-classworlds-2.11.0.jar" "-Dclassworlds.conf=%M2_HOME%\bin\m2.conf" org.codehaus.plexus.classworlds.launcher.Launcher test -pl airpay_vyaapaar -am "-DforkCount=0" "-Dtest=VyaapaarRunner" "-Dsurefire.failIfNoSpecifiedTests=false" "-Dcucumber.filter.tags=@VYAAPAAR_FROM_001 and @from-here" "-Dairpay.product=vyaapaar" "-Dandroid.udid=%UDID%" "-Da11y.enabled=true" "-Da11y.deep.scan=true" "-Da11y.from.here=true" "-Da11y.skip.edits=false" "-Da11y.skip.fill=false" "-Da11y.gate.strict=false" "-Dandroid.keep.session=true" "-Da11y.deep.max.screens=120"
set "EXIT_CODE=%ERRORLEVEL%"

echo.
echo Scorecard: %CD%\%REPORT_DIR%\vyaapaar-scorecard.html
echo ZIP:       %CD%\target\vyaapaar\accessibility_email_artifact\vyaapaar-a11y-reports.zip
exit /b %EXIT_CODE%
