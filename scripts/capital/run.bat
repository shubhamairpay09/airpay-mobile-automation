@echo off
setlocal
rem Airpay Capital functional tests.
rem Login mobile and OTP stay in config\capital.properties.
rem A failure screenshot is saved only when a functional scenario fails.

set "MODULE=airpay_capital"
set "PRODUCT=capital"
set "PRODUCT_TAG=@airpay-capital"
set "TEST=CapitalRunner"

set "ENV=live"
set "SUITE=functional"
set "EXTRA_TAGS="
set "CUCUMBER_TAGS="
set "CUCUMBER_FEATURES="
set "CLEAN_PREVIOUS_REPORTS=No"
set "OPEN_ALLURE=Yes"
set "A11Y=No"

set "EXTRA_MAVEN=-DforkCount=0"
rem set "EXTRA_MAVEN=-DforkCount=0 -Dandroid.udid=YOUR_UDID -Dcapital.test.otp=1234"

call "%~dp0..\_lib\run-mobile-product.bat"
exit /b %ERRORLEVEL%
