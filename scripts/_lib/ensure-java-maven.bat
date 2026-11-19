@echo off
rem Find JDK 21 or newer and Maven, or download portable copies into %LOCALAPPDATA%\airpay-mobile-tools.
rem JDK 27 at C:\Program Files\Java\jdk-27.0.1.0.3 is preferred for Capital, Money, and Vyaapaar.
rem No setlocal: JAVA_HOME, M2_HOME, and PATH stay set for the caller.
rem Exit: 0=ok, 1=Java missing, 2=Maven missing

set "PREFERRED_JAVA=C:\Program Files\Java\jdk-27.0.1.0.3"
set "AIRPAY_TOOLS=%LOCALAPPDATA%\airpay-mobile-tools"
if not exist "%AIRPAY_TOOLS%" mkdir "%AIRPAY_TOOLS%"

call :javaReady
if errorlevel 1 call :installJava
call :javaReady
if errorlevel 1 goto :javaMissing

call :mavenReady
if errorlevel 1 call :installMaven
call :mavenReady
if errorlevel 1 goto :mavenMissing

set "PATH=%JAVA_HOME%\bin;%M2_HOME%\bin;%PATH%"
echo [toolchain] Java:  %JAVA_HOME%
echo [toolchain] Maven: %M2_HOME%
exit /b 0

:javaMissing
echo [toolchain] JDK 21 or newer is not available. Install JDK 27, or set JAVA_HOME to the folder that contains bin\java.exe.
exit /b 1

:mavenMissing
echo [toolchain] Maven is not available. Install Maven 3.9 or newer and set M2_HOME to the folder that contains bin\mvn.cmd.
exit /b 2

:javaReady
if not exist "%PREFERRED_JAVA%\bin\java.exe" goto :javaEnv
call :useIfSupportedJdk "%PREFERRED_JAVA%"
if not errorlevel 1 exit /b 0
:javaEnv
if not defined JAVA_HOME goto :javaClear
call :useIfSupportedJdk "%JAVA_HOME%"
if not errorlevel 1 exit /b 0
:javaClear
set "JAVA_HOME="
if not exist "%AIRPAY_TOOLS%\jdk-21\bin\java.exe" goto :java2111
call :useIfSupportedJdk "%AIRPAY_TOOLS%\jdk-21"
if not errorlevel 1 exit /b 0
:java2111
if not exist "C:\Program Files\Java\jdk-21.0.11\bin\java.exe" goto :java21
call :useIfSupportedJdk "C:\Program Files\Java\jdk-21.0.11"
if not errorlevel 1 exit /b 0
:java21
if not exist "C:\Program Files\Java\jdk-21\bin\java.exe" goto :javaAdoptium
call :useIfSupportedJdk "C:\Program Files\Java\jdk-21"
if not errorlevel 1 exit /b 0
:javaAdoptium
if not exist "C:\Program Files\Eclipse Adoptium\jdk-21.0.11-hotspot\bin\java.exe" goto :javaScan
call :useIfSupportedJdk "C:\Program Files\Eclipse Adoptium\jdk-21.0.11-hotspot"
if not errorlevel 1 exit /b 0
:javaScan
for /d %%D in ("C:\Program Files\Java\jdk-*") do (
  call :useIfSupportedJdk "%%~fD"
  if not errorlevel 1 goto :javaYes
)
for /d %%D in ("C:\Program Files\Eclipse Adoptium\jdk-*") do (
  call :useIfSupportedJdk "%%~fD"
  if not errorlevel 1 goto :javaYes
)
for /d %%D in ("C:\Program Files\Microsoft\jdk-*") do (
  call :useIfSupportedJdk "%%~fD"
  if not errorlevel 1 goto :javaYes
)
for /f "delims=" %%J in ('where java 2^>nul') do (
  for %%D in ("%%~dpJ..") do call :useIfSupportedJdk "%%~fD"
  if not errorlevel 1 goto :javaYes
)
exit /b 1
:javaYes
exit /b 0

:useIfSupportedJdk
if not exist "%~1\bin\java.exe" exit /b 1
"%~1\bin\java.exe" -version >"%TEMP%\airpay-java-ver.txt" 2>&1
findstr /r /c:"version .21" /c:"version .2[2-9]" /c:"version .[3-9][0-9]" "%TEMP%\airpay-java-ver.txt" >nul
if errorlevel 1 exit /b 1
for /f "delims=" %%A in ("%~1") do set "JAVA_HOME=%%~fA"
exit /b 0

