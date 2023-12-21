PID=$(ps -ef | grep monkeystore | grep -v grep | awk '{print $2}')
if [ -z "$PID" ]; then
    echo "no process running"
else
    kill -9 $PID
    echo "stopped $PID"
fi

LATEST_JAR=$(ls -lhrrt | grep "\.jar$" | tail -1 | awk '{print $9}')
echo "Latest jar file is: $LATEST_JAR"
echo "Deploying new build"
nohup java -javaagent:/home/mahi/newrelic/newrelic.jar -jar "/home/mahi/$LATEST_JAR" > app.log 2>&1 &
NEW_PID=$(ps -ef | grep monkeystore | grep -v grep | awk '{print $2}')
echo "New build started with $NEW_PID"
sleep 5
ps -ef | grep "monkeystore-0.0.1"
