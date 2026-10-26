@echo off
rem Vyaapaar has one accessibility batch file. This starts that same scan.
call "%~dp0a11y-run.bat"
exit /b %ERRORLEVEL%
