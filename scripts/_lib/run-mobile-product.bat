@echo off
setlocal EnableDelayedExpansion
rem Shared local runner for mobile products (Appium). Product run.bat sets MODULE/PRODUCT, then calls this.
set "ROOT=%~dp0..\.."
cd /d "%ROOT%"

if "%MODULE%"=="" (
  echo MODULE is not set. Call from scripts\^<product^>\run.bat
  exit /b 1
)
if "%PRODUCT%"=="" (
  echo PRODUCT is not set.
  exit /b 1
)
if "%ENV%"=="" set "ENV=live"
if "%SUITE%"=="" set "SUITE=smoke"
if "%CLEAN_PREVIOUS_REPORTS%"=="" set "CLEAN_PREVIOUS_REPORTS=No"
if "%OPEN_ALLURE%"=="" set "OPEN_ALLURE=Yes"
if "%PARALLEL_THREADS%"=="" set "PARALLEL_THREADS=1"
if not defined JAVA_HOME set "JAVA_HOME=C:\Program Files\Java\jdk-21.0.11"
if not defined M2_HOME set "M2_HOME=C:\Tools\apache-maven-3.9.16"

echo.
echo === %MODULE%  product=%PRODUCT%  env=%ENV%  suite=%SUITE% ===
echo Logs:        %CD%\target\%PRODUCT%\logs\automation.log
echo Cucumber:    %CD%\target\%PRODUCT%\cucumber-reports\cucumber.html
echo Allure:      %CD%\target\%PRODUCT%\allure-report\index.html
if /i not "%SUITE%"=="a11y" if /i not "%A11Y%"=="Yes" (
  echo Screenshots: %CD%\target\%PRODUCT%\screenshots\failures
  echo              A screenshot is saved only when a functional scenario fails.
)
echo.

if /i "%CLEAN_PREVIOUS_REPORTS%"=="Yes" (
  if exist "target\%PRODUCT%" (
    echo Cleaning target\%PRODUCT%
    rmdir /s /q "target\%PRODUCT%" 2>nul
  )
)

call "%~dp0prepare-device.bat" %PRODUCT%
if errorlevel 1 exit /b 1
set "UDID="
for /f "usebackq tokens=1,* delims==" %%A in ("target\%PRODUCT%\prepare.env") do set "%%A=%%B"
set "DEVICE_ARG=-Dandroid.udid=%UDID%"
echo %EXTRA_MAVEN% | findstr /i /c:"android.udid" >nul
if not errorlevel 1 set "DEVICE_ARG="
echo Phone: %UDID%

call :deriveTags
if "%TAGS%"=="" (
  echo No Cucumber tags. Set CUCUMBER_TAGS or SUITE/EXTRA_TAGS in run.bat
  exit /b 1
)

set "A11Y_ARGS=-Da11y.enabled=false -Da11y.deep.scan=false"
if /i "%SUITE%"=="a11y" set "A11Y=Yes"
if /i "%A11Y%"=="Yes" set "A11Y_ARGS=-Da11y.enabled=true -Da11y.deep.scan=true"

set "FEAT_ARG="
if not "%CUCUMBER_FEATURES%"=="" set "FEAT_ARG=-Dcucumber.features=%CUCUMBER_FEATURES%"

set "TEST_ARG="
if not "%TEST%"=="" set "TEST_ARG=-Dtest=%TEST% -Dsurefire.failIfNoSpecifiedTests=false"

echo Tags: %TAGS%
call :mvnInstall
if errorlevel 1 exit /b 1

call :mvnTest
set "EXIT_CODE=%ERRORLEVEL%"

echo.
echo Logs:     %CD%\target\%PRODUCT%\logs\automation.log
echo Cucumber: %CD%\target\%PRODUCT%\cucumber-reports\cucumber.html
echo          %CD%\target\%PRODUCT%\cucumber-reports\cucumber.json
echo Allure:   %CD%\target\%PRODUCT%\allure-results
if /i "%A11Y%"=="Yes" (
  echo Scorecard: %CD%\target\%PRODUCT%\accessibility_testing_result\index.html
  echo Accessibility runs do not save failure screenshots.
) else (
  echo Failure screenshots: %CD%\target\%PRODUCT%\screenshots\failures
)

if /i "%OPEN_ALLURE%"=="Yes" (
  if exist "target\%PRODUCT%\allure-results" (
    call scripts\reports\open-allure.bat %PRODUCT%
  )
)

exit /b %EXIT_CODE%

:mvnInstall
if exist "%M2_HOME%\boot\plexus-classworlds-2.11.0.jar" (
  "%JAVA_HOME%\bin\java.exe" "-Dmaven.home=%M2_HOME%" "-Dmaven.multiModuleProjectDirectory=%CD%" -classpath "%M2_HOME%\boot\plexus-classworlds-2.11.0.jar" "-Dclassworlds.conf=%M2_HOME%\bin\m2.conf" org.codehaus.plexus.classworlds.launcher.Launcher -pl utilities -am install -DskipTests -q
) else (
  mvn -pl utilities -am install -DskipTests -q
)
exit /b %ERRORLEVEL%

:mvnTest
if exist "%M2_HOME%\boot\plexus-classworlds-2.11.0.jar" (
  "%JAVA_HOME%\bin\java.exe" "-Dmaven.home=%M2_HOME%" "-Dmaven.multiModuleProjectDirectory=%CD%" -classpath "%M2_HOME%\boot\plexus-classworlds-2.11.0.jar" "-Dclassworlds.conf=%M2_HOME%\bin\m2.conf" org.codehaus.plexus.classworlds.launcher.Launcher -pl %MODULE% -am test -Denv=%ENV% -Dairpay.product=%PRODUCT% %A11Y_ARGS% %FEAT_ARG% %TEST_ARG% %DEVICE_ARG% %EXTRA_MAVEN% "-Dcucumber.filter.tags=%TAGS%"
) else (
  mvn -pl %MODULE% -am test -Denv=%ENV% -Dairpay.product=%PRODUCT% %A11Y_ARGS% %FEAT_ARG% %TEST_ARG% %DEVICE_ARG% %EXTRA_MAVEN% "-Dcucumber.filter.tags=%TAGS%"
)
exit /b %ERRORLEVEL%

:deriveTags
if not "%CUCUMBER_TAGS%"=="" (
  set "TAGS=%CUCUMBER_TAGS%"
  goto :eof
)
if not "%PRODUCT_TAG%"=="" (
  set "TAGS=%PRODUCT_TAG%"
) else (
  set "TAGS=@airpay-%PRODUCT%"
)
if /i "%SUITE%"=="smoke" set "TAGS=%TAGS% and @smoke and not @a11y"
if /i "%SUITE%"=="functional" set "TAGS=%TAGS% and @functional and not @a11y"
if /i "%SUITE%"=="a11y" set "TAGS=%TAGS% and @a11y"
if /i "%SUITE%"=="regression" set "TAGS=%TAGS% and @regression and not @a11y"
if not "%EXTRA_TAGS%"=="" set "TAGS=%TAGS% and %EXTRA_TAGS%"
goto :eof
