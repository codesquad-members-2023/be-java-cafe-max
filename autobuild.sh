#!/bin/bash
# 저장소 업데이트
echo "저장소 갱신"
git fetch origin
REMOTE=`git rev-parse origin/step6`
LOCAL=`git rev-parse HEAD`

if [[ $REMOTE = $LOCAL ]]; then
        echo "빌드할 필요가 없습니다."
        exit 0
fi

git merge origin/step6

# CAFEID=`jps | grep cafe | cut  -d ' ' -f 1`
CAFEID=`jps | grep cafe | awk '{ print $1 }'`

if [ -z $CAFEID ]; then
        echo "동작중인 서버가 없습니다."
else
        echo "$CAFEID 프로세스를 삭제합니다."
        kill -9 $CAFEID
fi

echo "빌드 시작"
rm -rf build
./gradlew build -x test

echo "서버 시작"
nohup java -jar ~/be-java-cafe-max/build/libs/cafe-0.0.1-SNAPSHOT.jar > ../log.txt 2>&1 &
echo "배포 완료!"

# crontab -e
# */3 * * * * /home/ubuntu/be-java-cafe-max/autobuild.sh
