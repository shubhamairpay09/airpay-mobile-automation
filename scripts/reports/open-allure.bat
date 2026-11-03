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

for /d %%A in (".allure\allure-*") do (
  call "%%A\bin\allure.bat" generate "%RESULTS%" -o "%REPORT%" --clean
  echo Allure report: %CD%\%REPORT%\index.html
  exit /b %ERRORLEVEL%
)

echo Allure CLI was not installed. Results are still in %CD%\%RESULTS%
exit /b 1
