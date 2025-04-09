
# Repo-to-PDF

`repo-to-pdf` es una herramienta de línea de comandos en Kotlin que convierte repositorios Git (locales o remotos) en archivos PDF. Puedes incluir o excluir archivos y directorios utilizando patrones glob.

`repo-to-pdf` is a command-line tool written in Kotlin that converts Git repositories (local or remote) into PDF files. You can include or exclude files and directories using glob patterns.

---

## :gear: Requisitos / Requirements

- **Java 8 o superior**: Se requiere tener instalado Java para ejecutar la aplicación. Si no tienes Java instalado, puedes descargarlo desde:
  - [Oracle JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
  - [AdoptOpenJDK](https://adoptopenjdk.net/)

- **Java 8 or higher**: You need to have Java installed to run the application. If you don't have Java installed, you can download it from:
  - [Oracle JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
  - [AdoptOpenJDK](https://adoptopenjdk.net/)

---

## :package: Instalación / Installation

### 1. :clipboard: Clonar el repositorio / Clone the repository

Clona el repositorio en tu máquina con el siguiente comando:

Clone the repository to your machine using the following command:

```bash
git clone https://github.com/jasv18/repo-to-pdf.git
cd repo-to-pdf
```

### 2. :floppy_disk: Generar el archivo JAR / Generate the JAR file

Para generar el archivo JAR ejecutable de la herramienta, puedes usar **IntelliJ IDEA** o **Gradle**.

To generate the executable JAR file of the tool, you can use **IntelliJ IDEA** or **Gradle**.

#### Usando IntelliJ IDEA / Using IntelliJ IDEA:

1. Abre el proyecto en IntelliJ IDEA.
2. Configura el artefacto JAR:
   - Ve a `File > Project Structure > Artifacts`.
   - Añade un nuevo artefacto JAR desde el módulo principal de tu aplicación.
   - Asegúrate de que el archivo JAR esté configurado para incluir las dependencias y la clase principal.
3. Construye el artefacto:
   - Ve a `Build > Build Artifacts > Build`.
   - El archivo JAR se generará en el directorio `out/artifacts/`.

#### Usando Gradle / Using Gradle:

Si no usas IntelliJ, también puedes compilar el archivo JAR con Gradle ejecutando el siguiente comando:

If you're not using IntelliJ, you can also compile the JAR file with Gradle by running the following command:

```bash
./gradlew build
```

El archivo JAR se generará en el directorio `build/libs` / The JAR file will be generated in the `build/libs` directory.

---

## :hammer_and_wrench: Uso / Usage

Una vez que hayas generado el archivo JAR (`repo2pdf.jar`), puedes ejecutarlo desde la línea de comandos para convertir repositorios a archivos PDF.

Once you've generated the JAR file (`repo2pdf.jar`), you can run it from the command line to convert repositories to PDF files.

### :pencil: Sintaxis del comando / Command syntax:

```bash
java -jar repo2pdf.jar <source> [options]
```

- `<source>`: Ruta al repositorio (local o remoto). Puede ser una ruta local o una URL de un repositorio Git remoto (por ejemplo, `https://github.com/usuario/repositorio.git`).
- `[options]`: Opciones opcionales para incluir o excluir archivos según patrones glob:
  - `-e` o `--excludes`: Excluir archivos o directorios que coincidan con el patrón de glob proporcionado (por ejemplo, `**/test/**`).
  - `-i` o `--includes`: Incluir solo los archivos que coincidan con los patrones glob especificados (por ejemplo, `*.md`).

### :rocket: Ejecutar la aplicación con Gradle / Running the application with Gradle:

Una vez que el proyecto ha sido clonado y compilado con Gradle, puedes ejecutar la aplicación usando el siguiente comando:

Once the project has been cloned and built with Gradle, you can run the application using the following command:

```bash
./gradlew run --args="<ruta_del_repositorio>"
```

Asegúrate de reemplazar `<ruta_del_repositorio>` con la ruta local a tu repositorio. Por ejemplo:

Make sure to replace `<ruta_del_repositorio>` with the local path to your repository. For example:

```bash
./gradlew run --args="/ruta/al/repositorio"
```

---

## :file_folder: Resultado / Output

El programa generará un archivo PDF con los archivos del repositorio que coincidan con los patrones de inclusión/exclusión. El archivo PDF se guardará en el directorio actual con el nombre `test_report.pdf` (o el nombre predeterminado de la salida).

The program will generate a PDF file with the files from the repository that match the inclusion/exclusion patterns. The PDF file will be saved in the current directory with the name `test_report.pdf` (or the default output name).

---

## :heart: Contribuciones / Contributions

Las contribuciones son bienvenidas. Si encuentras errores o tienes mejoras, por favor abre un **issue** o envía un **pull request**.

Contributions are welcome. If you find any bugs or have improvements, please open an **issue** or send a **pull request**.

---

## :warning: Notas / Notes

- **Repositorios locales**: Actualmente, `repo-to-pdf` solo admite la conversión de repositorios locales. Los repositorios remotos estarán disponibles en futuras versiones.
- **Soporte de patrones glob**: Puedes usar patrones como `**/test/**` para excluir carpetas o `*.md` para incluir solo archivos Markdown.

- **Local repositories**: Currently, `repo-to-pdf` only supports converting local repositories. Remote repositories will be available in future versions.
- **Glob pattern support**: You can use patterns like `**/test/**` to exclude folders or `*.md` to include only Markdown files.
