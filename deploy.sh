PID=$(ps -ef | grep monkeystore | grep -v grep | awk '{print $2}')
if [ -z "$PID" ]; then
    echo "no process running"
else
    kill -9 $PID
    echo "stopped $PID"
fi
echo "starting new jar file"
echo $BUILD_NUMBER
nohup java -javaagent:/home/mahi/newrelic/newrelic.jar -jar monkeystore-0.0.1-SNAPSHOT-$BUILD_NUMBER.jar > app.log 2>&1 &
NEW_PID=$(ps -ef | grep monkeystore | grep -v grep | awk '{print $2}')
echo "New build started with $NEW_PID"
sleep 5
ps -ef | grep "monkeystore-0.0.1"
