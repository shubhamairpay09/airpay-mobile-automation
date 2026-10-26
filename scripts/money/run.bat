@echo off
setlocal
rem Airpay Money functional tests.
rem Values that can change (amounts, rates, goal name, calculator names) come from
rem airpay_money\src\test\resources\testdata\money-functional-data.xlsx.
rem Login mobile, OTP, and PIN stay in config\money.properties.
rem A failure screenshot is saved only when a functional scenario fails.

set "MODULE=airpay_money"
set "PRODUCT=money"
set "PRODUCT_TAG=@airpay-money"
set "TEST=MoneyRunner"

set "ENV=live"
set "SUITE=functional"
set "EXTRA_TAGS="
set "CUCUMBER_TAGS="
set "CUCUMBER_FEATURES="
set "CLEAN_PREVIOUS_REPORTS=No"
set "OPEN_ALLURE=Yes"
set "A11Y=No"

set "EXTRA_MAVEN=-DforkCount=0"
rem set "EXTRA_MAVEN=-DforkCount=0 -Dandroid.udid=YOUR_UDID -Dmoney.test.otp=1234"

call "%~dp0..\_lib\run-mobile-product.bat"
exit /b %ERRORLEVEL%
