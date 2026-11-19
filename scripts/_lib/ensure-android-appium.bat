@echo off
rem Find Android platform-tools, Node, Appium, and the UiAutomator2 driver.
rem Downloads a portable copy into %LOCALAPPDATA%\airpay-mobile-tools when one is missing.
rem Starts nothing. prepare-device.bat starts Appium when port 4723 is closed.
rem No setlocal: ANDROID_HOME, PATH, and APPIUM_CMD stay set for the caller.

set "AIRPAY_TOOLS=%LOCALAPPDATA%\airpay-mobile-tools"
if not exist "%AIRPAY_TOOLS%" mkdir "%AIRPAY_TOOLS%"

if exist "%JAVA_HOME%\bin\java.exe" goto :haveJava
call "%~dp0ensure-java-maven.bat"
if errorlevel 1 exit /b 1
:haveJava

call :sdkReady
if errorlevel 1 call :installSdk
call :sdkReady
if errorlevel 1 goto :sdkMissing
call :buildToolsReady
if errorlevel 1 call :installBuildTools
call :buildToolsReady
if errorlevel 1 goto :buildToolsMissing

call :nodeReady
if errorlevel 1 call :installNode
call :nodeReady
if errorlevel 1 goto :nodeMissing

call :appiumReady
if errorlevel 1 call :installAppium
call :appiumReady
if errorlevel 1 goto :appiumMissing

call :driverReady
if errorlevel 1 call :installDriver
call :driverReady
if errorlevel 1 goto :driverMissing

set "ANDROID_SDK_ROOT=%ANDROID_HOME%"
set "PATH=%ANDROID_HOME%\platform-tools;%NODE_HOME%;%PATH%"
if defined NPM_PREFIX set "PATH=%NPM_PREFIX%;%PATH%"
echo [toolchain] Android SDK: %ANDROID_HOME%
echo [toolchain] Node:        %NODE_HOME%
echo [toolchain] Appium:      %APPIUM_CMD%
echo [toolchain] Driver:      uiautomator2
exit /b 0

:sdkMissing
echo [toolchain] Android platform-tools are still missing.
exit /b 1

:buildToolsMissing
echo [toolchain] Android build-tools are still missing. UiAutomator2 needs them.
exit /b 1

:nodeMissing
echo [toolchain] Node.js 20.19+ or 22+ is still missing. Appium needs it.
exit /b 1

:appiumMissing
echo [toolchain] Appium is still missing.
exit /b 1

:driverMissing
echo [toolchain] The UiAutomator2 driver is still missing.
exit /b 1

:sdkReady
if defined ANDROID_HOME for /f "delims=" %%A in ("%ANDROID_HOME%") do set "ANDROID_HOME=%%~A"
if defined ANDROID_SDK_ROOT for /f "delims=" %%A in ("%ANDROID_SDK_ROOT%") do set "ANDROID_SDK_ROOT=%%~A"
if not defined ANDROID_HOME if defined ANDROID_SDK_ROOT set "ANDROID_HOME=%ANDROID_SDK_ROOT%"
if not defined ANDROID_HOME if exist "%LOCALAPPDATA%\Android\Sdk\platform-tools\adb.exe" set "ANDROID_HOME=%LOCALAPPDATA%\Android\Sdk"
if not defined ANDROID_HOME if exist "D:\Android_Studio\Sdk\platform-tools\adb.exe" set "ANDROID_HOME=D:\Android_Studio\Sdk"
if not defined ANDROID_HOME if exist "C:\Android\Sdk\platform-tools\adb.exe" set "ANDROID_HOME=C:\Android\Sdk"
if not defined ANDROID_HOME if exist "%AIRPAY_TOOLS%\android-sdk\platform-tools\adb.exe" set "ANDROID_HOME=%AIRPAY_TOOLS%\android-sdk"
if not exist "%ANDROID_HOME%\platform-tools\adb.exe" exit /b 1
set "ANDROID_SDK_ROOT=%ANDROID_HOME%"
exit /b 0

:buildToolsReady
set "TOOLS_OK="
if exist "%ANDROID_HOME%\build-tools" for /d %%D in ("%ANDROID_HOME%\build-tools\*") do (
  if exist "%%~fD\aapt.exe" set "TOOLS_OK=1"
  if exist "%%~fD\aapt2.exe" set "TOOLS_OK=1"
)
if defined TOOLS_OK exit /b 0
exit /b 1

:installSdk
call :needCurl
if errorlevel 1 exit /b 1
if not defined ANDROID_HOME set "ANDROID_HOME=%AIRPAY_TOOLS%\android-sdk"
echo [toolchain] Android platform-tools were not found. Downloading the SDK command-line tools.
call :installCmdlineTools
if errorlevel 1 exit /b 1
call :acceptLicenses
echo [toolchain] Installing platform-tools and build-tools. This can take a few minutes.
call :sdkInstall "platform-tools" "build-tools;35.0.0"
if errorlevel 1 echo [toolchain] sdkmanager could not install platform-tools.
if errorlevel 1 exit /b 1
exit /b 0

