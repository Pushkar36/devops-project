@echo off
REM Student Management System - Simple Run Script
REM Builds the shaded JAR (if needed) and runs it directly.

cd /d "%~dp0"

set JAR=target\student-management-system-1.0-SNAPSHOT-shaded.jar

echo.
echo ========================================
echo   Student Management System - Run
echo ========================================
echo.

if not exist "%JAR%" (
    echo [1/2] Shaded JAR not found. Building package...
    mvn -DskipTests package
    if errorlevel 1 (
        echo [X] Build failed. Check Maven output above.
        pause
        exit /b 1
    )
    echo [✓] Build completed. JAR created at %JAR%
) else (
    echo [✓] Found shaded JAR: %JAR%
)

echo.
echo [2/2] Launching application...
echo.
echo ========================================

java -jar "%JAR%"

echo.
echo ========================================
echo   Application Closed
echo ========================================
echo.
pause
