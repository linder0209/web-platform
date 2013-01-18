@echo off
@echo "Release ExtPlus JavaScript Library."

set projectFile=builder-js.jsb2
set homeDir=..
set charset=utf-8

java -jar JSBuilder2/JSBuilder2Plus.jar --projectFile %projectFile% --homeDir %homeDir% --verbose --charset %charset%
copy ..\..\js-tmp\*.js ..\jscompressor
rmdir ..\..\js-tmp /S/Q
@echo "Release done."
pause
