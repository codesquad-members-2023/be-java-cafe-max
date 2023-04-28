#!/bin/bash

#1
REPOSITORY=/home/ec2-user/app
PROJECT_NAME=be-java-cafe-max

#2
cd $REPOSITORY/$PROJECT_NAME/

#3
echo "> Git Pull"

git pull

echo "> 프로젝트 Build 시작"

#4
./gradlew build

echo "$REPOSITORY 디록토리로 이동"

cd $REPOSITORY

echo "> Build 파일 복사"

#5
cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/

echo "> 현재 구동중인 애플리케이션 pid 확인"

#6
CURRENT_PID=$(pgrep -f ${PROJECT_NAME}.*.jar)

echo "현재 구동 중인 애플리케이션 pid: $CURRENT_PID"

#7
if [ -z "$CURRENT_PID" ]; then
  echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> 새 애플리케이션 배포"

#8
JAR_NAME=$(ls -tr $REPOSITORY/ | grep jar | tail -n 1)

echo "> JAR name: $JAR_NAME"

#9
nohup java -jar -Dspring.profiles.active=dev $REPOSITORY/$JAR_NAME 2>&1 &
