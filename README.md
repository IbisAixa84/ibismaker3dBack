# ibismaker3d-back

API REST para la gestión de productos de una tienda de impresiones 3D.
Construida con **Spring Boot 3.4**, **Spring Data JPA** y base de datos **H2** en memoria (para desarrollo).

## Requisitos

- Java 20+
- Maven 3.8+

> **Nota sobre certificados (Windows):** si Maven falla al descargar dependencias con un error
> `PKIX path building failed`, es por un proxy/antivirus que inspecciona HTTPS. Solución: usar el
> almacén de certificados de Windows como truststore:
>
> ```bash
> set MAVEN_OPTS=-Djavax.net.ssl.trustStoreType=WINDOWS-ROOT   # Windows CMD
> $env:MAVEN_OPTS="-Djavax.net.ssl.trustStoreType=WINDOWS-ROOT" # PowerShell
> ```

## Ejecutar

```bash
mvn spring-boot:run
```

La API queda disponible en `http://localhost:8080`.
Consola H2 (para ver la BD): `http://localhost:8080/h2-console`
(JDBC URL: `jdbc:h2:mem:ibismaker3d`, usuario `sa`, sin contraseña).

## Endpoints

Base: `/api/products`

| Método | Ruta                 | Descripción                              |
|--------|----------------------|------------------------------------------|
| GET    | `/api/products`      | Lista productos (paginado)               |
| GET    | `/api/products/{id}` | Obtiene un producto por id               |
| POST   | `/api/products`      | Crea un producto                         |
| PUT    | `/api/products/{id}` | Actualiza un producto                    |
| DELETE | `/api/products/{id}` | Elimina un producto                      |

### Parámetros de consulta (GET lista)

- `category` — filtra por categoría (ej: `?category=figuras`)
- `search` — busca por nombre (ej: `?search=maceta`)
- `page`, `size`, `sort` — paginación estándar de Spring (ej: `?page=0&size=10&sort=price,desc`)

### Modelo de producto

```json
{
  "name": "Llavero personalizado",       // obligatorio
  "description": "Llavero con nombre",
  "category": "gadgets",
  "price": 4.99,                           // obligatorio, >= 0
  "stock": 50,                             // obligatorio, >= 0
  "material": "PLA",                       // PLA | ABS | PETG | TPU | NYLON | RESIN | OTHER
  "color": "Azul",
  "weightGrams": 8.0,
  "printTimeMinutes": 30,
  "imageUrl": "https://...",
  "active": true
}
```

### Ejemplos con curl

```bash
# Crear
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{"name":"Llavero","price":4.99,"stock":50,"material":"PLA"}'

# Listar por categoría
curl "http://localhost:8080/api/products?category=figuras"

# Actualizar
curl -X PUT http://localhost:8080/api/products/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"Maceta XL","price":15.0,"stock":10}'

# Eliminar
curl -X DELETE http://localhost:8080/api/products/1
```

## Estructura del proyecto

```
src/main/java/org/example/
├── Ibismaker3dApplication.java     # arranque Spring Boot
├── controller/ProductController    # endpoints REST
├── service/ProductService          # lógica de negocio
├── repository/ProductRepository    # acceso a datos (Spring Data JPA)
├── model/Product, Material          # entidad y enum
├── dto/ProductRequest, ProductResponse
└── exception/                       # 404 y validaciones -> JSON
```

## Pasar a PostgreSQL (producción)

1. Descomentar la dependencia `postgresql` en `pom.xml`.
2. Descomentar el bloque de PostgreSQL en `src/main/resources/application.properties`.
3. Ajustar url/usuario/contraseña.
