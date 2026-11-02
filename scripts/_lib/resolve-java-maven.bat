@echo off
rem Resolve JAVA_HOME / M2_HOME / CLASSWORLDS_JAR for this machine.
rem Usage: call "%~dp0resolve-java-maven.bat"
rem Shared by Money + Vyaapaar. No setlocal here so caller keeps the vars.
rem Exit: 0=ok, 1=Java missing, 2=Maven missing

rem ---- Java ----
if defined JAVA_HOME for /f "delims=" %%A in ("%JAVA_HOME%") do set "JAVA_HOME=%%~A"
if defined JAVA_HOME if exist "%JAVA_HOME%\bin\java.exe" goto :javaOk
if defined JAVA_HOME if exist "%JAVA_HOME%\java.exe" for %%D in ("%JAVA_HOME%\..") do set "JAVA_HOME=%%~fD"
if defined JAVA_HOME if exist "%JAVA_HOME%\bin\java.exe" goto :javaOk

if exist "C:\Program Files\Java\jdk-21.0.11\bin\java.exe" set "JAVA_HOME=C:\Program Files\Java\jdk-21.0.11" & goto :javaOk
if exist "C:\Program Files\Java\jdk-21\bin\java.exe" set "JAVA_HOME=C:\Program Files\Java\jdk-21" & goto :javaOk
if exist "C:\Program Files\Eclipse Adoptium\jdk-21.0.11-hotspot\bin\java.exe" set "JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.11-hotspot" & goto :javaOk

for /d %%D in ("C:\Program Files\Java\jdk-*") do if exist "%%~fD\bin\java.exe" set "JAVA_HOME=%%~fD" & goto :javaOk
for /d %%D in ("C:\Program Files\Eclipse Adoptium\jdk-*") do if exist "%%~fD\bin\java.exe" set "JAVA_HOME=%%~fD" & goto :javaOk
for /d %%D in ("C:\Program Files\Microsoft\jdk-*") do if exist "%%~fD\bin\java.exe" set "JAVA_HOME=%%~fD" & goto :javaOk

for /f "delims=" %%J in ('where java 2^>nul') do if exist "%%~dpJ..\lib" for %%D in ("%%~dpJ..") do if exist "%%~fD\bin\java.exe" set "JAVA_HOME=%%~fD" & goto :javaOk

echo.
echo [resolve] Java was not found.
echo   JAVA_HOME=[%JAVA_HOME%]
echo   Expected a JDK root that contains bin\java.exe
echo   Tip: remove quotes from JAVA_HOME in System Properties, then open a NEW CMD.
exit /b 1

:javaOk
rem ---- Maven ----
set "CLASSWORLDS_JAR="
if defined M2_HOME for /f "delims=" %%A in ("%M2_HOME%") do set "M2_HOME=%%~A"
if defined MAVEN_HOME for /f "delims=" %%A in ("%MAVEN_HOME%") do set "MAVEN_HOME=%%~A"
if not defined M2_HOME if defined MAVEN_HOME set "M2_HOME=%MAVEN_HOME%"
if not defined M2_HOME if exist "C:\Tools\apache-maven-3.9.16\bin\mvn.cmd" set "M2_HOME=C:\Tools\apache-maven-3.9.16"
if not defined M2_HOME if exist "C:\Program Files\Apache\maven\bin\mvn.cmd" set "M2_HOME=C:\Program Files\Apache\maven"

if not defined M2_HOME for /f "delims=" %%M in ('where mvn.cmd 2^>nul') do for %%D in ("%%~dpM..") do if exist "%%~fD\bin\mvn.cmd" set "M2_HOME=%%~fD"
if not defined M2_HOME for /f "delims=" %%M in ('where mvn 2^>nul') do for %%D in ("%%~dpM..") do if exist "%%~fD\bin\mvn.cmd" set "M2_HOME=%%~fD"

if defined M2_HOME for %%F in ("%M2_HOME%\boot\plexus-classworlds-*.jar") do if exist "%%~fF" set "CLASSWORLDS_JAR=%%~fF" & goto :mavenOk
where mvn >nul 2>nul
if not errorlevel 1 goto :mavenOk

echo.
echo [resolve] Maven was not found.
echo   M2_HOME=[%M2_HOME%]  MAVEN_HOME=[%MAVEN_HOME%]
echo   Fix: set M2_HOME to the Maven root folder that contains bin\mvn.cmd, or put mvn on PATH.
exit /b 2

:mavenOk
exit /b 0
