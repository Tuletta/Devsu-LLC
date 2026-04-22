# Sistema Bancario Devsu - Solución Técnica

Este repositorio contiene la solución completa para la prueba técnica de Devsu, que consiste en un sistema de gestión de clientes, cuentas y movimientos bancarios.

## Cómo Probar la Solución

Todo el entorno está contenedorizado para facilitar su ejecución. Solo necesitas tener **Docker** y **Docker Compose** instalados.

1. **Clonar el repositorio:**
   ```bash
   git clone https://github.com/Tuletta/Devsu-LLC.git
   cd Devsu-LLC
   ```

2. **Desplegar con Docker Compose:**
   ```bash
   docker-compose up --build -d
   ```

3. **Acceder a las aplicaciones:**
   - **Frontend (UI Premium):** [http://localhost](http://localhost)
   - **Backend (API):** [http://localhost:8081](http://localhost:8081)
   - **Base de Datos:** PostgreSQL en el puerto 5432.

---

## Tecnologías Utilizadas

### Backend
- **Java 17** con **Spring Boot 3.2.4**.
- **Spring Data JPA** para la persistencia.
- **PostgreSQL 15** como motor de base de datos.
- **iText7** para la generación de reportes PDF dinámicos.
- **JUnit 5 & Mockito** para pruebas unitarias.
- **Lombok** para reducir código repetitivo.

### Frontend
- **React 18** con **Vite**.
- **TypeScript** para tipado fuerte.
- **Vanilla CSS** (Custom Design System) - Sin frameworks externos.
- **Lucide React** para iconografía.
- **Vitest** para pruebas unitarias.

---

## Decisiones de Arquitectura

- **Patrón Strategy:** Implementado en la lógica de movimientos para separar el cálculo de saldos entre créditos y débitos, facilitando la escalabilidad.
- **Global Exception Handling:** Uso de `@RestControllerAdvice` para capturar errores de negocio (saldo insuficiente, cupo diario, etc.) y retornar respuestas estandarizadas.
- **Multi-Stage Docker Builds:** Optimización de imágenes Docker separando la etapa de construcción de la de ejecución.
- **Clean UI/UX:** Interfaz diseñada desde cero con enfoque en *Quiet Luxury* y *Glassmorphism* para una experiencia de usuario moderna.

---

## Pruebas Unitarias

Para ejecutar las pruebas manualmente sin Docker:

**Backend:**
```bash
cd backend
mvn test
```

**Frontend:**
```bash
cd frontend
npm test
```

---

## Colección de Postman

El archivo para validar los endpoints se encuentra en:
`./postman/BancaDevsu.postman_collection.json`

Solo importa este archivo en Postman para probar todos los flujos de Clientes, Cuentas y Movimientos.

---
¡Muchos éxitos en la evaluación!
