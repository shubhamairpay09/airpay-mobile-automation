@echo off
setlocal EnableDelayedExpansion
set "ROOT=%~dp0..\.."
cd /d "%ROOT%"
set "LEFT=0"

echo.
echo === Clean leftover module target\ dumps ===
echo Does not delete repo-root target\ or .allure
echo.

for %%D in (airpay_capital airpay_money utilities) do (
  if exist "%ROOT%\%%D\target" (
    rmdir /s /q "%ROOT%\%%D\target" 2>nul && echo Removed %%D\target || (echo FAILED %%D\target & set "LEFT=1")
  )
)

echo.
if "%LEFT%"=="1" (
  echo Some folders could not be removed ^(file in use^).
  exit /b 1
)
echo Cleanup complete. Product reports remain under %ROOT%\target\
exit /b 0
