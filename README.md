# Product Tracker API

## Installation

1. Clone the repository:
```
git clone https://github.com/Leroyst03/tracker-api.git
```

2. Navigate to the project directory:
```
cd tracker
```

3. Build the project using Maven:
```
mvn clean install
```

4. Run the application:
```
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`.

## Usage

The Product Tracker API provides the following endpoints:

### Authentication

#### Login
```
POST /auth/login
```
Request Body:
```json
{
  "email": "user@example.com",
  "password": "password"
}
```
Response:
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjE4MDc2ODAwLCJleHAiOjE2MTg2ODE2MDB9.Xt5IxMXAL7XxY3ybhdmhAx4nio4HkFFBSr_P3k-Rvl-V_i2HRd5_Tgmv2PYQrTKMgQ"
}
```

#### Sign Up
```
POST /auth/sign-up
```
Request Body:
```json
{
  "email": "user@example.com",
  "password": "password"
}
```
Response:
```
Usuario registrado!
```

### Product Records

#### Get Records
```
GET /api/records
```
Response:
```json
[
  {
    "id": 1,
    "nombreProducto": "Product 1",
    "cantidad": 10,
    "precio": 9.99,
    "fecha": "2023-04-01"
  },
  {
    "id": 2,
    "nombreProducto": "Product 2",
    "cantidad": 5,
    "precio": 14.99,
    "fecha": "2023-04-02"
  }
]
```

#### Save Records
```
POST /api/records
```
Request Body:
```json
{
  "productList": [
    {
      "nombre": "Product 1",
      "cantidad": 10,
      "precio": 9.99
    },
    {
      "nombre": "Product 2",
      "cantidad": 5,
      "precio": 14.99
    }
  ]
}
```
Response:
```
Ventas guardadas
```

#### Delete Records
```
DELETE /api/records
```
Request Body:
```json
[1, 2]
```
Response:
```
Ventas eliminadas
```

#### Update Records
```
PUT /api/records?purchaseId=1
```
Request Body:
```json
{
  "nombre": "Updated Product",
  "cantidad": 15,
  "precio": 12.99
}
```
Response:
```
Ventas actualizadas
```

## API

The Product Tracker API provides the following endpoints:

### Authentication

- `POST /auth/login`: Authenticate a user and return a JWT token.
- `POST /auth/sign-up`: Register a new user.

### Product Records

- `GET /api/records`: Retrieve the user's product records.
- `POST /api/records`: Save new product records.
- `DELETE /api/records`: Delete product records.
- `PUT /api/records`: Update a product record.

All endpoints that interact with product records require a valid JWT token in the `Authorization` header (e.g., `Authorization: Bearer <token>`).
