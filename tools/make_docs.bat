@echo off
:: Copyright (c) 2013 The Chromium Embedded Framework Authors. All rights
:: reserved. Use of this source code is governed by a BSD-style license
:: that can be found in the LICENSE file.

set RETURNCODE=
setlocal

cd ..\java

set OUT_PATH="..\out\docs"
set JOGAMP_CP="..\third_party\jogamp\jar\gluegen-rt.jar;..\third_party\jogamp\jar\jogl-all.jar"

if not exist %OUT_PATH% mkdir %OUT_PATH%
javadoc -Xdoclint:none ^
    -windowtitle "CEF3 Java API Docs" ^
    -bottom "<center><a href=""https://bitbucket.org/chromiumembedded/java-cef"" target=""_top"">Chromium Embedded Framework (CEF)</a> Copyright &copy 2013 Marshall A. Greenblatt</center>" ^
    -classpath %JOGAMP_CP% ^
    -nodeprecated -d %OUT_PATH% ^
    -link https://docs.oracle.com/en/java/javase/17/docs/api/ ^
    -subpackages org.cef

:end
endlocal & set RETURNCODE=%ERRORLEVEL%
goto omega

:returncode
exit /B %RETURNCODE%

:omega
call :returncode %RETURNCODE%
