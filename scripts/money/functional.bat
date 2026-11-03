@echo off
setlocal
rem Airpay Money functional tests. Optional: functional.bat [udid]
cd /d "%~dp0..\.."
set "MODULE=airpay_money"
set "PRODUCT=money"
set "PRODUCT_TAG=@airpay-money"
set "TEST=MoneyRunner"
set "ENV=live"
set "SUITE=functional"
set "A11Y=No"
set "OPEN_ALLURE=Yes"
set "CLEAN_PREVIOUS_REPORTS=No"
set "EXTRA_MAVEN=-DforkCount=0"
set "PREFERRED_UDID=%~1"
call "%~dp0..\_lib\run-mobile-product.bat"
set "EXIT_CODE=%ERRORLEVEL%"
pause
exit /b %EXIT_CODE%
