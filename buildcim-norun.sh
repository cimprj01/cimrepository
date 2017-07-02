
echo "Building CIM Services"
cd CIMServices
mvn clean package
cd ..

cd CIMServices-Ext
mvn clean package
cd ..
