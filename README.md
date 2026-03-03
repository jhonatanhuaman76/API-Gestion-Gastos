# Gestión de Gastos - API REST

## Descripción del Proyecto

Aplicación web para el control de gastos. El proyecto está compuesto por un backend desarrollado en Java Spring Boot y un frontend en HTML/CSS/JavaScript.

### Funcionalidades Principales

- **Crear Gasto**: Agregar nuevos gastos con validación de datos
- **Listar Gastos**: Ver todos los gastos registrados
- **Actualizar Gasto**: Modificar gastos existentes
- **Eliminar Gasto**: Eliminar gastos no deseados
- **Filtrar por Mes/Año**: Ver gastos específicos por fecha

## Tecnologías Utilizadas

### Backend
- **Java 21**: Lenguaje de programación
- **Spring Boot 4.0.3**: Framework de desarrollo
- **Spring Web MVC**: Para crear la API REST
- **Bean Validation**: Validación de datos
- **Jackson**: Manejo de JSON
- **Maven**: Gestor de dependencias
- **JUnit 5**: Testing unitario
- **Mockito**: Simulación de dependencias en tests

### Frontend
- **HTML5/CSS3**: Estructura y estilos
- **JavaScript**: Lógica de la aplicación
- **Bootstrap 5.3.2**: Framework CSS
- **SweetAlert2**: Notificaciones y alertas

### Almacenamiento de Datos
- **JSON**: Almacenamiento de gastos en archivos locales

## Estructura del Proyecto

El backend implementa una arquitectura en capas para la separación de responsabilidades:

```
API-Gestion-de-Gastos/
├── backend/               # Aplicación Spring Boot
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/mibanco/apigastos/
│   │   │   │   ├── application/
│   │   │   │   │   ├── service/               # Lógica de negocio
│   │   │   │   |   └── mapper/                # Mapeo de entidades
│   │   │   │   ├── domain/           
│   │   │   │   │   ├── model/                 # Entidades de negocio
│   │   │   │   │   └── repository/            # Repositorio de dominio (interfaces)
│   │   │   │   ├── infra/            
│   │   │   │   │   ├── repository/            # Manejo de datos (JSON)
│   │   │   │   │   ├── exception/             # Manejador de Errores
│   │   │   │   │   └── web/
│   │   │   │   │       ├── dto/               # DTOs
│   │   │   │   |       └── controller/        # Controladores REST
│   │   │   │   └── config/                    # Configuración
│   │   │   └── resources/
│   │   │       └── application.yaml           # Configuración de la app
│   │   └── test/                              # Tests unitarios
│   ├── pom.xml                                # Configuración de Maven
│   └── app/                                   # Almacenamiento de datos (gastos.json)
│
└── frontend/              # Aplicación Frontend
    ├── index.html         # HTML principal
    ├── app.js             # Lógica JavaScript
    └── styles.css         # Estilos CSS
```

## Instalación y Setup

### 1. Requisitos Previos

- **Java Development Kit (JDK) 21** o superior
- **Maven 3.9** o superior

### 2. Clonar el Repositorio
```bash
git clone https://github.com/jhonatanhuaman76/API-Gestion-Gastos.git
cd "API-Gestion-de-Gastos"
```

### 3. Configurar Variables de Entorno

El proyecto utiliza variables de entorno para configurar valores dinámicos. Crea un archivo `.env` en la raíz del proyecto o configura las variables en tu sistema operativo:

```bash
# Ejemplo de URL del frontend (usada para CORS)
FRONTEND_URL=http://localhost:3000
```

### Ejecutar la Aplicación

#### 1. Usando Maven para el backend

```bash
cd backend
mvn spring-boot:run
```

#### 2. Levantar un servidor para el frontend

En una terminal diferente:

```bash
cd frontend
# Usa un servidor web simple
# Con Python 3:
python -m http.server 3000
# Con Node.js:
npx http-server -p 3000
```

**Accede a:**
- Backend: `http://localhost:8080`
- Frontend: `http://localhost:3000`

## Uso de la Aplicación

### Reglas de Validación para Gastos

Todos los campos de los gastos cumplen con las siguientes validaciones:

- **titulo**: Requerido, 3-100 caracteres
- **motivo**: Requerido, 3-255 caracteres
- **fecha**: Requerido, formato ISO (YYYY-MM-DD)
- **monto**: Requerido, > 0, máximo 15 dígitos enteros y 2 decimales

### Endpoints del Backend

La API REST está disponible en `http://localhost:8080/mibancoapi/gastos`

#### 1. **Crear Gasto**

**Endpoint:**
```
POST /mibancoapi/gastos
```

**Headers:**
```
Content-Type: application/json
```

**Body (Request):**
```json
{
  "titulo": "Cine",
  "motivo": "Salida al cine con amigos",
  "fecha": "2025-09-15",
  "monto": 200.00
}
```

**Response (201 Created):**
```json
{
  "id": "40e4d24c-365f-4dae-a2ab-89963170c2e4",
  "titulo": "Cine",
  "motivo": "Salida al cine con amigos",
  "fecha": "2025-09-15",
  "monto": 200.00
}
```

**Reglas de Validación:** Ver sección de validaciones arriba

---

#### 2. **Listar Todos los Gastos**

**Endpoint:**
```
GET /mibancoapi/gastos
```

**Response (200 OK):** Retorna un array de gastos con el mismo formato que Crear Gasto

---

#### 3. **Actualizar Gasto**

**Endpoint:**
```
PUT /mibancoapi/gastos/{id}
```

**Parámetros:** `id` (Path): UUID del gasto

