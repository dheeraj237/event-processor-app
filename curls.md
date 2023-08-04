## Test Endpoints Curls
### get all databases
```http
curl --location 'http://localhost:8080/getDatabases'
```

### get database for domain
```http
curl --location 'http://localhost:8080/getDBForDomain/google.com'
```
---

## Image Processor Curls

### get all databases
```http
curl --location 'http://localhost:8082/app/all-external-db'
```

### get database for domain
```http
curl --location 'http://localhost:8082/api/getDbForDomain/www.domain1.com'
```


### post image data for processing
```http
curl --location 'http://localhost:8082/api/process' \
--header 'Content-Type: application/json' \
--data '[
    {
        "domain": "www.domain1.com",
        "images": [
            "image1.jpg",
            "somepath/anotherpath/image2.gif"
        ]
    },
    {
        "domain": "www.domain2.com",
        "images": [
            "image3.jpg",
            "somepath/anotherpath/image4.gif"
        ]
    }
]'
```

### get failed image domain data stored in local cache
```http
curl --location 'http://localhost:8082/api/pending-images'
```

### get paginated image paths data
```http
curl --location 'http://localhost:8082/api/image-paths?page=1&size=10'
```

### get paginated image paths data
```http
curl --location 'http://localhost:8082/api/image-paths?page=1&size=2&domain=proident.net'
```