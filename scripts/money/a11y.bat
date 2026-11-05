@echo off
setlocal
rem Airpay Money accessibility. Optional: a11y.bat [udid]
cd /d "%~dp0..\.."
set "PRODUCT=money"
set "MODULE=airpay_money"
set "TEST=MoneyRunner"
set "TAGS=@MONEY_001 and @deep"
set "EXTRA=-Da11y.deep.scan=true -Da11y.crawl.exhaustive=true -Dandroid.keep.session=true -Da11y.fresh.run=true"
set "PREFERRED_UDID=%~1"
call "%~dp0..\_lib\run-a11y.bat"
set "EXIT_CODE=%ERRORLEVEL%"
pause
exit /b %EXIT_CODE%
