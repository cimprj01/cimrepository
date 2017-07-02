
# Build CIM Services
#Run Config and eureka for build
./runconfig.sh >> runcim.log


./runeureka.sh >> runcim.log

sleep 60

echo "Building CIM Services"
cd CIMServices
mvn clean package
cd ..

cd CIMServices-Ext
mvn clean package
cd ..
