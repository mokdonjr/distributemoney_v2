source ./app_env.sh

echo "SERVICE_NAME_PREFIX="${SERVICE_NAME_PREFIX}
echo "MYSQL_CONTAINER_NAME="${MYSQL_CONTAINER_NAME}
echo "MYSQL_HOST="${MYSQL_HOST}
echo "MYSQL_PORT="${MYSQL_PORT}
echo "MYSQL_USERNAME="${MYSQL_USERNAME}
echo "MYSQL_PASSWORD="${MYSQL_PASSWORD}
echo "MYSQL_SCHEMA="${MYSQL_SCHEMA}
echo "MYSQL_VOLUME_NAME="${MYSQL_VOLUME_NAME}
echo "MYSQL_CONF_VOLUME_NAME="${MYSQL_CONF_VOLUME_NAME}

# mysql cnf volume 생성
docker volume create --driver local \
    --opt type=none \
    --opt device=$(pwd)/mysql/conf.d \
    --opt o=bind \
    --name ${MYSQL_CONF_VOLUME_NAME}

# mysql pull 및 실행
docker run -d \
  --name ${MYSQL_CONTAINER_NAME} \
  -p ${MYSQL_PORT}:3306 \
  -e MYSQL_DATABASE=${MYSQL_SCHEMA} \
  -e MYSQL_ROOT_PASSWORD=${MYSQL_PASSWORD} \
  --volume=${MYSQL_VOLUME_NAME}:/var/lib/mysql \
  --volume=${MYSQL_CONF_VOLUME_NAME}:/etc/mysql/conf.d \
  mysql:8

while ! docker exec ${MYSQL_CONTAINER_NAME} mysql --user=${MYSQL_USERNAME} --password=${MYSQL_PASSWORD} --host=${MYSQL_HOST} --port=${MYSQL_PORT} ${MYSQL_SCHEMA} --silent &> /dev/null ; do
    echo "Waiting for database connection..."
    sleep 2
done
sleep 5

# spring app build
pushd ..
mvn clean package \
-Dmaven.test.skip \
-Ddatabase.username=${MYSQL_USERNAME} \
-Ddatabase.password=${MYSQL_PASSWORD} \
-Ddatabase.schema=${MYSQL_SCHEMA} \
-Ddatabase.host=${MYSQL_HOST} \
-Ddatabase.port=${MYSQL_PORT}
popd

# spring app 실행
java -jar ../target/distributemoney_v2-0.0.1-SNAPSHOT.jar