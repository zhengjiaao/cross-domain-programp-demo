CHCP 65001
@echo off&title Nginx-18093-reload
start ./nginx.exe -s reload


:: nginx端口
set App_Port=18093


set existPort=false
call:fun_exist_port %App_Port%
if %existPort% == false (
	goto :failed
)

:Successful
echo Nginx Successful restart!
echo 重启完成将自动关闭窗口！
TIMEOUT /T 5

exit

:failed
echo Nginx Restart failed!
echo.
echo 请检测 Nginx服务 必须在运行中，才能重启成功！

pause
exit


:: 函数-判断端口是否被占用

:fun_exist_port
	set app_port=%1
	for /f "tokens=3 delims=: " %%a in ('netstat -an') do (
		if "%%a"=="%app_port%" (
			echo 端口: %app_port% 检测到已占用,正在运行中...
			set existPort=true
			echo.
			goto :end_exist_port
		)
	)

:end_Exist_Port

goto:eof
