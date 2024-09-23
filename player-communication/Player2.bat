@echo off
echo Compiling and starting Player2 as the client...

mvn compile exec:java -P PlayerSeparateProcess -Dexec.args="Mark Initiator 6001 6000 false"

pause
