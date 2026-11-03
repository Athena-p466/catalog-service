# Catalog Service API demo

## POST /books → 201 Created

```
http POST :9001/books isbn=1234567891 title="Northern Lights" author="Lyra Silverstar" price:=9.90
```

```
HTTP/1.1 201
Connection: keep-alive
Content-Length: 86
Content-Type: application/json
Date: Mon, 02 Nov 2026 18:11:17 GMT
Keep-Alive: timeout=15

{
    "author": "Lyra Silverstar",
    "isbn": "1234567891",
    "price": 9.9,
    "title": "Northern Lights"
}
```

## POST /books (duplicate) → 422 Unprocessable Entity

```
http POST :9001/books isbn=1234567891 title="Northern Lights" author="Lyra Silverstar" price:=9.90
```

```
HTTP/1.1 422
Connection: keep-alive
Content-Length: 43
Content-Type: application/json
Date: Mon, 02 Nov 2026 18:13:31 GMT
Keep-Alive: timeout=15

A book with ISBN 1234567891 already exists.
```

## POST /books (invalid) → 400 Bad Request

```
http POST :9001/books isbn=123ABC456Z title="" author="Jon Snow" price:=9.90
```

```
HTTP/1.1 400
Connection: close
Content-Length: 83
Content-Type: application/json
Date: Mon, 02 Nov 2026 18:14:43 GMT

{
    "isbn": "The ISBN format must be valid.",
    "title": "The book title must be defined."
}

```

## GET /books/{isbn} → 200 OK

```
http :9001/books/1234567891
```

```
HTTP/1.1 200
Connection: keep-alive
Content-Length: 86
Content-Type: application/json
Date: Mon, 02 Nov 2026 18:15:06 GMT
Keep-Alive: timeout=15

{
    "author": "Lyra Silverstar",
    "isbn": "1234567891",
    "price": 9.9,
    "title": "Northern Lights"
}
```

## GET /books → 200 OK

```
http :9001/books
```

```
HTTP/1.1 200
Connection: keep-alive
Content-Length: 88
Content-Type: application/json
Date: Mon, 02 Nov 2026 18:15:38 GMT
Keep-Alive: timeout=15

[
    {
        "author": "Lyra Silverstar",
        "isbn": "1234567891",
        "price": 9.9,
        "title": "Northern Lights"
    }
]
```

## PUT /books/{isbn} → 200 OK

```
http PUT :9001/books/1234567891 isbn=1234567891 title="Northern Lights" author="Lyra Silverstar" price:=12.50
```

```
HTTP/1.1 200
Connection: keep-alive
Content-Length: 87
Content-Type: application/json
Date: Mon, 02 Nov 2026 18:16:10 GMT
Keep-Alive: timeout=15

{
    "author": "Lyra Silverstar",
    "isbn": "1234567891",
    "price": 12.5,
    "title": "Northern Lights"
}
```

## PUT → 201 OK

```
http PUT :9001/books/9999999999 isbn=9999999999 title="New Book" author="Someone" price:=5.00
```

```
HTTP/1.1 201
Connection: keep-alive
Content-Type: application/json
Date: Tue, 03 Nov 2026 04:33:36 GMT
Keep-Alive: timeout=15
Transfer-Encoding: chunked

{
    "author": "Someone",
    "isbn": "9999999999",
    "price": 5.0,
    "title": "New Book"
}
```

## DELETE /books/{isbn} → 204 No Content

```
http DELETE :9001/books/1234567891
```

```
HTTP/1.1 204
Connection: keep-alive
Date: Mon, 02 Nov 2026 18:16:24 GMT
Keep-Alive: timeout=15
```

## GET /books/{isbn} (deleted) → 404 Not Found

```
http :9001/books/1234567891
```

```
HTTP/1.1 404
Connection: keep-alive
Content-Length: 44
Content-Type: text/plain;charset=UTF-8
Date: Mon, 02 Nov 2026 18:17:02 GMT
Keep-Alive: timeout=15

The book with ISBN 1234567891 was not found.
```