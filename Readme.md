
Generate private key
openssl genpkey -algorithm RSA -out private-key.pem
Generate public key
openssl rsa -pubout -in private-key.pem -out public-key.pem
