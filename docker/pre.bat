set MAX_WAIT_TIME=%1
set PORT=%2

cd .. && make up

@echo off
rem Wait for application to startup (ping during %MAX_WAIT_TIME% seconds, waiting 1 second each time)
set count=0

echo Wait until service is available
:loop
if /i %count% geq %MAX_WAIT_TIME% goto :timeout

curl -sI --url http://localhost:%PORT%/api/management/health

if /i %ERRORLEVEL% NEQ 0 goto :retry
goto :up

:retry
set /a "count+=1"
echo|set /p="."
sleep 1
goto :loop

goto :up

:timeout
echo Failed to start under %MAX_WAIT_TIME% seconds
make down
exit /b 1

goto :end

:up
echo health responded in %count% seconds

:end
