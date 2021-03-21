# RestApiWithDocker
Scala, RestApi, Swagger-ui, Docker

Swagger UI with https://github.com/iheartradio/play-swagger

```bash
sbt docker:publishLocal
docker run --env APPLICATION_SECRET=<Your application secret> --env APPLICATION_HOST=<Your host> -p 9000:9000 restapiwithdocker:1.0-SNAPSHOT
```

## Azure Stuff
```bash
az login
docker tag restapiwithdocker:1.0-SNAPSHOT <your azure container registry>/restapiwithdocker:1.0-SNAPSHOT
docker login <your azure container registry>
docker push <your azure container registry>/restapiwithdocker:1.0-SNAPSHOT
```

