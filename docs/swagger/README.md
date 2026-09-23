# Swagger / OpenAPI

The app auto-generates a live spec via springdoc (`config/OpenApiConfig.java`, configured in `application.properties`):

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- Raw OpenAPI JSON: `http://localhost:8080/v3/api-docs`

[`openapi.yaml`](openapi.yaml) in this folder is a static, hand-maintained snapshot of the same spec — useful for offline viewing, importing into other tools (Postman, Insomnia, code generators), or diffing when the API surface changes. It should be re-synced whenever endpoints or DTOs change.

To refresh it from the running app instead of editing by hand:

```bash
curl http://localhost:8081/v3/api-docs.yaml -o docs/swagger/openapi.yaml
```

(requires the app to be running against a reachable MySQL instance — see [`docs/api/payment-api.md`](../api/payment-api.md) for the base URL and endpoint reference).