:installJava
call :needCurl
if errorlevel 1 exit /b 1
echo [toolchain] JDK 21 was not found. Downloading Temurin 21 into %AIRPAY_TOOLS%\jdk-21
if exist "%AIRPAY_TOOLS%\jdk-extract" rmdir /s /q "%AIRPAY_TOOLS%\jdk-extract"
mkdir "%AIRPAY_TOOLS%\jdk-extract"
curl.exe -fL --retry 3 --retry-delay 2 -o "%AIRPAY_TOOLS%\jdk-21.zip" "https://api.adoptium.net/v3/binary/latest/21/ga/windows/x64/jdk/hotspot/normal/eclipse?project=jdk"
if not errorlevel 1 goto :jdkUnpack
echo [toolchain] Temurin download failed. Trying the Microsoft JDK 21 build.
curl.exe -fL --retry 3 --retry-delay 2 -o "%AIRPAY_TOOLS%\jdk-21.zip" "https://aka.ms/download-jdk/microsoft-jdk-21-windows-x64.zip"
if not errorlevel 1 goto :jdkUnpack
echo [toolchain] JDK download failed.
exit /b 1
:jdkUnpack
tar.exe -xf "%AIRPAY_TOOLS%\jdk-21.zip" -C "%AIRPAY_TOOLS%\jdk-extract"
if errorlevel 1 echo [toolchain] Could not unpack the JDK zip.
if errorlevel 1 exit /b 1
set "JDK_SRC="
for /d %%D in ("%AIRPAY_TOOLS%\jdk-extract\*") do if exist "%%~fD\bin\java.exe" set "JDK_SRC=%%~fD"
if not defined JDK_SRC echo [toolchain] The JDK zip did not contain bin\java.exe.
if not defined JDK_SRC exit /b 1
if exist "%AIRPAY_TOOLS%\jdk-21" rmdir /s /q "%AIRPAY_TOOLS%\jdk-21"
move "%JDK_SRC%" "%AIRPAY_TOOLS%\jdk-21" >nul
set "JAVA_HOME=%AIRPAY_TOOLS%\jdk-21"
exit /b 0

:mavenReady
if defined M2_HOME if exist "%M2_HOME%\bin\mvn.cmd" exit /b 0
if defined MAVEN_HOME if exist "%MAVEN_HOME%\bin\mvn.cmd" set "M2_HOME=%MAVEN_HOME%"
if exist "%M2_HOME%\bin\mvn.cmd" exit /b 0
if exist "%AIRPAY_TOOLS%\maven\bin\mvn.cmd" set "M2_HOME=%AIRPAY_TOOLS%\maven"
if exist "%M2_HOME%\bin\mvn.cmd" exit /b 0
if exist "C:\Tools\apache-maven-3.9.16\bin\mvn.cmd" set "M2_HOME=C:\Tools\apache-maven-3.9.16"
if exist "%M2_HOME%\bin\mvn.cmd" exit /b 0
if exist "C:\Program Files\Apache\maven\bin\mvn.cmd" set "M2_HOME=C:\Program Files\Apache\maven"
if exist "%M2_HOME%\bin\mvn.cmd" exit /b 0
for /f "delims=" %%M in ('where mvn.cmd 2^>nul') do (
  for %%D in ("%%~dpM..") do if exist "%%~fD\bin\mvn.cmd" set "M2_HOME=%%~fD"
)
if exist "%M2_HOME%\bin\mvn.cmd" exit /b 0
exit /b 1

:installMaven
call :needCurl
if errorlevel 1 exit /b 1
echo [toolchain] Maven was not found. Downloading Maven 3.9.16 into %AIRPAY_TOOLS%\maven
if exist "%AIRPAY_TOOLS%\maven-extract" rmdir /s /q "%AIRPAY_TOOLS%\maven-extract"
mkdir "%AIRPAY_TOOLS%\maven-extract"
curl.exe -fL --retry 3 --retry-delay 2 -o "%AIRPAY_TOOLS%\maven.zip" "https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.16/apache-maven-3.9.16-bin.zip"
if errorlevel 1 echo [toolchain] Maven download failed.
if errorlevel 1 exit /b 1
tar.exe -xf "%AIRPAY_TOOLS%\maven.zip" -C "%AIRPAY_TOOLS%\maven-extract"
if errorlevel 1 echo [toolchain] Could not unpack Maven.
if errorlevel 1 exit /b 1
set "MVN_SRC="
for /d %%D in ("%AIRPAY_TOOLS%\maven-extract\apache-maven-*") do if exist "%%~fD\bin\mvn.cmd" set "MVN_SRC=%%~fD"
if not defined MVN_SRC echo [toolchain] The Maven zip did not contain bin\mvn.cmd.
if not defined MVN_SRC exit /b 1
if exist "%AIRPAY_TOOLS%\maven" rmdir /s /q "%AIRPAY_TOOLS%\maven"
move "%MVN_SRC%" "%AIRPAY_TOOLS%\maven" >nul
set "M2_HOME=%AIRPAY_TOOLS%\maven"
exit /b 0

:needCurl
where curl.exe >nul 2>&1
if errorlevel 1 echo [toolchain] curl.exe was not found. Windows 10 and later include it.
if errorlevel 1 exit /b 1
where tar.exe >nul 2>&1
if errorlevel 1 echo [toolchain] tar.exe was not found. Windows 10 and later include it.
if errorlevel 1 exit /b 1
exit /b 0