**Body (Request):**
```json
{
  "titulo": "Cine Actualizado",
  "motivo": "Salida al cine con familia",
  "fecha": "2025-09-15",
  "monto": 250.00
}
```

**Response (200 OK):** Retorna el gasto actualizado con la misma estructura que Crear Gasto

**Reglas de Validación:** Ver sección de validaciones arriba

---

#### 4. **Eliminar Gasto**

**Endpoint:**
```
DELETE /mibancoapi/gastos/{id}
```

**Parámetros:**
- `id` (Path): UUID del gasto

**Response (204 No Content):**
```
(Sin cuerpo)
```

---

#### 5. **Listar Gastos por Mes y Año**

**Endpoint:**
```
GET /mibancoapi/gastos/mes-anio?mes={mes}&anio={anio}
```

**Parámetros Query:**
- `mes` (Integer): Mes (1-12)
- `anio` (Integer): Año (ej: 2025)

**Response (200 OK):** Retorna un array de gastos del mes/año especificado (mismo formato que Listar Todos los Gastos)

---

### Interfaz del Frontend

La aplicación incluye una interfaz web intuitiva con:
- **Tabla de gastos** con acciones de Editar y Eliminar
- **Filtros** por mes (1-12) y año (YYYY)
- **Total acumulado** de gastos
- **Modal formulario** para agregar/editar gastos

Flujos básicos:
- **Agregar:** Click en "Agregar Gasto" → Llenar formulario → Guardar
- **Editar:** Click en "Editar" → Modificar datos → Guardar
- **Eliminar:** Click en "Eliminar" → Confirmar
- **Filtrar:** Ingresar mes/año → Click "Filtrar" (dejar vacíos para ver todos)

## Testing

El proyecto incluye tests unitarios e integración en `backend/src/test/java/`:
- `GastoServiceTest.java`: Pruebas unitarias para la lógica de negocio
- `GastoControllerTest.java`: Pruebas de integración para el controlador REST
- `GastoRepositoryTest.java`: Pruebas unitarias para el repositorio de datos

Se proporcionan datos de prueba en `backend/src/test/resources/json/gasto/`:
- `request-crear-gasto.json`
- `response-crear-gasto.json`
- `request-actualizar-gasto.json`
- `response-actualizar-gasto.json`
- `response-listar-gastos.json`
- `response-listar-mes-anio-gastos.json`

**Ejecutar tests:**

```bash
cd backend

# Todos los tests
mvn test

# Tests específicos
mvn test -Dtest=ApiGestionDeGatosApplicationTests

# Con reporte
mvn surefire-report:report
```

## Automatización CI/CD con GitHub Actions

El proyecto incluye un workflow de GitHub Actions para:
- Compilar y construir la aplicación
- Ejecutar tests automáticamente en cada push
- Empaquetar la aplicación con Maven en un JAR listo para producción

## Prompts utilizados

Se utilizó el siguiente prompt para Microsoft Copilot IA para generar la interfaz web para agilizar el desarrollo del frontend:

```
Actúa como un desarrollador frontend senior.

Genera un frontend completo en HTML, CSS y JavaScript modular (ES6) para consumir mi API REST de gestión de gastos desarrollada en Spring Boot.

BASE URL:
http://localhost:8080/mibancoapi/gastos

ENDPOINTS:

GET /mibancoapi/gastos
→ Lista todos los gastos (200 OK)

GET /mibancoapi/gastos/mes-anio?mes=9&anio=2025
→ Lista gastos filtrados por mes y año (200 OK)

POST /mibancoapi/gastos
→ Crea un gasto (201 Created)

PUT /mibancoapi/gastos/{id}
→ Actualiza un gasto (200 OK)

DELETE /mibancoapi/gastos/{id}
→ Elimina un gasto (204 No Content)

Modelo JSON de Gasto:

{
  "id": "40e4d24c-365f-4dae-a2ab-89963170c2e4",
  "titulo": "Cine",
  "motivo": "Salida de Cine con amigos",
  "fecha": "2025-09-15",
  "monto": 45.50
}

REQUISITOS FUNCIONALES:

1. Vista principal:
   - Título: "Gestión API de Gastos"
   - Filtro por mes y año encima del listado
   - Botón "Agregar Gasto"
   - Tabla con columnas:
     Título | Motivo | Fecha | Monto | Editar | Eliminar
   - Mostrar total acumulado de gastos debajo de la tabla

2. Formularios:
   - Reutilizable para crear y editar gasto
   - Validaciones:
     - Campos obligatorios
     - Fecha válida (yyyy-MM-dd)
     - Monto numérico positivo con máximo 2 decimales

3. Alertas y confirmaciones:
   - Usa **SweetAlert2** para:
     - Confirmación antes de eliminar
     - Mensajes de éxito al crear o editar
     - Mensajes de error si falla la petición

REQUISITOS TÉCNICOS:

- Código modular y limpio en archivos separados:
  - index.html
  - styles.css
  - app.js
- Importar **Bootstrap 5** y **SweetAlert2** mediante CDN en index.html
- Usar Fetch API para todas las peticiones
- Manejar errores HTTP mostrando mensajes adecuados
- Diseño minimalista, profesional y responsive
- Comentarios claros en el código explicando la funcionalidad
- No usar frameworks adicionales fuera de Bootstrap y SweetAlert2

Genera todo el código completo listo para ejecutar, con estructura funcional de CRUD y filtrado por mes y año.
```

### Autor

**Jhonatan Huaman** - [GitHub](https://github.com/jhonatanhuaman76)

**Última actualización:** 3 de marzo de 2026
