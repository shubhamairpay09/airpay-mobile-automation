@echo off
setlocal
set "PRODUCT=%~1"
if "%PRODUCT%"=="" set "PRODUCT=capital"
set "ROOT=%~dp0..\.."
cd /d "%ROOT%"

call "%~dp0..\_lib\resolve-java-maven.bat"
if errorlevel 1 exit /b 1

dir /b /ad ".allure\allure-*" >nul 2>&1
if errorlevel 1 (
  echo Installing Allure CLI to .allure ...
  if defined CLASSWORLDS_JAR (
    "%JAVA_HOME%\bin\java.exe" "-Dmaven.home=%M2_HOME%" "-Dmaven.multiModuleProjectDirectory=%CD%" -classpath "%CLASSWORLDS_JAR%" "-Dclassworlds.conf=%M2_HOME%\bin\m2.conf" org.codehaus.plexus.classworlds.launcher.Launcher -q io.qameta.allure:allure-maven:2.13.0:install
  ) else (
    mvn -q io.qameta.allure:allure-maven:2.13.0:install
  )
)

set "RESULTS=target\%PRODUCT%\allure-results"
set "REPORT=target\%PRODUCT%\allure-report"
if not exist "%RESULTS%" (
  echo No Allure results at %RESULTS%
  exit /b 1
)

set "ALLURE_BAT="
for /d %%A in (".allure\allure-*") do (
  set "ALLURE_BAT=%%A\bin\allure.bat"
)
if not defined ALLURE_BAT (
  echo Allure CLI was not installed. Results are still in %CD%\%RESULTS%
  exit /b 1
)

call "%ALLURE_BAT%" generate "%RESULTS%" -o "%REPORT%" --clean
if errorlevel 1 (
  echo Allure generate failed. Results are still in %CD%\%RESULTS%
  exit /b 1
)
echo Allure report: %CD%\%REPORT%\index.html
if defined JENKINS_URL goto :generated
if defined JENKINS_HOME goto :generated
if /i "%OPEN_ALLURE_BROWSER%"=="No" goto :generated

set "PORT=4811"
if /i "%PRODUCT%"=="money" set "PORT=4812"
if /i "%PRODUCT%"=="vyaapaar" set "PORT=4813"

for /f "tokens=5" %%P in ('netstat -ano ^| findstr ":%PORT% " ^| findstr LISTENING') do (
  taskkill /PID %%P /F >nul 2>&1
)

rem Headless so Allure does not also open the default browser. Chrome is started below.
start "Allure %PRODUCT%" /MIN cmd /c "set JAVA_TOOL_OPTIONS=-Djava.awt.headless=true&& call ""%ALLURE_BAT%"" open -h 127.0.0.1 -p %PORT% ""%CD%\%REPORT%"""

set /a TRIES=0
:waitport
set /a TRIES+=1
netstat -ano | findstr ":%PORT% " | findstr LISTENING >nul
if not errorlevel 1 goto :openchrome
if %TRIES% GEQ 25 (
  echo Allure server did not start. Open %CD%\%REPORT%\index.html manually.
  exit /b 1
)
timeout /t 1 /nobreak >nul
goto :waitport

:openchrome
set "CHROME=%ProgramFiles%\Google\Chrome\Application\chrome.exe"
if not exist "%CHROME%" set "CHROME=%ProgramFiles(x86)%\Google\Chrome\Application\chrome.exe"
if not exist "%CHROME%" set "CHROME=%LOCALAPPDATA%\Google\Chrome\Application\chrome.exe"
if not exist "%CHROME%" (
  echo Chrome was not found. Report URL: http://127.0.0.1:%PORT%/
  exit /b 0
)
start "" "%CHROME%" "http://127.0.0.1:%PORT%/"
echo Opened Allure in Chrome: http://127.0.0.1:%PORT%/
exit /b 0

:generated
echo Allure HTML is ready. The browser was not opened.
exit /b 0
