@echo off
setlocal
cd /d "%~dp0"
call mvnw.cmd clean compile
exit /b %ERRORLEVEL%
