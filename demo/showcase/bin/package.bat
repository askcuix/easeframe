@echo off
echo [INFO] Use maven assembly-plugin package jsw+jetty+webapp zip.

cd %~dp0
cd ..
call mvn -U clean package -Dpackage.bin=true -Dmaven.test.skip=true
cd bin
pause 