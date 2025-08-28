# Ticketera API

API para gestión de tickets de clientes con soporte de **cola de atención**. Permite crear tickets, agregarlos a la cola, tomar tickets y cambiar su estado.

## 🔹 Tecnologías

- Java 17
- Spring Boot 3
- Hibernate / JPA
- MySQL
- Postman (para pruebas)
- Mermaid (diagramas en GitHub)
- Dotenv (variables de entorno)

---

## 🔹 Arquitectura

Esta aplicación sigue un patrón de capas clásico:

- **Entidad (Entity)**: Representa las tablas de la base de datos (`Cliente`, `Consulta`, `Ticket`).
- **DTO (Data Transfer Object)**: Se usa para no exponer las entidades directamente en los endpoints.
- **Repository**: Interfaces de JPA para acceso a datos.
- **Service**: Lógica de negocio y manejo de la cola.
- **Controller**: Endpoints REST que exponen la funcionalidad.

Las configuraciones sensibles, como credenciales de la base de datos, se manejan mediante **variables de entorno** con `dotenv`.

---

## 🔹 Entidades principales

### Ticket
| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | Long | ID autoincremental |
| cliente | Cliente | Cliente asociado |
| consulta | Consulta | Tipo de consulta |
| fecha_hora | Date | Fecha y hora del ticket |
| estado | Enum (EN_PROCESO, ATENDIDO, EN_ESPERA, FINALIZADO) | Estado del ticket |

### Cliente
| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | Long | ID autoincremental |
| nombre | String | Nombre completo |
| dni | String | Documento |
| email | String | Email |

### Consulta
| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | Long | ID autoincremental |
| tipoConsulta | Enum (CUENTAS, CAJA_AHORRO, TARJETA_CREDITO, PAGOS, BENEFICIOS, SEGUROS, OTROS) | Tipo de consulta |

---
---
### Notas importantes

* Todos los IDs son autoincrementales.
* Los enums para estado y tipo de consulta deben respetarse.
* La cola es FIFO: el primer ticket agregado será el primero en ser tomado.
* POST /api/cola/tomar se queda bloqueado si la cola está vacía hasta que se agregue un ticket.
* Se utiliza DTO para no exponer directamente las entidades en los endpoints.
* Variables sensibles como la conexión a la base de datos se cargan mediante dotenv.