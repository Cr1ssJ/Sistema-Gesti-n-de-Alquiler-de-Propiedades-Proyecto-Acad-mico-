# Sistema de Gestión de Alquiler de Propiedades

**Integrantes del grupo:**  
- Cristian Jimenez  
- Cristian Guevara  
- Lia Reyes  
- Jassier Hernandez  

---

## 🧩 Descripción del proyecto
El **Sistema de Gestión de Alquiler de Propiedades** permite administrar propiedades, contratos de alquiler, inquilinos, pagos, mantenimiento y reportes.  
Este avance corresponde al **Avance #1**, que incluye la definición de clases, escenarios clave, y la estructura del repositorio en GitHub.

---

## 📁 Estructura del repositorio
```
alquiler-propiedades/
├── src/
│   ├── main/
│   │   ├── resources/
│   │   └── utp/
│   │       ├── ac/
│   │       │   └── pa/
│   │       │       └── sistema/
│   │       │           ├── App.java               ← Clase principal
│   │       │           ├── utils/
│   │       │           │   └── IOUtils.java
│   │       │           └── domain/
│   │       │               ├── Administrador.java
│   │       │               ├── ContratoAlquiler.java
│   │       │               ├── Direccion.java
│   │       │               ├── GestorReportes.java
│   │       │               ├── Inquilino.java
│   │       │               ├── Notificacion.java
│   │       │               ├── Pago.java
│   │       │               ├── PlanPago.java
│   │       │               ├── Propiedad.java
│   │       │               ├── Propietario.java
│   │       │               ├── ServicioIncluido.java
│   │       │               ├── SolicitudMantenimiento.java
│   │       │               ├── Tecnico.java
│   │       │               └── Usuario.java
├── docs/
│   ├── Avance1_Documentacion_Sistema_Alquiler.docx
│   ├── uml/
│   │   ├── uml_clases_simplificado.png
│   │   └── uml_clases_simplificado.dot
└── README.md
```

---

## 📄 Contenido de `docs/`
| Archivo | Descripción |
|----------|-------------|
| `Avance1_Documentacion_Sistema_Alquiler.docx` | Contiene las tablas de escenarios, clases, atributos y métodos. |
| `uml_clases_simplificado.png` | Diagrama UML simplificado del sistema. |
| `uml_clases_simplificado.dot` | Versión editable del diagrama UML (Graphviz). |

---

## 🧠 Escenarios clave
1. Registro de propiedades y contratos.  
2. Control de pagos y vencimientos.  
3. Solicitudes de mantenimiento.  
4. Reportes por ubicación y tipo de propiedad.  
5. Servicios incluidos en cada propiedad.  
6. Gestión de propiedades y usuarios.

---

## 📅 Avance #1 — Entregables
- ✅ Documento de Word con tablas de escenarios y clases.  
- ✅ Diagrama UML con clases, atributos y asociaciones.  
- ✅ Estructura completa de proyecto en GitHub.

---

> Proyecto desarrollado para la asignatura **Programación I**, Universidad Tecnológica de Panamá (UTP).
