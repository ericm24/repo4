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
		set CP_LIB_ALL=
		
		set CP_BASE=c:\apps\java\CShell
		set CP_LIB=%CP_BASE%\lib

		set CP=%CP_BASE%

		set CP_LIB_ALL=%CP_LIB%\log4j-1.2.14.jar;
		set CP_LIB_ALL=%CP_LIB_ALL%%CP_LIB%\spring-tx-4.1.6.RELEASE.jar;
		set CP_LIB_ALL=%CP_LIB_ALL%%CP_LIB%\spring-expression-4.1.6.RELEASE.jar;
		set CP_LIB_ALL=%CP_LIB_ALL%%CP_LIB%\spring-beans-4.1.6.RELEASE.jar;
		set CP_LIB_ALL=%CP_LIB_ALL%%CP_LIB%\spring-aop-4.1.6.RELEASE.jar;
		set CP_LIB_ALL=%CP_LIB_ALL%%CP_LIB%\slf4j-log4j12-1.5.10.jar;
		set CP_LIB_ALL=%CP_LIB_ALL%%CP_LIB%\slf4j-api-1.5.10.jar;
		set CP_LIB_ALL=%CP_LIB_ALL%%CP_LIB%\json-simple-1.1.jar;
		set CP_LIB_ALL=%CP_LIB_ALL%%CP_LIB%\commons-lang-2.4.jar;
		set CP_LIB_ALL=%CP_LIB_ALL%%CP_LIB%\aopalliance-1.0.jar;
		set CP_LIB_ALL=%CP_LIB_ALL%%CP_LIB%\cglib-nodep-2.2.jar;
		set CP_LIB_ALL=%CP_LIB_ALL%%CP_LIB%\aspectjweaver-1.6.1.jar;
		set CP_LIB_ALL=%CP_LIB_ALL%%CP_LIB%\commons-logging-1.1.1.jar;
		set CP_LIB_ALL=%CP_LIB_ALL%%CP_LIB%\commons-lang.jar;
		set CP_LIB_ALL=%CP_LIB_ALL%%CP_LIB%\commons-codec-1.10.jar;
		set CP_LIB_ALL=%CP_LIB_ALL%%CP_LIB%\hamcrest-core-1.2.1.jar;
		set CP_LIB_ALL=%CP_LIB_ALL%%CP_LIB%\spring-context-4.1.6.RELEASE.jar;
		set CP_LIB_ALL=%CP_LIB_ALL%%CP_LIB%\spring-core-4.1.6.RELEASE.jar
    
    set CP=%CP_BASE%\shell-project-common-4.4.1.0.jar
		
		set CP=%CP%;%CP_LIB_ALL%

:RUN_IT

	java -Xmx1536m -classpath %CP% com/eric/shell/controller/CommandLineController %1 %2 %3 %4 %5 %6
	goto END

:USAGE	
	echo.
	echo.
	echo s2 command da-params
	echo.
	echo.

:END


