@echo off
setlocal
cd /d "%~dp0"
call mvnw.cmd clean javafx:run
exit /b %ERRORLEVEL%
