@echo off
setlocal EnableDelayedExpansion
rem Airpay Money accessibility — Products DEEP (ordered).
rem Order: Products → Investments (MF deep → NPS → Gold → FD → US → Indian)
rem        → Insurance → Loans & Credit Cards → Personal Finance.
rem Mutual Funds follows docs\analysis\money-products-deep-scan-flow.md
rem FinSight is deferred until Products deep is stable.
rem Before each run: archive scorecard (keep last 2), then fresh report.
rem Keeps Money in foreground (no system Back to launcher). Fast settles only.

cd /d "%~dp0..\.."

if not defined JAVA_HOME set "JAVA_HOME=C:\Program Files\Java\jdk-21.0.11"
set "M2_HOME=C:\Tools\apache-maven-3.9.16"

set "REPORT_DIR=target\money\accessibility_testing_result"
set "HISTORY_DIR=target\money\accessibility_testing_result_history"

echo.
echo === Airpay Money accessibility (Products DEEP, fresh report) ===
echo Order:  Investments (MF deep first) → Insurance → Loans → Tax; FinSight later
echo Flow:   docs\analysis\money-products-deep-scan-flow.md
echo Report: %REPORT_DIR%
echo Keep:   last 2 reports under %HISTORY_DIR%
echo ZIP:    target\money\accessibility_email_artifact\money-a11y-reports.zip
echo Logs:   target\money\logs\automation.log
echo Phone should already be logged in. App stays in foreground.
echo.

rem --- Archive current report (if any), keep only the last 2 history folders, clear for a new run ---
if exist "%REPORT_DIR%\money-scan.json" (
  echo Archiving previous scorecard...
  if not exist "%HISTORY_DIR%" mkdir "%HISTORY_DIR%"
  for /f %%I in ('powershell -NoProfile -Command "Get-Date -Format yyyyMMdd-HHmmss"') do set "STAMP=%%I"
  set "ARCHIVE=%HISTORY_DIR%\!STAMP!"
  mkdir "!ARCHIVE!" >nul 2>&1
  xcopy /E /I /Y /Q "%REPORT_DIR%\*" "!ARCHIVE!\" >nul
  if exist "!ARCHIVE!\money-scorecard.html" (
    echo Saved previous report: !ARCHIVE!
  ) else if exist "!ARCHIVE!\money-scan.json" (
    echo Saved previous report: !ARCHIVE!
  )
  powershell -NoProfile -Command ^
    "$h='%HISTORY_DIR%'; if (Test-Path $h) {" ^
    "  Get-ChildItem $h -Directory | Sort-Object Name -Descending | Select-Object -Skip 2 |" ^
    "    ForEach-Object { Write-Host ('Removing older archive: ' + $_.FullName); Remove-Item $_.FullName -Recurse -Force }" ^
    "}"
  echo Clearing current report folder for a fresh scan...
  rmdir /S /Q "%REPORT_DIR%" 2>nul
)
if not exist "%REPORT_DIR%" mkdir "%REPORT_DIR%"
echo Fresh report folder ready: %REPORT_DIR%
echo.

call scripts\_lib\prepare-device.bat money
if errorlevel 1 (
  pause
  exit /b 1
)
set "UDID="
for /f "usebackq tokens=1,* delims==" %%A in ("target\money\prepare.env") do set "%%A=%%B"

"%JAVA_HOME%\bin\java.exe" "-Dmaven.home=%M2_HOME%" "-Dmaven.multiModuleProjectDirectory=%CD%" -classpath "%M2_HOME%\boot\plexus-classworlds-2.11.0.jar" "-Dclassworlds.conf=%M2_HOME%\bin\m2.conf" org.codehaus.plexus.classworlds.launcher.Launcher test -pl airpay_money -am "-DforkCount=0" "-Dtest=MoneyRunner" "-Dsurefire.failIfNoSpecifiedTests=false" "-Dcucumber.filter.tags=@MONEY_001 and @deep" "-Dairpay.product=money" "-Dandroid.udid=%UDID%" "-Da11y.enabled=true" "-Da11y.deep.scan=true" "-Da11y.gate.strict=false" "-Dandroid.keep.session=true"
set "EXIT_CODE=%ERRORLEVEL%"

echo.
echo Current scorecard: %CD%\%REPORT_DIR%\money-scorecard.html
echo History (last 2):  %CD%\%HISTORY_DIR%
echo ZIP:               %CD%\target\money\accessibility_email_artifact\money-a11y-reports.zip
echo Logs:              %CD%\target\money\logs\automation.log
echo Cucumber:          %CD%\target\money\cucumber-reports\cucumber.html
echo.
pause
exit /b %EXIT_CODE%
