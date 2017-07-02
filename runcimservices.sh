echo "Running CIM Services "
cd CIMServices
echo "Running 3 instances each"
java -jar target/CIMServices-0.0.1-SNAPSHOT.jar &

java -Dserver.port=11001 -jar target/CIMServices-0.0.1-SNAPSHOT.jar &

java -Dserver.port=11002 -jar target/CIMServices-0.0.1-SNAPSHOT.jar &
cd ..

cd CIMServices-Ext
java -jar target/CIMServices-Ext-0.0.1-SNAPSHOT.jar &

java  -Dserver.port=10002 -jar target/CIMServices-Ext-0.0.1-SNAPSHOT.jar &

java -Dserver.port=10003  -jar target/CIMServices-Ext-0.0.1-SNAPSHOT.jar &
