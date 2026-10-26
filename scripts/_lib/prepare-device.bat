@echo off
setlocal EnableDelayedExpansion
rem Before functional or accessibility tests:
rem   1. Start Appium when it is not already running.
rem   2. Record the connected phone.
rem   3. Confirm this project's app is installed.
rem Writes target\<product>\device-info.txt and target\<product>\prepare.env
rem Usage: prepare-device.bat <money|capital|vyaapaar> [preferred-udid]

set "PRODUCT=%~1"
set "PREFERRED=%~2"
if "%PRODUCT%"=="" (
  echo PRODUCT is required.
  exit /b 1
)
set "ROOT=%~dp0..\.."
cd /d "%ROOT%"

set "ADB=D:\Android_Studio\Sdk\platform-tools\adb.exe"
if not exist "%ADB%" set "ADB=adb"
set "APPIUM_CMD=C:\Users\shubham.verma\AppData\Roaming\npm\appium.cmd"
if not exist "%APPIUM_CMD%" set "APPIUM_CMD=appium"

set "APP_PACKAGE="
for /f "usebackq tokens=1,* delims==" %%A in (`findstr /b /c:"%PRODUCT%.app.package=" "config\%PRODUCT%.properties"`) do (
  set "APP_PACKAGE=%%B"
)
if "!APP_PACKAGE!"=="" (
  echo No %PRODUCT%.app.package in config\%PRODUCT%.properties
  exit /b 1
)

if not exist "target\%PRODUCT%" mkdir "target\%PRODUCT%"
if not exist "target\%PRODUCT%\logs" mkdir "target\%PRODUCT%\logs"
set "INFO=target\%PRODUCT%\device-info.txt"
set "ENVFILE=target\%PRODUCT%\prepare.env"

echo.
echo === Device setup for %PRODUCT% ===

call :appiumUp
if not errorlevel 1 goto :appiumReady
echo Appium is not running. Starting it.
where appium >nul 2>&1
if errorlevel 1 if not exist "C:\Users\shubham.verma\AppData\Roaming\npm\appium.cmd" (
  echo Appium was not found. Install it with: npm install -g appium
  exit /b 1
)
start "Airpay Appium" cmd /c call "%~dp0start-appium.cmd"
set /a TRIES=0
:waitAppium
call :appiumUp
if not errorlevel 1 goto :appiumReady
set /a TRIES+=1
if !TRIES! GEQ 45 (
  echo Appium did not answer at http://127.0.0.1:4723
  exit /b 1
)
echo Waiting for Appium... !TRIES!
timeout /t 2 /nobreak >nul
goto :waitAppium
:appiumReady
echo Appium is running at http://127.0.0.1:4723

"%ADB%" start-server >nul 2>&1
"%ADB%" devices -l > "%INFO%.devices"
set "FIRST="
set "PICK="
set /a COUNT=0
for /f "skip=1 tokens=1,2" %%A in ('"%ADB%" devices') do (
  if /i "%%B"=="device" (
    set /a COUNT+=1
    if not defined FIRST set "FIRST=%%A"
    if /i "%%A"=="%PREFERRED%" set "PICK=%%A"
  )
)
if !COUNT! EQU 0 (
  echo No phone is connected. Plug in the phone, unlock it, and allow USB debugging.
  echo Saved device list: %CD%\%INFO%
  call :writeInfo missing
  exit /b 1
)
if "!PICK!"=="" set "PICK=!FIRST!"
if !COUNT! GTR 1 if /i not "!PICK!"=="%PREFERRED%" (
  echo More than one phone is connected. Using !PICK!.
)

set "MODEL="
set "ANDROID_VER="
for /f "delims=" %%M in ('"%ADB%" -s !PICK! shell getprop ro.product.model') do set "MODEL=%%M"
for /f "delims=" %%M in ('"%ADB%" -s !PICK! shell getprop ro.build.version.release') do set "ANDROID_VER=%%M"

set "PKGPATH="
for /f "delims=" %%P in ('"%ADB%" -s !PICK! shell pm path !APP_PACKAGE!') do set "PKGPATH=%%P"
if "!PKGPATH!"=="" (
  echo !APP_PACKAGE! is not installed on !PICK!.
  echo Install the %PRODUCT% app on the phone, then run again.
  call :writeInfo missing-app
  exit /b 1
)

"%ADB%" -s !PICK! shell am force-stop io.appium.uiautomator2.server >nul 2>&1
"%ADB%" -s !PICK! shell am force-stop io.appium.uiautomator2.server.test >nul 2>&1

call :writeInfo ready
> "!ENVFILE!" echo UDID=!PICK!
>> "!ENVFILE!" echo APP_PACKAGE=!APP_PACKAGE!

echo Phone:    !PICK!  !MODEL!  Android !ANDROID_VER!
echo App:      !APP_PACKAGE!
echo Saved:    %CD%\!INFO!
echo.
exit /b 0

:writeInfo
> "!INFO!" echo product=%PRODUCT%
>> "!INFO!" echo status=%~1
>> "!INFO!" echo appium=http://127.0.0.1:4723
>> "!INFO!" echo udid=!PICK!
>> "!INFO!" echo model=!MODEL!
>> "!INFO!" echo android=!ANDROID_VER!
>> "!INFO!" echo package=!APP_PACKAGE!
>> "!INFO!" echo packagePath=!PKGPATH!
>> "!INFO!" echo.
>> "!INFO!" echo adb devices -l
type "!INFO!.devices" >> "!INFO!"
del "!INFO!.devices" >nul 2>&1
exit /b 0

:appiumUp
powershell -NoProfile -Command "try { if ((Invoke-WebRequest -UseBasicParsing -TimeoutSec 3 http://127.0.0.1:4723/status).StatusCode -eq 200) { exit 0 } else { exit 1 } } catch { exit 1 }"
exit /b !ERRORLEVEL!
