@echo off
setlocal EnableDelayedExpansion
rem Starts Appium on 127.0.0.1:4723. Exports ANDROID_HOME into THIS process
rem so UiAutomator2 can find the SDK even when Appium was launched by a bat.

rem ---- Android SDK (env first, then common Studio locations) ----
if defined ANDROID_HOME for /f "delims=" %%A in ("%ANDROID_HOME%") do set "ANDROID_HOME=%%~A"
if defined ANDROID_SDK_ROOT for /f "delims=" %%A in ("%ANDROID_SDK_ROOT%") do set "ANDROID_SDK_ROOT=%%~A"
if not defined ANDROID_HOME if defined ANDROID_SDK_ROOT set "ANDROID_HOME=%ANDROID_SDK_ROOT%"
if not defined ANDROID_HOME if exist "%LOCALAPPDATA%\Android\Sdk\platform-tools\adb.exe" set "ANDROID_HOME=%LOCALAPPDATA%\Android\Sdk"
if not defined ANDROID_HOME if exist "D:\Android_Studio\Sdk\platform-tools\adb.exe" set "ANDROID_HOME=D:\Android_Studio\Sdk"
if not defined ANDROID_HOME if exist "C:\Android\Sdk\platform-tools\adb.exe" set "ANDROID_HOME=C:\Android\Sdk"
if not defined ANDROID_HOME (
  echo ANDROID_HOME is not set and no SDK was found under %%LOCALAPPDATA%%\Android\Sdk
  echo Set ANDROID_HOME to your Android Studio SDK folder, open a NEW CMD, then rerun.
  exit /b 1
)
if not exist "%ANDROID_HOME%\platform-tools\adb.exe" (
  echo ANDROID_HOME=%ANDROID_HOME% but platform-tools\adb.exe is missing.
  exit /b 1
)
set "ANDROID_SDK_ROOT=%ANDROID_HOME%"
set "PATH=%ANDROID_HOME%\platform-tools;%ANDROID_HOME%\emulator;%PATH%"

rem ---- Appium CLI ----
set "APPIUM_CMD="
if exist "%APPDATA%\npm\appium.cmd" set "APPIUM_CMD=%APPDATA%\npm\appium.cmd"
if not defined APPIUM_CMD if exist "%LOCALAPPDATA%\npm\appium.cmd" set "APPIUM_CMD=%LOCALAPPDATA%\npm\appium.cmd"
if not defined APPIUM_CMD set "APPIUM_CMD=appium"
if defined USERPROFILE if exist "%USERPROFILE%\.appium" set "APPIUM_HOME=%USERPROFILE%\.appium"

echo Using ANDROID_HOME=%ANDROID_HOME%
echo Using ANDROID_SDK_ROOT=%ANDROID_SDK_ROOT%
echo Using APPIUM_CMD=%APPIUM_CMD%
"%APPIUM_CMD%" --address 127.0.0.1 --port 4723
exit /b %ERRORLEVEL%
