# Build Eureka Server
echo "Building CIM Eureka Server"
cd eureka-master
mvn clean package
cd ..


# Build CIM Config Server
echo "Building CIM Config Server"
cd configserver-master
mvn clean package
cd ..

# Build CIM Zuul
echo "Building CIM Zuul API Gateway"
cd zuul-server-master
mvn clean package
cd ..

# Build CIM Services
#Run Config and eureka for build
./runconfig.sh
./runeureka.sh

echo "Building CIM Services"
cd CIMServices
mvn clean package
cd ..

cd CIMServices-Ext
mvn clean package
cd ..
