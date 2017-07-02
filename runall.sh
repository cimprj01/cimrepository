# Run CIM Eureka
echo "Running CIM Eureka Server"
runeureka.sh

# Run CIM Config Server
echo "Running CIM Config Server"
runconfig.sh

# Run CIM Zuul
echo "Running CIM Zuul API Gateway"
runzuul.sh

# Run CIM Services
echo "Running CIM Services"
runcimservices.sh
