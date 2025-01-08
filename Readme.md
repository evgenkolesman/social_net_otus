
*** Social net for otus practice

1. You need, firstly, generate private keys in resources path
    
   1. ``cd ./src/main/resources`` move to resources path 
   2.   Generate private key for linux OR YOU MAY USE EXISTING KEYS
      ``openssl genpkey -algorithm RSA -out private-key.pem`` 
      Generate public key
      ``openssl rsa -pubout -in private-key.pem -out public-key.pem``
2. Second step to start Api: ``./gradlew bootJar`` or ``./gradlew.bat bootJar`` for windows
3. then you need to configure [application-docker.yml](docker%2Fapplication-docker.yml)
4. After that  run [docker-compose.yaml](docker%2Fdocker-compose.yaml)  with ``docker-compose up -d``
Attention check path when you would start docker compose file maybe you will need to add ``-f path_to_docker_compose_manifest``


