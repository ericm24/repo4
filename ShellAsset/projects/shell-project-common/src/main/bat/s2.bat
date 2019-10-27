@ECHO OFF

REM ---------------------------------------------------------------------------
REM Prior to running this script, 
REM ensure that the configuration files have been edited appropriately.
REM ---------------------------------------------------------------------------

:BEGIN

  if "%1" == "" goto USAGE

		set CP=
		set CP_BASE=
		set CP_LIB=
		
		set CP_BASE=c:\apps\java\CShell
		set CP_LIB=%CP_BASE%\lib

		set CP=%CP_BASE%
		set CP=%CP%;%CP_BASE%\shell-project-common-4.4.1.0.jar;
		
		set CP=%CP%;%CP_BASE%\commons-lang.jar;
		set CP=%CP%;%CP_BASE%\commons-logging-1.1.1.jar;
		set CP=%CP%;%CP_BASE%\json-simple-1.1.jar;

:RUN_IT

	java -Xmx1536m -classpath %CP% com/eric/shell/controller/CommandLineController %1 %2 %3 %4 %5 %6
	goto END

:USAGE	
	echo.
	echo.
	echo s command params
	echo.
	echo.

:END


