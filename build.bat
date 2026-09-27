@echo off
title Build Grain Storage System
echo =======================================================================
echo   Building Digital Grain Storage ^& Quality Grading Management System
echo =======================================================================
echo.
call mvn clean package -DskipTests
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo [ERROR] Build failed!
    pause
    exit /b %ERRORLEVEL%
)
echo.
echo [SUCCESS] Build succeeded! Executable JAR created at target\grain-storage-system-1.0.0.jar
pause
