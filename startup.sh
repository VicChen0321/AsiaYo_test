docker build -t i_asia_yo_api .
docker run -d -p 8080:8080 --name c_asia_yo_api i_asia_yo_api