title msp-obr-service-provider-ws
cmd /c RenameTab "UserManagerWs"

setlocal

java -Xms128M -Xmx128M -Duser.timezone=UTC -XX:-OmitStackTraceInFastThrow -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5002 -jar build/libs/msp-obr-user-manager-ws-boot.jar

endlocal
