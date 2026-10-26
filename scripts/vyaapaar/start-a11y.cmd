@echo off
setlocal
rem Wakes the phone, then starts the single Vyaapaar accessibility batch file.
cd /d "%~dp0..\.."

set "ADB=D:\Android_Studio\Sdk\platform-tools\adb.exe"
if not exist "%ADB%" set "ADB=adb"

"%ADB%" -s RZCW724DGXZ shell input keyevent 224
"%ADB%" -s RZCW724DGXZ shell svc power stayon true

call "%~dp0a11y-run.bat"
exit /b %ERRORLEVEL%
