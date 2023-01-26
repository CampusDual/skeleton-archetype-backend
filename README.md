# Aplicación base arquetipo SpringBoot Campus Dual
Esta aplicación es una **aplicación base** que se usa para generar el arquetipo que usan los alumnos de Campus Dual. 

El arquetipo se genera a través de *GitHub Actions*. Estas actions están guardadas en la carpeta <code>.github</code> por
lo que **esta carpeta no debe de modificarse**. También contiene otra action para escanear el código fuente contra 
*SonarCloud* después de hacer un *push* contra main o una *pull request*.

## Cambios que se realizan sobre el proyecto en el arquetipo
* **MANUAL**: El arquetipo **NO PUEDE** contener el fichero ***templateDB.script*** de la base de datos. Si se hace un cambio en la base de datos, después de cerrar correctamente la base de datos con el comando sql <code>SHUTDOWN</code>, el fichero *templateDB.script* contendrá el estado de la base de datos con el estado en el que se quedó, por lo que borraremos el fichero *templateDB.txt* y **renombraremos la extensión del fichero *templateDB.script* a \*.txt** 
* *AUTOMÁTICO*: Se elimina cualquier rastro de las *GitHub Actions*, tanto las de despliegue del arquetipo en *Maven Central* como las de escaneo del código con *SonarCloud* (incluyendo las propiedades del pom.xml relacionadas)
* *AUTOMÁTICO*: Este fichero *README.md* se elimina, y **se renombra el fichero HELP.md como el README.md del arquetipo**
