# Documentacion Tecnica

# Índice
* [Compilar](#compilar)
* [Arquitectura](#arquitectura)

<a id="compilar"></a>
## Como compilar la aplicación
Al tratarse de una aplicación java, necesitamos 2 cosas :
1. El *.jar* que permite ejecutar la app.
2. Servirlo con *jre* que permita su ejecución sin instalar java completo.

Para ello hay que seguir las siguientes instrucciones:
### Hacer .jar
Se ha adjuntado un Dockerfile, el cual incluye un contenedor con **maven** preparado para hacer el ejecutable java.

Para usarlo, es necesario situarse en el directorio donde se encuentra el fichero **pom.xml** (el cual indica a maven que hacer e indica la raíz de nuestro proyecto).

Una vez en ese directorio hay que construir la imagen:
```bash
docker build -t constructor-java .
```
Una vez hayamos contruido la imagen podremos usarla para hacer el jar simepre que queramos, usando el siguiente comando:
```bash
docker run --rm -v ${PWD}/out:/export constructor-java
```
Este nos deja el *.jar* en el directorio *out\*.

Este *.jar* ya es perfectamente usable (siempre que java 21 o posterior esté instalado) mediante el comando:
```bash
java -jar <nombre-del-jar>
```
*nota*: para probarlo de esta forma es preferible hacerlo dentro del directorio *out\\* para evitar generar más directorios con ficheros (log, navision, sepa) en la raíz del proyecto. Así como eliminar cualquier otra cosa que no sea el *.jar* dentro de este directorio, tras ejecutar la app de esta forma.

Es recomendable comprobar que el progama se ejecuta correctamente con nuestra versión de java antes de pasar al siguiente paso.

### "Empaquetarlo" con un jre
Antes de continuar es necesario instalar una versión del *jdk* (ya que es bastante más rápido que reiniciar el docker engine y descargar una imagen de docker con windows), para ello basta con usar uno de estos enlaces:
- enlace de descarga directa para jdk 21 https://download.oracle.com/java/21/latest/jdk-21_windows-x64_bin.exe
- enlace a Oracle para seleccionar el release (mínimo jdk 21) https://www.oracle.com/es/java/technologies/downloads/

Para poder lanzar la app sin preocuparnos por instalar la versión de java adecuada, lo empaquetaremos con nuestra versión del *jre*.

Para ello usaremos este comando desde el diretorio donde está el fichero *pom.xml*:
```bash
& 'C:\Program Files\Java\jdk-21\bin\jpackage.exe' --name "<nombre-de-la-apliacción>" --input .\out --main-jar <nombre-del-.jar>.jar --type app-image --win-console
```
Explicación del comando :
1. ```& 'C:\Program Files\Java\jdk-21\bin\jpackage.exe'``` indica la ruta al ejecutable de *jpackage* la razón de hacerlo así y no con variable del *$PATH* es que esta forma nos permite indicar la verión específica del *jre* que queremos usar. Observa que en la ruta indicamos **jdk-21**, este directorio contiene los binarios del *jdk* 21, lo que nos permite "empaquetar" la aplicación con esa versión de java, así como evitamos errores al asegurarnos de apuntar al ejecutable que queremos y no al de una variable del sistema que podría apuntar a una versión de java anterior a la que necesitamos.
2. ``--name "<nombre-de-la-apliacción>"`` indica el nombre de nuestra app.
3. ``--input .\out`` directorio donde debe buscar el *.jar*.
4. ``--main-jar <nombre-del-.jar>.jar`` el nombre del jar con el main (es inportante, sobre todo si se tuviesen como dependencias otros proyectos en *.jar*).
5. ``--type app-image`` el tipo de empaquetado que queremos.
6. ``--win-console`` le indica que muestre la terminal (en nuestro caso es útil, ya que levantamos un servidor el cual debe finalizarse para cerrar la app).

Esto nos dejará un directorio con el nombre de la aplicación, el cual contendrá un *.exe*, este ejecutable no debe moverse fuera de ese directorio (si es posible mover todo el directorio con el nombre de la app) ya que requiere las dependencias del runtime y el jar (sin esto de nada nos sirve haberla empaquetado con el *jre*).

<a id="arquitectura"></a>
## Aquitectura de la App
La aplicación se distrubuye en las siguientes capas:
- **Core**. Aquí se encuentran el modelo de dominio, las excepciones y los repositorios.
- **Application**. Aquí se encuentran el controlador, los casos de uso, las configuraciones de springboot(CorsConfig), el BrowserLauncher y el main.
- **Infraestructura**. Esta es la capa más cambiante. Aquí van los servicios(de dominio, parsers, ...), los puertos con sus adaptadores, los utils, las constantes(del paquete constants), el dominio del sepa.


