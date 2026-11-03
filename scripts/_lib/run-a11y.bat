@echo off
setlocal EnableDelayedExpansion
rem Shared accessibility run. Each app calls this from its own scripts\<app>\a11y.bat.
rem Caller sets PRODUCT, MODULE, TEST, TAGS, EXTRA, and optional PREFERRED_UDID.

cd /d "%~dp0..\.."
if "%PRODUCT%"=="" (
  echo PRODUCT is not set. Run scripts\money\a11y.bat, scripts\capital\a11y.bat, or scripts\vyaapaar\a11y.bat
  exit /b 1
)

echo.
echo === Accessibility  %PRODUCT% ===
echo Report: target\%PRODUCT%\accessibility_testing_result
echo.

call "%~dp0resolve-java-maven.bat"
if errorlevel 1 exit /b 1
echo JAVA_HOME=%JAVA_HOME%
echo M2_HOME=%M2_HOME%

call "%~dp0prepare-device.bat" %PRODUCT% %PREFERRED_UDID%
if errorlevel 1 exit /b 1
set "UDID="
for /f "usebackq tokens=1,* delims==" %%A in ("target\%PRODUCT%\prepare.env") do set "%%A=%%B"
echo Using UDID=!UDID!

if defined CLASSWORLDS_JAR (
  "%JAVA_HOME%\bin\java.exe" "-Dmaven.home=%M2_HOME%" "-Dmaven.multiModuleProjectDirectory=%CD%" -classpath "%CLASSWORLDS_JAR%" "-Dclassworlds.conf=%M2_HOME%\bin\m2.conf" org.codehaus.plexus.classworlds.launcher.Launcher test -pl %MODULE% -am "-DforkCount=0" "-Dtest=%TEST%" "-Dsurefire.failIfNoSpecifiedTests=false" "-Dcucumber.filter.tags=%TAGS%" "-Dairpay.product=%PRODUCT%" "-Dandroid.udid=!UDID!" "-Da11y.enabled=true" "-Da11y.gate.strict=false" %EXTRA%
) else (
  call mvn test -pl %MODULE% -am "-DforkCount=0" "-Dtest=%TEST%" "-Dsurefire.failIfNoSpecifiedTests=false" "-Dcucumber.filter.tags=%TAGS%" "-Dairpay.product=%PRODUCT%" "-Dandroid.udid=!UDID!" "-Da11y.enabled=true" "-Da11y.gate.strict=false" %EXTRA%
)
set "EXIT_CODE=%ERRORLEVEL%"

echo.
echo Scorecard: %CD%\target\%PRODUCT%\accessibility_testing_result
echo Logs:      %CD%\target\%PRODUCT%\logs\automation.log
exit /b %EXIT_CODE%
