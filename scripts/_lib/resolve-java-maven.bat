@echo off
rem Resolve JAVA_HOME / M2_HOME / CLASSWORLDS_JAR for this machine.
rem Downloads JDK 21 or Maven into %LOCALAPPDATA%\airpay-mobile-tools when JDK 21+ is missing.
rem Prefers C:\Program Files\Java\jdk-27.0.1.0.3 when that JDK is installed.
rem Usage: call "%~dp0resolve-java-maven.bat"
rem No setlocal here so the caller keeps the variables.
rem Exit: 0=ok, 1=Java missing, 2=Maven missing

call "%~dp0ensure-java-maven.bat"
if errorlevel 1 exit /b %ERRORLEVEL%

set "CLASSWORLDS_JAR="
if defined M2_HOME for %%F in ("%M2_HOME%\boot\plexus-classworlds-*.jar") do if exist "%%~fF" set "CLASSWORLDS_JAR=%%~fF"
if defined CLASSWORLDS_JAR exit /b 0

where mvn.cmd >nul 2>&1
if not errorlevel 1 exit /b 0
where mvn >nul 2>&1
if not errorlevel 1 exit /b 0

echo [resolve] Maven was found but plexus-classworlds is missing and mvn is not on PATH.
echo   M2_HOME=[%M2_HOME%]
exit /b 2
