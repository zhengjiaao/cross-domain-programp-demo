@echo off&title Nginx-18093-stop

taskkill /f /t /im nginx.exe

echo Nginx Service stop.

pause
exit