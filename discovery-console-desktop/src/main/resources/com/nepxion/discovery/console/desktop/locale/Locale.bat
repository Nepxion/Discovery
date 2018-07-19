@echo on
@echo =============================================================
@echo $                                                           $
@echo $                     Encoding localizer                    $
@echo $                                                           $
@echo $                                                           $
@echo $                                                           $
@echo $  Nepxion Technologies All Right Reserved                  $
@echo $  Copyright(C) 2015                                        $
@echo $                                                           $
@echo =============================================================
@echo.
@echo off

@rem ======================================
@rem DOS Batch file to invoke the Swing UI
@rem ======================================

@title Encoding localizer
@color 0a

rem if "%JAVA_HOME%"=="" goto noJava

if "%JAVA_HOME%"=="" set JAVA_HOME=D:\JDK1.6.0

%JAVA_HOME%\bin\native2ascii -encoding gbk Locale.properties Locale_zh_CN.properties