@echo off
setlocal
rem Airpay Capital functional tests. Optional: functional.bat [udid]
rem Checks JDK 21 or newer, Maven, Android SDK, Appium, and UiAutomator2, and downloads what is missing.
cd /d "%~dp0..\.."
set "MODULE=airpay_capital"
set "PRODUCT=capital"
set "PRODUCT_TAG=@airpay-capital"
set "TEST=CapitalRunner"
set "ENV=live"
set "SUITE=functional"
set "A11Y=No"
set "OPEN_ALLURE=Yes"
set "CLEAN_PREVIOUS_REPORTS=No"
set "EXTRA_MAVEN=-DforkCount=0 -Dandroid.newCommandTimeout=120 -Dandroid.uiautomator2ServerReadTimeoutMs=30000 -Dcapital.test.otp=1234"
set "PREFERRED_UDID=%~1"
call "%~dp0..\_lib\run-mobile-product.bat"
set "EXIT_CODE=%ERRORLEVEL%"
if defined JENKINS_URL goto :done
if defined JENKINS_HOME goto :done
if /i "%CI%"=="true" goto :done
if /i "%NO_PAUSE%"=="Yes" goto :done
pause
:done
exit /b %EXIT_CODE%
