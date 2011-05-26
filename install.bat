@echo off
echo [INFO] 确保默认JDK版本为JDK6.0及以上版本,已配置JAVA_HOME.

set MVN=mvn
set ANT=ant
set MAVEN_OPTS=%MAVEN_OPTS% -XX:MaxPermSize=128m

echo Maven命令为%MVN%
echo Ant命令为%ANT%

echo [Step 1] 安装EaseFrame 所有module, demo项目生成模板到本地Maven仓库, 生成Eclipse项目文件.
call %MVN% clean install -Dmaven.test.skip=true
if errorlevel 1 goto error
call %MVN% eclipse:clean eclipse:eclipse
if errorlevel 1 goto error

echo [Step 2] 启动H2数据库.
cd tools/h2
start "H2" %MVN% exec:java
cd ..\..\

echo [Step 3] 为Mini-Service 初始化数据库, 启动Jetty.
cd demo\mini-service
call %MVN% eclipse:clean eclipse:eclipse
if errorlevel 1 goto error
call %ANT% -f bin/build.xml init-db 
if errorlevel 1 goto error
start "Mini-Service" %MVN% jetty:run -Djetty.port=8083
cd ..\..\

echo [Step 4] 为Springmvc-Web 初始化数据库, 启动Jetty.
cd demo\springmvc-web
call %MVN% eclipse:clean eclipse:eclipse
if errorlevel 1 goto error
call %ANT% -f bin/build.xml init-db 
if errorlevel 1 goto error
start "Springmvc-Web" %MVN% %OFF_LINE% jetty:run -Djetty.port=8084
cd ..\..\

echo [Step 5] 为Struts2-Web 初始化数据库, 启动Jetty.
cd demo\struts2-web
call %MVN% eclipse:clean eclipse:eclipse
if errorlevel 1 goto error
call %ANT% -f bin/build.xml init-db 
if errorlevel 1 goto error
start "Struts2-Web" %MVN% %OFF_LINE% jetty:run -Djetty.port=8085
cd ..\..\

echo [INFO] EaseFrame 启动完毕.
echo [INFO] 可访问以下演示网址:
echo [INFO] http://localhost:8083/mini-service
echo [INFO] http://localhost:8084/springmvc-web
echo [INFO] http://localhost:8085/struts2-web

goto end
:error
echo "有错误发生"
:end
pause