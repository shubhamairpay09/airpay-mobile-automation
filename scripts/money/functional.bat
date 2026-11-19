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
set "EXTRA_MAVEN=-DforkCount=0 -Dandroid.keep.session=true -Dmoney.functional.fast=true -Dmoney.test.pin=123698 -Dtimeout.action=12000 -Dtimeout.navigation=35000"
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
