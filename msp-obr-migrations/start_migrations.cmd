title msp-obr-migration
@echo off
setlocal
echo starting migrations

set heapMemoryOpt=-Xms256m -Xmx1024m

java -Xms128M -Xmx128M -Duser.timezone=UTC -XX:-OmitStackTraceInFastThrow -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5031 -jar build/libs/msp-obr-migrations-boot.jar

endlocal
