@echo off
setlocal
rem Airpay Vyaapaar accessibility. Optional: a11y.bat [udid]
rem Vyaapaar has no functional suite.
cd /d "%~dp0..\.."
%SystemRoot%\System32\findstr /r /c:"^vyaapaar.test.password=[^ ]" "config\secrets.properties" >nul 2>&1
if errorlevel 1 (
  echo config\secrets.properties needs vyaapaar.test.password=YOUR_PASSWORD
  echo Without it the Vyaapaar accessibility run cannot sign in.
  pause
  exit /b 1
)
set "PRODUCT=vyaapaar"
set "MODULE=airpay_vyaapaar"
set "TEST=VyaapaarRunner"
set "TAGS=@VYAAPAAR_001 and @deep"
set "EXTRA=-Da11y.deep.scan=true -Da11y.login.password=true -Dandroid.keep.session=true"
set "PREFERRED_UDID=%~1"
call "%~dp0..\_lib\run-a11y.bat"
set "EXIT_CODE=%ERRORLEVEL%"
pause
exit /b %EXIT_CODE%
