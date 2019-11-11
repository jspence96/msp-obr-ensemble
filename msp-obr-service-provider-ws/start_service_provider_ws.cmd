title msp-obr-service-provider-ws
cmd /c RenameTab "ServiceProviderWS"

setlocal

java -Xms128M -Xmx128M -Duser.timezone=UTC -XX:-OmitStackTraceInFastThrow -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5001 -jar build/libs/msp-obr-service-provider-ws-boot.jar

endlocal
