@echo off
setlocal
rem Airpay Capital accessibility. Optional: a11y.bat [udid]
cd /d "%~dp0..\.."
set "PRODUCT=capital"
set "MODULE=airpay_capital"
set "TEST=CapitalRunner"
set "TAGS=@CAPITAL_A11Y_001 and @deep"
set "EXTRA=-Da11y.deep.scan=true -Dandroid.keep.session=true"
set "PREFERRED_UDID=%~1"
call "%~dp0..\_lib\run-a11y.bat"
set "EXIT_CODE=%ERRORLEVEL%"
pause
exit /b %EXIT_CODE%