:installBuildTools
call :installCmdlineTools
if errorlevel 1 exit /b 1
call :acceptLicenses
echo [toolchain] Installing Android build-tools for UiAutomator2.
call :sdkInstall "build-tools;35.0.0"
if errorlevel 1 call :sdkInstall "build-tools;36.0.0"
exit /b %ERRORLEVEL%

:installCmdlineTools
if exist "%ANDROID_HOME%\cmdline-tools\latest\bin\sdkmanager.bat" set "SDKMANAGER=%ANDROID_HOME%\cmdline-tools\latest\bin\sdkmanager.bat"
if exist "%ANDROID_HOME%\cmdline-tools\latest\bin\sdkmanager.bat" exit /b 0
call :needCurl
if errorlevel 1 exit /b 1
if not exist "%ANDROID_HOME%" mkdir "%ANDROID_HOME%"
if exist "%AIRPAY_TOOLS%\cmdline-extract" rmdir /s /q "%AIRPAY_TOOLS%\cmdline-extract"
mkdir "%AIRPAY_TOOLS%\cmdline-extract"
set "CMDLINE_ZIP=%AIRPAY_TOOLS%\commandlinetools.zip"
curl.exe -fL --retry 3 --retry-delay 2 -o "%CMDLINE_ZIP%" "https://dl.google.com/android/repository/commandlinetools-win-13114758_latest.zip"
if errorlevel 1 curl.exe -fL --retry 3 --retry-delay 2 -o "%CMDLINE_ZIP%" "https://dl.google.com/android/repository/commandlinetools-win-11076708_latest.zip"
if errorlevel 1 echo [toolchain] Could not download the Android command-line tools.
if errorlevel 1 exit /b 1
tar.exe -xf "%CMDLINE_ZIP%" -C "%AIRPAY_TOOLS%\cmdline-extract"
if errorlevel 1 echo [toolchain] Could not unpack the Android command-line tools.
if errorlevel 1 exit /b 1
if not exist "%ANDROID_HOME%\cmdline-tools\latest" mkdir "%ANDROID_HOME%\cmdline-tools\latest"
xcopy "%AIRPAY_TOOLS%\cmdline-extract\cmdline-tools\*" "%ANDROID_HOME%\cmdline-tools\latest\" /E /I /Y >nul
if not exist "%ANDROID_HOME%\cmdline-tools\latest\bin\sdkmanager.bat" echo [toolchain] sdkmanager.bat was not in the command-line tools zip.
if not exist "%ANDROID_HOME%\cmdline-tools\latest\bin\sdkmanager.bat" exit /b 1
set "SDKMANAGER=%ANDROID_HOME%\cmdline-tools\latest\bin\sdkmanager.bat"
exit /b 0

:acceptLicenses
(for /l %%N in (1,1,40) do @echo y) | call "%SDKMANAGER%" --sdk_root="%ANDROID_HOME%" --licenses >nul 2>&1
exit /b 0

:sdkInstall
(for /l %%N in (1,1,8) do @echo y) | call "%SDKMANAGER%" --sdk_root="%ANDROID_HOME%" %* >"%TEMP%\airpay-sdk-out.txt" 2>&1
type "%TEMP%\airpay-sdk-out.txt"
findstr /i /c:"Exception" /c:"Failed" /c:"not available" "%TEMP%\airpay-sdk-out.txt" >nul
if not errorlevel 1 exit /b 1
exit /b 0

:nodeReady
if defined NODE_HOME if exist "%NODE_HOME%\node.exe" goto :nodeVersion
if exist "%AIRPAY_TOOLS%\node\node.exe" set "NODE_HOME=%AIRPAY_TOOLS%\node"
if exist "%AIRPAY_TOOLS%\node\node.exe" goto :nodeVersion
set "NODE_FOUND="
for /f "delims=" %%N in ('where node 2^>nul') do if not defined NODE_FOUND if /i "%%~nxN"=="node.exe" (
  set "NODE_HOME=%%~dpN"
  set "NODE_FOUND=1"
)
if not defined NODE_FOUND exit /b 1
:nodeVersion
set "PATH=%NODE_HOME%;%PATH%"
"%NODE_HOME%\node.exe" -v >"%TEMP%\airpay-node-ver.txt" 2>&1
findstr /r /c:"^v2[2-9]\." /c:"^v[3-9][0-9]\." /c:"^v20\.19" /c:"^v20\.[2-9][0-9]" "%TEMP%\airpay-node-ver.txt" >nul
exit /b %ERRORLEVEL%

