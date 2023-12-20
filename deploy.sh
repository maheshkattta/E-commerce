PID=$(ps -ef | grep ecommerence | grep -v grep | awk '{print $3}')
if [ -z "$PID" ] then
    echo "no process running"
else
    kill -9 $PID
    echo "stopped $PID"
fi
echo "starting new jar file"
nohup java -javaagent:/home/mahi/newrelic/newrelic.jar -jar monkeystore-0.0.1-SNAPSHOT-$BUILD_NUMBER.jar > app.log 2>&1 &
