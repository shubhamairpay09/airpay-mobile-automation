@echo off
setlocal EnableDelayedExpansion
rem =============================================================================
rem Airpay Money — ONE accessibility bat for any machine.
rem Usage:
rem   a11y-run.bat              deep Products scan (default)
rem   a11y-run.bat two [udid]   two live screens only
rem   a11y-run.bat deep [udid]  same as default
rem Java / Maven / ADB / Appium are resolved on this PC (no hard-coded paths).
rem =============================================================================

cd /d "%~dp0..\.."
if errorlevel 1 (
  echo Could not cd to the repo root from %~dp0
  pause
  exit /b 1
)

set "MODE=deep"
set "PREFERRED_UDID="
if /i "%~1"=="two" (
  set "MODE=two"
  set "PREFERRED_UDID=%~2"
) else if /i "%~1"=="deep" (
  set "MODE=deep"
  set "PREFERRED_UDID=%~2"
) else if not "%~1"=="" (
  rem First arg can be UDID when mode is omitted.
  set "PREFERRED_UDID=%~1"
)

set "REPORT_DIR=target\money\accessibility_testing_result"
set "HISTORY_DIR=target\money\accessibility_testing_result_history"

echo.
echo === Airpay Money accessibility ===
echo Mode:   %MODE%
echo Report: %REPORT_DIR%
echo.

call :resolveJava
if errorlevel 1 (
  echo Java was not found. Install JDK 17+ and set JAVA_HOME, then rerun.
  pause
  exit /b 1
)
call :resolveMaven
if errorlevel 1 (
  echo Maven was not found. Install Apache Maven ^(M2_HOME / MAVEN_HOME / PATH^), then rerun.
  pause
  exit /b 1
)
echo JAVA_HOME=%JAVA_HOME%
echo M2_HOME=%M2_HOME%
if defined CLASSWORLDS_JAR echo Maven launcher=%CLASSWORLDS_JAR%
echo.

if /i "%MODE%"=="deep" goto :prepDeep
if exist "%REPORT_DIR%" rmdir /S /Q "%REPORT_DIR%" 2>nul
if not exist "%REPORT_DIR%" mkdir "%REPORT_DIR%"
goto :afterPrep

:prepDeep
if exist "%REPORT_DIR%\money-scan.json" (
  echo Archiving previous scorecard...
  if not exist "%HISTORY_DIR%" mkdir "%HISTORY_DIR%"
  for /f %%I in ('powershell -NoProfile -Command "Get-Date -Format yyyyMMdd-HHmmss"') do set "STAMP=%%I"
  set "ARCHIVE=%HISTORY_DIR%\!STAMP!"
  mkdir "!ARCHIVE!" >nul 2>&1
  xcopy /E /I /Y /Q "%REPORT_DIR%\*" "!ARCHIVE!\" >nul
  powershell -NoProfile -Command ^
    "$h='%HISTORY_DIR%'; if (Test-Path $h) {" ^
    "  Get-ChildItem $h -Directory | Sort-Object Name -Descending | Select-Object -Skip 2 |" ^
    "    ForEach-Object { Remove-Item $_.FullName -Recurse -Force }" ^
    "}"
  rmdir /S /Q "%REPORT_DIR%" 2>nul
)
if not exist "%REPORT_DIR%" mkdir "%REPORT_DIR%"

:afterPrep
call scripts\_lib\prepare-device.bat money %PREFERRED_UDID%
if errorlevel 1 (
  pause
  exit /b 1
)
set "UDID="
for /f "usebackq tokens=1,* delims==" %%A in ("target\money\prepare.env") do set "%%A=%%B"
echo Using UDID=%UDID%

set "TAGS=@MONEY_001 and @deep"
set "EXTRA=-Da11y.deep.scan=true"
if /i "%MODE%"=="two" (
  set "TAGS=@MONEY_TWO_001 and @live-two"
  set "EXTRA=-Da11y.deep.scan=false -Da11y.live.wait.seconds=300"
)

if defined CLASSWORLDS_JAR (
  "%JAVA_HOME%\bin\java.exe" "-Dmaven.home=%M2_HOME%" "-Dmaven.multiModuleProjectDirectory=%CD%" -classpath "%CLASSWORLDS_JAR%" "-Dclassworlds.conf=%M2_HOME%\bin\m2.conf" org.codehaus.plexus.classworlds.launcher.Launcher test -pl airpay_money -am "-DforkCount=0" "-Dtest=MoneyRunner" "-Dsurefire.failIfNoSpecifiedTests=false" "-Dcucumber.filter.tags=%TAGS%" "-Dairpay.product=money" "-Dandroid.udid=%UDID%" "-Da11y.enabled=true" "-Da11y.gate.strict=false" "-Dandroid.keep.session=true" %EXTRA%
) else (
  call mvn test -pl airpay_money -am "-DforkCount=0" "-Dtest=MoneyRunner" "-Dsurefire.failIfNoSpecifiedTests=false" "-Dcucumber.filter.tags=%TAGS%" "-Dairpay.product=money" "-Dandroid.udid=%UDID%" "-Da11y.enabled=true" "-Da11y.gate.strict=false" "-Dandroid.keep.session=true" %EXTRA%
)
set "EXIT_CODE=%ERRORLEVEL%"

echo.
echo Scorecard: %CD%\%REPORT_DIR%\money-scorecard.html
echo ZIP:       %CD%\target\money\accessibility_email_artifact\money-a11y-reports.zip
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
