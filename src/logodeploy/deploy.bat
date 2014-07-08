SETLOCAL ENABLEDELAYEDEXPANSION

REM  take relevant jar files from projects
copy ..\logo\dist\logo.jar 					lib\logo.jar
copy ..\logojavacc\dist\logojavacc.jar 		lib\logojavacc.jar 
copy ..\logoclasslib\dist\logoclasslib.jar 	lib\logoclasslib.jar
copy ..\logoserver\dist\logoserver.jar 		lib\logoserver.jar 
copy ..\logocomm\dist\logocomm.jar 			lib\logocomm.jar 	

REM copy the file running on the robot
copy ..\logorobot\build\*.nxj				dist\

REM sign them all (with the same certificate)	
jarsigner -storepass elperro1 -keystore logoKeystore lib\logo.jar 				jgrindall
jarsigner -storepass elperro1 -keystore logoKeystore lib\logocomm.jar 			jgrindall
jarsigner -storepass elperro1 -keystore logoKeystore lib\logojavacc.jar 		jgrindall
jarsigner -storepass elperro1 -keystore logoKeystore lib\logoclasslib.jar 		jgrindall
jarsigner -storepass elperro1 -keystore logoKeystore lib\logoserver.jar 		jgrindall
jarsigner -storepass elperro1 -keystore logoKeystore lib\substance.jar 			jgrindall
jarsigner -storepass elperro1 -keystore logoKeystore lib\trident.jar 			jgrindall
jarsigner -storepass elperro1 -keystore logoKeystore lib\bluecove.jar 			jgrindall
jarsigner -storepass elperro1 -keystore logoKeystore lib\bluecove-gpl.jar 		jgrindall
jarsigner -storepass elperro1 -keystore logoKeystore lib\pccomm.jar 			jgrindall

REM copy them 
copy lib\logo.jar 				dist\appserver\
copy lib\substance.jar 			dist\appserver\lib\
copy lib\trident.jar 			dist\appserver\lib\
copy lib\logojavacc.jar 		dist\appserver\lib\
copy lib\logocomm.jar 			dist\appserver\lib\
copy lib\logoclasslib.jar 		dist\appserver\lib\

copy lib\logoserver.jar 		dist\robotserver\
copy lib\bluecove.jar 			dist\robotserver\lib\
copy lib\bluecove-gpl.jar 		dist\robotserver\lib\
copy lib\pccomm.jar 			dist\robotserver\lib\
copy lib\logocomm.jar 			dist\robotserver\lib\
copy lib\logoclasslib.jar 		dist\robotserver\lib\
copy lib\logojavacc.jar 		dist\robotserver\lib\
copy lib\pccomm.jar 			dist\robotserver\lib\
copy lib\substance.jar 			dist\robotserver\lib\
copy lib\trident.jar 			dist\robotserver\lib\

REM timestamp for my use
echo created %date% %time% > dist\robotserver\version.txt
echo created %date% %time% > dist\appserver\version.txt

@echo off
echo RoboLogo log file > dist/robotserver/robologo.log

copy "C:/Users/John/Documents/univ_westminster/project/WORK/userManual.odt" dist\userManual.odt
copy "C:/Users/John/Documents/univ_westminster/project/WORK/userManual.pdf" dist\userManual.pdf

set path="C:\Program Files\WinRAR\";%path%
cd dist
rar a robologo.zip -r *.*

