# Crud_Java_MVC

Este proyecto es una aplicación de escritorio desarrollada en Java que sigue el patrón de diseño Modelo-Vista-Controlador (MVC). La aplicación permite gestionar información de una inmobiliaria, incluyendo inmuebles, clientes, propietarios y agentes comerciales.

## Características

- **Gestión de Inmuebles:** Permite registrar, consultar, actualizar y eliminar inmuebles.
- **Gestión de Clientes:** Administración de la información de los clientes.
- **Gestión de Propietarios:** Administración de la información de los propietarios de los inmuebles.
- **Gestión de Agentes Comerciales:** Administración de los agentes comerciales de la inmobiliaria.
- **Autenticación de Usuarios:** Sistema de login para controlar el acceso a la aplicación.
- **Persistencia de Datos:** Utiliza una base de datos PostgreSQL para almacenar la información.
- **Arquitectura MVC:** El proyecto está estructurado en tres capas:
    - **Modelo:** Contiene la lógica de negocio y el acceso a los datos.
    - **Vista:** Proporciona la interfaz de usuario.
    - **Controlador:** Actúa como intermediario entre el Modelo y la Vista.

## Tecnologías Utilizadas

- **Lenguaje:** Java
- **Base de Datos:** PostgreSQL
- **Patrón de Diseño:** Modelo-Vista-Controlador (MVC)

## Cómo Ejecutar el Proyecto

1.  **Clonar el repositorio:**
    ```bash
    git clone https://github.com/lozadandres/Crud_Java_MVC.git
    ```
2.  **Abrir el proyecto:**
    - Abrir el proyecto en su IDE de Java preferido (por ejemplo, NetBeans, Eclipse, IntelliJ IDEA).
3.  **Configurar la base de datos:**
    - Asegurarse de tener PostgreSQL instalado y en ejecución.
    - Crear una base de datos para el proyecto.
    - Configurar la conexión a la base de datos en el archivo `modelo/PostgresConexion.java`.
4.  **Ejecutar la aplicación:**
    - Construir y ejecutar el proyecto desde el IDE.

## Colaboradores

- Andrés Lozada
