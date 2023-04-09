REPOSITORY=/home/ec2-user/application
PROJECT_NAME=cafe

cd $REPOSITORY/$PROJECT_NAME/

echo "> git pull"

git pull

echo "> project build start"

./gradlew clean build

echo "> project directory move"

cd $REPOSITORY

echo "> build file copy"
cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/

echo "> Check the currently running application pid"

CURRENT_PID=$(pgrep -f ${PROJECT_NAME}.*.jar)

if [ -z "$CURRENT_PID" ]; then
        echo "> No applications are currently running and will not shut down"
else
        echo "> kill -15 $CURRENT_PID"
        kill -15 $CURRENT_PID
        sleep 5
fi

echo "> new application distribution"

JAR_NAME=$(ls -tr $REPOSITORY/ | grep jar | grep -v plain |tail -n 1)
echo "> JAR name: $JAR_NAME"

nohup java -jar $REPOSITORY/$JAR_NAME 2>&1 &
