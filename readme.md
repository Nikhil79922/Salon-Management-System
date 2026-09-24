for dir in user_service category_service salon_service service-offering_service payment_service booking_servi
ce gateway-service eureka-server;   
echo "===== $dir ====="
(cd "$dir" && mvn clean compile jib:build -Djib.from.platforms=linux/arm64) || exit 1