:installNode
call :needCurl
if errorlevel 1 exit /b 1
echo [toolchain] Node.js 20.19+ was not found. Downloading the latest Node 22 release.
curl.exe -fL --retry 3 --retry-delay 2 -o "%TEMP%\airpay-node-sums.txt" "https://nodejs.org/dist/latest-v22.x/SHASUMS256.txt"
if errorlevel 1 echo [toolchain] Could not read the Node.js release list.
if errorlevel 1 exit /b 1
set "NODE_ZIP="
for /f "tokens=2" %%N in ('findstr /c:"win-x64.zip" "%TEMP%\airpay-node-sums.txt"') do if not defined NODE_ZIP set "NODE_ZIP=%%N"
if not defined NODE_ZIP echo [toolchain] The Node.js release list had no Windows x64 zip.
if not defined NODE_ZIP exit /b 1
curl.exe -fL --retry 3 --retry-delay 2 -o "%AIRPAY_TOOLS%\node.zip" "https://nodejs.org/dist/latest-v22.x/%NODE_ZIP%"
if errorlevel 1 echo [toolchain] Node.js download failed.
if errorlevel 1 exit /b 1
if exist "%AIRPAY_TOOLS%\node-extract" rmdir /s /q "%AIRPAY_TOOLS%\node-extract"
mkdir "%AIRPAY_TOOLS%\node-extract"
tar.exe -xf "%AIRPAY_TOOLS%\node.zip" -C "%AIRPAY_TOOLS%\node-extract"
set "NODE_SRC="
for /d %%D in ("%AIRPAY_TOOLS%\node-extract\node-*") do if exist "%%~fD\node.exe" set "NODE_SRC=%%~fD"
if not defined NODE_SRC echo [toolchain] The Node.js zip did not contain node.exe.
if not defined NODE_SRC exit /b 1
if exist "%AIRPAY_TOOLS%\node" rmdir /s /q "%AIRPAY_TOOLS%\node"
move "%NODE_SRC%" "%AIRPAY_TOOLS%\node" >nul
set "NODE_HOME=%AIRPAY_TOOLS%\node"
set "PATH=%NODE_HOME%;%PATH%"
exit /b 0

:appiumReady
if defined APPIUM_CMD if exist "%APPIUM_CMD%" goto :appiumVersion
set "APPIUM_CMD="
if exist "%APPDATA%\npm\appium.cmd" set "APPIUM_CMD=%APPDATA%\npm\appium.cmd"
if not defined APPIUM_CMD if exist "%LOCALAPPDATA%\npm\appium.cmd" set "APPIUM_CMD=%LOCALAPPDATA%\npm\appium.cmd"
if not defined APPIUM_CMD if exist "%AIRPAY_TOOLS%\npm\appium.cmd" set "APPIUM_CMD=%AIRPAY_TOOLS%\npm\appium.cmd"
if not defined APPIUM_CMD for /f "delims=" %%A in ('where appium.cmd 2^>nul') do if not defined APPIUM_CMD set "APPIUM_CMD=%%~fA"
if not defined APPIUM_CMD exit /b 1
:appiumVersion
call "%APPIUM_CMD%" -v >"%TEMP%\airpay-appium-ver.txt" 2>&1
if errorlevel 1 exit /b 1
findstr /r /c:"^3\." /c:"^2\." "%TEMP%\airpay-appium-ver.txt" >nul
if errorlevel 1 echo [toolchain] Appium at %APPIUM_CMD% is not version 2 or 3.
if errorlevel 1 type "%TEMP%\airpay-appium-ver.txt"
if errorlevel 1 exit /b 1
exit /b 0

:installAppium
if not defined NPM_PREFIX set "NPM_PREFIX=%AIRPAY_TOOLS%\npm"
if not exist "%NPM_PREFIX%" mkdir "%NPM_PREFIX%"
echo [toolchain] Appium was not found. Installing Appium 3 with npm. This can take a few minutes.
call "%NODE_HOME%\npm.cmd" install -g --prefix "%NPM_PREFIX%" appium@3
if errorlevel 1 echo [toolchain] npm could not install Appium.
if errorlevel 1 exit /b 1
set "APPIUM_CMD=%NPM_PREFIX%\appium.cmd"
set "PATH=%NPM_PREFIX%;%PATH%"
exit /b 0

:driverReady
call "%APPIUM_CMD%" driver list --installed >"%TEMP%\airpay-appium-drivers.txt" 2>&1
if errorlevel 1 exit /b 1
findstr /i /c:"uiautomator2@" "%TEMP%\airpay-appium-drivers.txt" >nul
exit /b %ERRORLEVEL%

:installDriver
echo [toolchain] Installing the Appium UiAutomator2 driver.
if not defined APPIUM_HOME set "APPIUM_HOME=%USERPROFILE%\.appium"
call "%APPIUM_CMD%" driver install uiautomator2
if errorlevel 1 echo [toolchain] UiAutomator2 driver install failed.
if errorlevel 1 exit /b 1
exit /b 0

:needCurl
where curl.exe >nul 2>&1
if errorlevel 1 echo [toolchain] curl.exe was not found. Windows 10 and later include it.
if errorlevel 1 exit /b 1
where tar.exe >nul 2>&1
if errorlevel 1 echo [toolchain] tar.exe was not found. Windows 10 and later include it.
if errorlevel 1 exit /b 1
exit /b 0
