@echo off
setlocal
set "PRODUCT=%~1"
if "%PRODUCT%"=="" set "PRODUCT=capital"
set "ROOT=%~dp0..\.."
cd /d "%ROOT%"

if not defined JAVA_HOME set "JAVA_HOME=C:\Program Files\Java\jdk-21.0.11"
if not defined M2_HOME set "M2_HOME=C:\Tools\apache-maven-3.9.16"

dir /b /ad ".allure\allure-*" >nul 2>&1
if errorlevel 1 (
  echo Installing Allure CLI to .allure ...
  if exist "%M2_HOME%\boot\plexus-classworlds-2.11.0.jar" (
    "%JAVA_HOME%\bin\java.exe" "-Dmaven.home=%M2_HOME%" "-Dmaven.multiModuleProjectDirectory=%CD%" -classpath "%M2_HOME%\boot\plexus-classworlds-2.11.0.jar" "-Dclassworlds.conf=%M2_HOME%\bin\m2.conf" org.codehaus.plexus.classworlds.launcher.Launcher -q io.qameta.allure:allure-maven:2.13.0:install
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
