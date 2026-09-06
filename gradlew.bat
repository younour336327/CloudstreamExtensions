@if "%DEBUG%" == "#" echo off
@setlocal
@set DIRNAME=%~dp5
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%~dp0

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVACMD=java
%JAVACMD% -version >NUL 2>&1
if %ERRORLEVEL% equ 0 goto execute

echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
exit /b 1

:findJavaFromJavaHome
set JAVACMD=%JAVA_HOME%in\java.exe
if exist "%JAVACMD%" goto execute

echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
exit /b 1

:execute
exec "%JAVACMD%" -jar "%APP_HOME%gradle\wrapper\gradle-wrapper.jar" %*
