::
:: Runs the YManage project locally using jetty-runner web server.
::
:: By default Jetty Runner uses port 8080, if port 8080 is in use on your machine, specify a different port number
:: If the browser page is blank, wait for few seconds and then hit refresh
::
@echo off
set PORT_NUMBER=8080
echo Starting Web Server on port %PORT_NUMBER%
start java -jar "%~dp0jetty-runner.jar" "%~dp0YManage.war" --port %PORT_NUMBER%
echo Waiting for 10 seconds for web server to start, before launching the browser
TIMEOUT 10
start http://localhost:%PORT_NUMBER%/