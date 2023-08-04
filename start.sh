
cp application.properties.tmpl src/main/resources/application.properties

DIR="apache-activemq-5.17.5"
if [ -d "$DIR" ]; then
  echo "${DIR} Exists starting activemq on local"
else
  echo "extracting >> ${DIR}..."
  tar -xvf apache-activemq-5.17.5-bin.tar.gz
fi
sh apache-activemq-5.17.5/bin/activemq start
java -jar target/img-processor-0.0.1-SNAPSHOT.jar --server.port=8082