@echo off
setlocal
set JAVAC="C:\Program Files\Java\jdk-25.0.5.0.2\bin\javac.exe"
set JAVA="C:\Program Files\Java\jre1.8.0_501\bin\java.exe"
set JFX="C:\Program Files\Java\jre1.8.0_501\lib\ext\jfxrt.jar"

echo Compiling all JavaFX LA2 programs...
%JAVAC% --release 8 -cp %JFX% *.java
if errorlevel 1 (
    echo Compilation failed.
    pause
    exit /b 1
)

:menu
echo.
echo ============================================
echo   JavaFX LA2 Assignments - Select Program
echo ============================================
echo   1. Student Registration Form
echo   2. Simple Calculator
echo   3. Temperature Converter
echo   4. Student Grade Calculator
echo   5. Simple Banking Application
echo   0. Exit
echo ============================================
set /p choice="Enter choice (0-5): "

if "%choice%"=="1" %JAVA% StudentRegistrationApp
if "%choice%"=="2" %JAVA% SimpleCalculatorApp
if "%choice%"=="3" %JAVA% TemperatureConverterApp
if "%choice%"=="4" %JAVA% StudentGradeCalculatorApp
if "%choice%"=="5" %JAVA% SimpleBankingApp
if "%choice%"=="0" exit /b 0
goto menu

