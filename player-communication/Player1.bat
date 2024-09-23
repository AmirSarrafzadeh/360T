@echo off
echo Starting Player1 as the server and possibly the initiator...

mvn compile exec:java -P PlayerSeparateProcess -Dexec.args="Initiator Mark 6000 6001 true"

pause

