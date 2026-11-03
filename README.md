![Commit Stage](https://github.com/Athena-p466/catalog-service/actions/workflows/commit-stage.yml/badge.svg)

exercise 1: PUT changed from 200 to 201

exercise4: Returning 204 for a missing book is acceptable because DELETE is idempotent: whether the book existed or not, the server ends in the same state, with no book at that ISBN. This makes DELETE safe to retry, for example when a response is lost on the network, without the client mistaking a successful outcome for an error. A 404 would be more informative, but here the client only cares that the book is gone