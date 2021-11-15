CHCP 65001
@echo off&title Nginx-18093-start


:: nginx端口
set App_Port=18093

echo The Nginx service is starting...

set existPort=false
call:fun_exist_port %App_Port%
if %existPort% == false (
	goto :start_app
)

taskkill /f /t /im nginx.exe

echo 关闭: %App_Port% 端口成功！

:start_app
start ./nginx.exe

TIMEOUT /T 2

set existPort=false
call:fun_exist_port %App_Port%
if %existPort% == false (
	goto :failed
)

:Successful
echo.
echo Nginx service %App_Port% started successfully.
echo.
echo 此窗口可以手动关闭，Nginx service %App_Port% 服务不会退出！
pause
exit

:failed
echo Nginx service %App_Port% failed to start！
echo.
echo 请检测 Nginx服务配置文件内容 ./conf/nginx.conf

pause
exit


:: 函数-判断端口是否被占用

:fun_exist_port
	set app_port=%1
	for /f "tokens=3 delims=: " %%a in ('netstat -an') do (
		if "%%a"=="%app_port%" (
			echo 端口: %app_port% 检测到已占用,正在运行中...
			set existPort=true
			goto :end_exist_port
		)
	)

:end_Exist_Port

goto:eof
