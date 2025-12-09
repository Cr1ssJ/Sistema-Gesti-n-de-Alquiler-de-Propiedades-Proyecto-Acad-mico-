# Sistema de Gestión de Alquiler de Propiedades
### Proyecto Final – Programación I (Java)

**Integrantes del grupo:**  
- Cristian Jimenez  
- Cristian Guevara  
- Lia Reyes  
- Jassier Hernandez  

Este repositorio contiene el desarrollo completo de un **Sistema de Gestión de Alquileres**, construido siguiendo principios sólidos de **Programación Orientada a Objetos (POO)**, diseñado para simular la administración integral de propiedades, contratos, pagos, mantenimientos y actores involucrados (inquilinos, propietarios, técnicos y administradores).

El proyecto fue desarrollado a lo largo de los **Avances #1, #2 y #3**, incorporando modelado UML, diseño del dominio, modularización del código, manejo de entradas/salidas, validaciones y presentación del sistema.  
Actualmente, el sistema se encuentra **finalizado** y estructurado para ser mantenido y ampliado fácilmente.

---

## Objetivos del Sistema
- Gestionar propiedades, propietarios e inquilinos.
- Registrar contratos de alquiler con sus características y fechas.
- Procesar pagos y gestionar planes de pago.
- Registrar solicitudes de mantenimiento y asignarlas a técnicos.
- Generar reportes para la administración.
- Simular un flujo completo de un sistema de alquiler a nivel académico.

---

## Principales Funcionalidades
- **Gestión de usuarios del sistema**
  - Administrador, Propietario, Inquilino, Técnico.
- **Manejo de contratos de alquiler**
  - Creación, modificación y seguimiento del contrato.
- **Procesamiento de pagos**
  - Planes de pago, historial, montos y fechas.
- **Registro de propiedades**
  - Dirección, dueño, estado y características.
- **Notificaciones**
  - Simulación de envío y registro de notificaciones internas.
- **Módulo de mantenimiento**
  - Solicitud, asignación de técnico y seguimiento.
- **Generación de reportes**
  - Reportes administrativos y de actividad del sistema.

---

## Arquitectura del Proyecto

El sistema sigue un diseño modular basado en POO:

- **domain/** → Contiene todas las entidades del sistema.  
- **utils/** → Herramientas para manejo de entrada/salida (IOUtils).  
- **Main.java** → Punto de entrada del programa.  
- **docs/** → Documentación académica del proyecto.  
- **uml/** → Diagramas UML utilizados en el diseño (Avance #1 y #2).

---

##  Estructura del repositorio
```
alquiler-propiedades/
├── src/
│   ├── main/
│   │   ├── resources/
│   │   └── utp/
│   │       ├── ac/
│   │       │   └── pa/
│   │       │       └── sistema/
│   │       │           ├── Main.java               ← Clase principal
│   │       │           ├── utils/
│   │       │           │   └── IOUtils.java
│   │       │           └── domain/
│   │       │               ├── Administrador.java
│   │       │               ├── ContratoAlquiler.java
│   │       │               ├── DataStore.java
│   │       │               ├── Direccion.java
│   │       │               ├── EstadoContrato.java
│   │       │               ├── EstadoSolicitud.java
│   │       │               ├── GestorReportes.java
│   │       │               ├── GestorUsuarios.java
│   │       │               ├── Inquilino.java
│   │       │               ├── Notificacion.java
│   │       │               ├── Pago.java
│   │       │               ├── Propiedad.java
│   │       │               ├── Propietario.java
│   │       │               ├── ServicioIncluido.java
│   │       │               ├── SolicitudMantenimiento.java
│   │       │               ├── Tecnico.java
│   │       │               ├── TipoPropiedad.java
│   │       │               └── Usuario.java
│   │       │               ├── ValidacionException.java
├── docs/
│   ├── Avance1_Documentacion_Sistema_Alquiler.docx
│   ├── Avance1_Documentacion_Sistema_Alquiler.pdf
│   ├── Documento de Diseño de Interfaz y Validaciones - Avance 3.pdf
│   ├── Tablas Avance #2.docx
│   ├── diagrama de flujo.png
│   ├── uml/
│   │   ├── Avance #1 Proyecto Programacion.png
│   │   ├── Diagrama Final UML.png
│   │   ├── Diagrama UML Actualizado.png
│   │   
└── README.md
```

---
## 🛠️ Tecnologías Utilizadas
- **Java 17**
- Programación Orientada a Objetos (POO)
- Diseño UML (Clases & Relaciones)
- Validaciones de entrada

---

##  Escenarios clave
1. Registro de propiedades y contratos.  
2. Control de pagos y vencimientos.  
3. Solicitudes de mantenimiento.  
4. Reportes por ubicación y tipo de propiedad.  
5. Servicios incluidos en cada propiedad.  
6. Gestión de propiedades y usuarios.

---
 ## Decisiones de Diseño Importantes
- El modelo se centró en **entidades**, manteniendo la lógica de registro dentro de cada clase según las indicaciones académicas.
- No se añadió un controlador central para respetar la estructura requerida.
- El sistema permite futuras extensiones como:
  - Conexión a base de datos.
  - Interfaz gráfica (JavaFX / Swing).
  - Autenticación real de usuarios.

---
   ## Enfoque Académico  
Este proyecto forma parte del desarrollo progresivo del curso de Programación I, demostrando:

- Aplicación de conceptos de POO (herencia, composición, encapsulación).
- Modelado mediante diagramas UML.
- Construcción iterativa del sistema.
- Implementación de casos de uso reales en software académico.
- Buenas prácticas de estructuración y documentación.

## Autor del Repositorio
**Cristian Jiménez** (Cr1ssJ) 
Estudiante de Ciberseguridad – Universidad Tecnológica de Panamá  
Enfoque en ciberseguridad defensiva, análisis de eventos y seguridad en la nube (AWS)

---

## Licencia
Este proyecto es de uso académico. Puede ser utilizado como referencia o base educativa.

> Proyecto desarrollado para la asignatura **Programación I**, Universidad Tecnológica de Panamá (UTP).
