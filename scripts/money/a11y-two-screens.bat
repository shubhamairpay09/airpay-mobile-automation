@echo off
setlocal EnableDelayedExpansion
rem Airpay Money — live a11y for TWO different screens only (no deep crawl, no taps).
rem Pass UDID as arg1 when needed. Default: first connected device via prepare-device.
rem 1) Open screen 1 on the phone, then run this bat (or leave screen 1 open).
rem 2) When log says "Waiting... different screen", navigate to screen 2.
rem 3) Scorecard: target\money\accessibility_testing_result\

cd /d "%~dp0..\.."
if errorlevel 1 (
  echo Could not cd to the repo root from %~dp0
  pause
  exit /b 1
)

set "PREFERRED_UDID=%~1"

set "REPORT_DIR=target\money\accessibility_testing_result"

echo.
echo === Airpay Money a11y — TWO live screens only ===
echo Device: %PREFERRED_UDID% ^(empty = first connected^)
echo Report: %REPORT_DIR%
echo Steps:  leave Screen 1 open → bat starts → when prompted, open Screen 2
echo Wait:   up to 300s for screen change (-Da11y.live.wait.seconds=N to change)
echo.

rem ---- Resolve Java / Maven for THIS machine (not hard-coded PC paths) ----
call :resolveJava
if errorlevel 1 (
  echo Java was not found. Install JDK 17+ and set JAVA_HOME, then rerun.
  pause
  exit /b 1
)
call :resolveMaven
if errorlevel 1 (
  echo Maven was not found. Install Apache Maven and set M2_HOME or MAVEN_HOME, or put mvn on PATH.
  pause
  exit /b 1
)
echo JAVA_HOME=%JAVA_HOME%
echo M2_HOME=%M2_HOME%
if defined CLASSWORLDS_JAR echo Maven launcher jar=%CLASSWORLDS_JAR%
echo.

if exist "%REPORT_DIR%" (
  echo Clearing previous report folder for a fresh two-screen run...
  rmdir /S /Q "%REPORT_DIR%" 2>nul
)
if not exist "%REPORT_DIR%" mkdir "%REPORT_DIR%"

call scripts\_lib\prepare-device.bat money %PREFERRED_UDID%
if errorlevel 1 (
  pause
  exit /b 1
)
set "UDID="
for /f "usebackq tokens=1,* delims==" %%A in ("target\money\prepare.env") do set "%%A=%%B"

echo Using UDID=%UDID%
echo Open Screen 1 on the phone now if it is not already showing.
timeout /t 5 /nobreak >nul

if defined CLASSWORLDS_JAR (
  "%JAVA_HOME%\bin\java.exe" "-Dmaven.home=%M2_HOME%" "-Dmaven.multiModuleProjectDirectory=%CD%" -classpath "%CLASSWORLDS_JAR%" "-Dclassworlds.conf=%M2_HOME%\bin\m2.conf" org.codehaus.plexus.classworlds.launcher.Launcher test -pl airpay_money -am "-DforkCount=0" "-Dtest=MoneyRunner" "-Dsurefire.failIfNoSpecifiedTests=false" "-Dcucumber.filter.tags=@MONEY_TWO_001 and @live-two" "-Dairpay.product=money" "-Dandroid.udid=%UDID%" "-Da11y.enabled=true" "-Da11y.deep.scan=false" "-Da11y.gate.strict=false" "-Dandroid.keep.session=true" "-Da11y.live.wait.seconds=300"
) else (
  call mvn test -pl airpay_money -am "-DforkCount=0" "-Dtest=MoneyRunner" "-Dsurefire.failIfNoSpecifiedTests=false" "-Dcucumber.filter.tags=@MONEY_TWO_001 and @live-two" "-Dairpay.product=money" "-Dandroid.udid=%UDID%" "-Da11y.enabled=true" "-Da11y.deep.scan=false" "-Da11y.gate.strict=false" "-Dandroid.keep.session=true" "-Da11y.live.wait.seconds=300"
)
set "EXIT_CODE=%ERRORLEVEL%"

echo.
echo Scorecard: %CD%\%REPORT_DIR%\money-scorecard.html
echo Logs:      %CD%\target\money\logs\automation.log
echo.
pause
exit /b %EXIT_CODE%

:resolveJava
if defined JAVA_HOME if exist "%JAVA_HOME%\bin\java.exe" exit /b 0
if exist "C:\Program Files\Java\jdk-21.0.11\bin\java.exe" (
  set "JAVA_HOME=C:\Program Files\Java\jdk-21.0.11"
  exit /b 0
)
if exist "C:\Program Files\Java\jdk-21\bin\java.exe" (
  set "JAVA_HOME=C:\Program Files\Java\jdk-21"
  exit /b 0
)
if exist "C:\Program Files\Eclipse Adoptium\jdk-21.0.11-hotspot\bin\java.exe" (
  set "JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.11-hotspot"
  exit /b 0
)
for /f "delims=" %%J in ('where java 2^>nul') do (
  for %%D in ("%%~dpJ..") do (
    if exist "%%~fD\bin\java.exe" (
      set "JAVA_HOME=%%~fD"
      exit /b 0
    )
  )
)
exit /b 1

:resolveMaven
set "CLASSWORLDS_JAR="
if not defined M2_HOME if defined MAVEN_HOME set "M2_HOME=%MAVEN_HOME%"
if not defined M2_HOME if exist "C:\Tools\apache-maven-3.9.16\bin\mvn.cmd" set "M2_HOME=C:\Tools\apache-maven-3.9.16"
if not defined M2_HOME if exist "C:\Program Files\Apache\maven\bin\mvn.cmd" set "M2_HOME=C:\Program Files\Apache\maven"
if not defined M2_HOME (
  for /f "delims=" %%M in ('where mvn 2^>nul') do (
    for %%D in ("%%~dpM..") do (
      if exist "%%~fD\bin\mvn.cmd" (
        set "M2_HOME=%%~fD"
        goto :mavenHomeSet
      )
    )
  )
)
:mavenHomeSet
if defined M2_HOME (
  for %%F in ("%M2_HOME%\boot\plexus-classworlds-*.jar") do (
    if exist "%%~fF" (
      set "CLASSWORLDS_JAR=%%~fF"
      goto :mavenOk
    )
  )
)
where mvn >nul 2>nul
if not errorlevel 1 (
  set "CLASSWORLDS_JAR="
  exit /b 0
)
exit /b 1
:mavenOk
exit /b 0
