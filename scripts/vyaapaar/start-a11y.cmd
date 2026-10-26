@echo off
setlocal EnableDelayedExpansion
rem Wakes the phone, then starts the single Vyaapaar accessibility batch file.
rem Uses UDID when set, else the first connected device (never a fixed serial).
cd /d "%~dp0..\.."

set "ADB=D:\Android_Studio\Sdk\platform-tools\adb.exe"
if not exist "%ADB%" set "ADB=adb"

if not defined UDID (
  for /f "skip=1 tokens=1,2" %%A in ('"%ADB%" devices 2^>nul') do (
    if "%%B"=="device" if not defined UDID set "UDID=%%A"
  )
)
if not defined UDID (
  echo No phone is connected. Connect one with USB debugging on, then rerun.
  pause
  exit /b 1
)
echo Waking phone: !UDID!
"%ADB%" -s !UDID! shell input keyevent 224
"%ADB%" -s !UDID! shell svc power stayon true

call "%~dp0a11y-run.bat"
exit /b %ERRORLEVEL%
