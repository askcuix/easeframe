@echo off
echo [INFO] ȷ������pathϵͳ������ANT1.7���ϰ汾��binĿ¼.
echo [INFO] ȷ������WebServiceӦ��������.

cd %~dp0
call ant -f save-wsdl-build.xml

echo [INFO] WSDL�ѱ��浽webapp/wsdl/mini-service.wsdl.
pause