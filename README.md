# 🚌 TransCesar S.A.S. — Sistema de Gestión de Tickets Intermunicipales

Sistema de consola desarrollado en Java para la gestión de venta de tickets, registro de vehículos, conductores, pasajeros y reservas de la empresa de transporte intermunicipal TransCesar S.A.S.

---

## 📋 Descripción

El sistema permite gestionar de forma digital las operaciones de la empresa, reemplazando el proceso manual de venta de tickets. Incluye registro de vehículos, conductores y pasajeros, venta de tickets con descuentos automáticos, módulo de reservas con anticipación, reportes y estadísticas de operaciones.

---

## 🏗️ Arquitectura

El proyecto está organizado en cuatro capas siguiendo el patrón de **Arquitectura en Capas**:

```
src/main/java/transcesar/
├── model/        → Entidades del negocio
├── dao/          → Persistencia en archivos .txt
├── service/      → Reglas de negocio
└── view/         → Interfaz de consola
```

La comunicación fluye en una sola dirección: `view → service → dao → model`.

---

## 🗂️ Estructura del Proyecto

```
TransCesar/
├── src/
│   └── main/
│       └── java/
│           └── transcesar/
│               ├── model/
│               │   ├── Imprimible.java
│               │   ├── Calculable.java
│               │   ├── Persona.java
│               │   ├── Conductor.java
│               │   ├── Pasajero.java
│               │   ├── Vehiculo.java
│               │   ├── Bus.java
│               │   ├── Buseta.java
│               │   ├── MicroBus.java
│               │   ├── Ruta.java
│               │   ├── Ticket.java
│               │   └── Reserva.java
│               ├── dao/
│               │   ├── VehiculoDAO.java
│               │   ├── RutaDao.java
│               │   ├── ConductorDAO.java
│               │   ├── PasajeroDAO.java
│               │   ├── TicketDAO.java
│               │   └── ReservaDAO.java
│               ├── service/
│               │   ├── VehiculoService.java
│               │   ├── RutaService.java
│               │   ├── PersonaService.java
│               │   ├── TicketService.java
│               │   └── ReservaService.java
│               ├── view/
│               │   ├── Menu.java
│               │   ├── MenuVehiculos.java
│               │   ├── MenuRutas.java
│               │   ├── MenuPersonas.java
│               │   ├── MenuTickets.java
│               │   ├── MenuEstadisticas.java
│               │   ├── MenuReportes.java
│               │   └── MenuReservas.java
│               └── TransCesar.java
└── datos/
    ├── buseta.txt
    ├── bus.txt
    ├── microbus.txt
    ├── rutas.txt
    ├── conductores.txt
    ├── pasajeros.txt
    ├── tickets.txt
    └── reservas.txt
```

---

## ⚙️ Requisitos

- Java JDK 17 o superior
- IntelliJ IDEA o Eclipse
- No requiere dependencias externas

---

## 🚀 Cómo ejecutar

1. Clonar el repositorio:
```bash
git clone https://github.com/CamiloDuranCode/TransCesar.git
```

2. Abrir el proyecto en IntelliJ IDEA o Eclipse.

3. Asegurarse de que existe la carpeta `datos/` en la raíz del proyecto con los archivos `.txt` vacíos.

4. Ejecutar la clase principal `TransCesar.java`.

---

## 📌 Funcionalidades

### Gestión de Rutas
- Registrar rutas con código, origen, destino, distancia y tiempo estimado
- Listar rutas disponibles

### Gestión de Vehículos
- Registrar vehículos de tipo Bus, Buseta o MicroBus
- Asignar una ruta existente al registrar
- Validación de placa duplicada
- Listar vehículos registrados

### Gestión de Personas
- Registrar conductores con número y categoría de licencia (B1, B2, C1, C2)
- Registrar pasajeros con fecha de nacimiento
- Autodetección del tipo de pasajero según edad:
  - **Adulto Mayor** (≥ 60 años) → 30% de descuento
  - **Estudiante** (18 a 24 años) → 15% de descuento
  - **Regular** → sin descuento

### Venta de Tickets
- Vender tickets verificando cupos disponibles en el vehículo
- Aplicar descuento automático según tipo de pasajero
- Aplicar recargo del 20% en días festivos colombianos
- Límite de 3 tickets por pasajero por día

### Módulo de Reservas
- Crear reservas con código único generado automáticamente
- Cancelar reservas por código
- Listar reservas activas
- Ver historial completo de reservas de un pasajero
- Convertir reserva en ticket aplicando todas las reglas de venta
- Verificar y cancelar automáticamente reservas vencidas (más de 24 horas)

### Reportes y Estadísticas
- Total de dinero recaudado
- Cantidad de pasajeros por tipo
- Vehículo con más tickets vendidos
- Resumen del día actual
- Filtrar tickets por fecha, tipo de vehículo o tipo de pasajero

---

## 💾 Persistencia

Cada entidad se almacena en su propio archivo de texto plano en la carpeta `datos/`. Los campos se separan con punto y coma (`;`). Al iniciar el sistema los archivos se cargan automáticamente en memoria.

| Entidad | Archivo |
|---|---|
| Busetas | `datos/buseta.txt` |
| Buses | `datos/bus.txt` |
| MicroBuses | `datos/microbus.txt` |
| Rutas | `datos/rutas.txt` |
| Conductores | `datos/conductores.txt` |
| Pasajeros | `datos/pasajeros.txt` |
| Tickets | `datos/tickets.txt` |
| Reservas | `datos/reservas.txt` |

---

## 👥 Equipo de Desarrollo

| Rol | Responsabilidades | Rama |
|---|---|---|
| Líder de Desarrollo | Repositorio, merges, capa View, Ticket, RutaService | `main` · `feature/model-view` |
| Desarrollador 1 | Jerarquía Vehiculo, VehiculoDAO, RutaDao, VehiculoService | `feature/vehiculos` |
| Desarrollador 2 | Jerarquía Persona, Ticket, Reserva, DAOs, Services | `feature/personas-ticket` |

---

## 📚 Asignatura

**Programación III — Taller 2: Arquitectura en Capas**  
Universidad Popular del Cesar  
Docente: Ing. Esp. Alfredo Bautista
