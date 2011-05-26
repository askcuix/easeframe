@echo off
echo [INFO] ȷ��Ĭ��JDK�汾ΪJDK6.0�����ϰ汾,������JAVA_HOME.

set MVN=mvn
set ANT=ant
set MAVEN_OPTS=%MAVEN_OPTS% -XX:MaxPermSize=128m

echo Maven����Ϊ%MVN%
echo Ant����Ϊ%ANT%

echo [Step 1] ��װEaseFrame ����module, demo��Ŀ����ģ�嵽����Maven�ֿ�, ����Eclipse��Ŀ�ļ�.
call %MVN% clean install -Dmaven.test.skip=true
if errorlevel 1 goto error
call %MVN% eclipse:clean eclipse:eclipse
if errorlevel 1 goto error

echo [Step 2] ����H2���ݿ�.
cd tools/h2
start "H2" %MVN% exec:java
cd ..\..\

echo [Step 3] ΪMini-Service ��ʼ�����ݿ�, ����Jetty.
cd demo\mini-service
call %MVN% eclipse:clean eclipse:eclipse
if errorlevel 1 goto error
call %ANT% -f bin/build.xml init-db 
if errorlevel 1 goto error
start "Mini-Service" %MVN% jetty:run -Djetty.port=8083
cd ..\..\

echo [Step 4] ΪSpringmvc-Web ��ʼ�����ݿ�, ����Jetty.
cd demo\springmvc-web
call %MVN% eclipse:clean eclipse:eclipse
if errorlevel 1 goto error
call %ANT% -f bin/build.xml init-db 
if errorlevel 1 goto error
start "Springmvc-Web" %MVN% %OFF_LINE% jetty:run -Djetty.port=8084
cd ..\..\

echo [Step 5] ΪStruts2-Web ��ʼ�����ݿ�, ����Jetty.
cd demo\struts2-web
call %MVN% eclipse:clean eclipse:eclipse
if errorlevel 1 goto error
call %ANT% -f bin/build.xml init-db 
if errorlevel 1 goto error
start "Struts2-Web" %MVN% %OFF_LINE% jetty:run -Djetty.port=8085
cd ..\..\

echo [INFO] EaseFrame �������.
echo [INFO] �ɷ���������ʾ��ַ:
echo [INFO] http://localhost:8083/mini-service
echo [INFO] http://localhost:8084/springmvc-web
echo [INFO] http://localhost:8085/struts2-web

goto end
:error
echo "�д�����"
:end
pause