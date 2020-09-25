@echo on
@echo =============================================================
@echo $                                                           $
@echo $                     Nepxion Discovery                     $
@echo $                                                           $
@echo $                                                           $
@echo $                                                           $
@echo $  Nepxion Studio All Right Reserved                        $
@echo $  Copyright (C) 2017-2050                                  $
@echo $                                                           $
@echo =============================================================
@echo.
@echo off

@title Nepxion Discovery
@color 0a

@set CLASSPATH=./conf/;./lib/*
@set PATH=

call:JAVA_HOME_CHECK

:JAVA_HOME_CHECK
if "%JAVA_HOME%"=="" goto ECHO_JAVA_HOME_CHECK_EXIT

@rem echo Found Java Home=%JAVA_HOME%
echo JAVA_HOME=%JAVA_HOME%
goto SET_CLASSPATH_AND_RUN

:ECHO_JAVA_HOME_CHECK_EXIT
@rem echo Please set JAVA_HOME
echo «Î…Ë÷√JAVA_HOME
goto EXIT

:SET_CLASSPATH_AND_RUN
"%JAVA_HOME%\bin\java" -Dfile.encoding=GBK -Ddefault.client.encoding=GBK -Duser.language=zh -Duser.region=CN -Djava.security.policy=java.policy -Djava.library.path=%PATH% -Xms128m -Xmx512m -classpath %CLASSPATH% com.nepxion.discovery.console.desktop.ConsoleLauncher

:PAUSE
pause;

:EXIT
exit;