@echo off
title Digital Grain Storage & Quality Grading Management System
cls
echo =======================================================================
echo   Digital Grain Storage ^& Quality Grading Management System
echo =======================================================================
echo.
echo Choose Run Mode:
echo   [1] Run with Instant Demo Mode (H2 In-Memory - Recommended for Viva)
echo   [2] Run with MySQL Database (Uses application.properties)
echo.
set /p choice="Enter choice (1 or 2, default is 1): "

if "%choice%"=="2" (
    echo.
    echo Starting Spring Boot backend with MySQL configuration...
    java -jar "%~dp0target\grain-storage-system-1.0.0.jar"
) else (
    echo.
    echo Starting Spring Boot backend with Instant Demo profile...
    java -jar "%~dp0target\grain-storage-system-1.0.0.jar" --spring.profiles.active=h2
)

pause
