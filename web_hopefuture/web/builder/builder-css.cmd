@echo off
@echo "Release ExtPlus JavaScript Library."

set projectFile=builder-css.jsb2
set homeDir=..
set charset=utf-8

java -jar JSBuilder2/JSBuilder2Plus.jar --projectFile %projectFile% --homeDir %homeDir% --verbose --charset %charset%
copy ..\css-tmp\*.css ..\stylesheets
rmdir ..\css-tmp /S/Q
@echo "Release done."
pause
