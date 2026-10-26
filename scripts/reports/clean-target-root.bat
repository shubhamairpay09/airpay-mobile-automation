@echo off
setlocal EnableDelayedExpansion
rem Remove ad-hoc exploration junk from target\ root (py/json/xml dumps from old manual scripts).
rem Keeps target\<product>\, target\cucumber-reports\, target\surefire-reports\, target\build\
set "ROOT=%~dp0..\.."
set "T=%ROOT%\target"
cd /d "%ROOT%"

if not exist "%T%" (
  echo No target folder.
  exit /b 0
)

echo.
echo === Clean legacy files under target\ root ===

for %%E in (py json xml log txt class) do (
  for /f "delimiters=" %%F in ('dir /b /a-d "%T%\*.%%E" 2^>nul') do (
    del /q "%T%\%%F" 2>nul && echo Removed %%F
  )
)

if exist "%T%\__pycache__" (
  rmdir /s /q "%T%\__pycache__" 2>nul && echo Removed __pycache__\
)

for /d %%D in ("%T%\a11y-reports" "%T%\a11y-reports-public" "%T%\locators" "%T%\email-artifacts") do (
  if exist %%D (
    rmdir /s /q %%D 2>nul && echo Removed %%~nxD\
  )
)

rem Loose folders that belong under target\<product>\
for /d %%D in ("%T%\logs" "%T%\screenshots" "%T%\allure-results") do (
  if exist %%D (
    rmdir /s /q %%D 2>nul && echo Removed loose %%~nxD\
  )
)

echo.
echo OK. Product reports stay in target\capital\ target\money\ etc.
exit /b 0
