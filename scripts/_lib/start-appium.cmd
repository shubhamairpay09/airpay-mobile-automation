@echo off
setlocal
rem Starts the Appium server on 127.0.0.1:4723. The window stays open while the server runs.
set "APPIUM_HOME=C:\Users\shubham.verma\.appium"
set "APPIUM_CMD=C:\Users\shubham.verma\AppData\Roaming\npm\appium.cmd"
set "ANDROID_HOME=D:\Android_Studio\Sdk"
set "ANDROID_SDK_ROOT=D:\Android_Studio\Sdk"
set "PATH=%ANDROID_HOME%\platform-tools;%PATH%"
if not exist "%APPIUM_CMD%" set "APPIUM_CMD=appium"
echo Using APPIUM_HOME=%APPIUM_HOME%
echo Using ANDROID_HOME=%ANDROID_HOME%
"%APPIUM_CMD%" --address 127.0.0.1 --port 4723
exit /b %ERRORLEVEL%
