[![Backend Archetype](https://img.shields.io/maven-central/v/com.campusdual/skeleton-backend-archetype?label=Latest%20backend%20archetype&style=flat-square)](https://maven-badges.herokuapp.com/maven-central/com.campusdual/skeleton-backend-archetype)

# Aplicación base arquetipo SpringBoot Campus Dual
Esta aplicación es una **aplicación base** que se usa para generar el arquetipo que usan los alumnos de Campus Dual. Esto significa que cualquier cambio que se desee hacer al arquetipo, se debe hacer en esta aplicación, que generará el nuevo arquetipo a partir de su estructura e información. 

El arquetipo se genera a través de *GitHub Actions*. Estas actions están guardadas en la carpeta <code>.github</code> por
lo que **esta carpeta no debe de modificarse**. También contiene otra action para escanear el código fuente contra 
*SonarCloud* después de hacer un *push* contra main o una *pull request*.

## Cambios que se realizan sobre el proyecto en el arquetipo
* **MANUAL**: El arquetipo **NO PUEDE** contener el fichero ***templateDB.script*** de la base de datos. Si se hace un cambio en la base de datos, después de cerrar correctamente la base de datos con el comando sql <code>SHUTDOWN</code>, el fichero *templateDB.script* contendrá el estado de la base de datos con el estado en el que se quedó, por lo que borraremos el fichero *templateDB.txt* y **renombraremos la extensión del fichero *templateDB.script* a \*.txt** 
* *AUTOMÁTICO*: Se elimina cualquier rastro de las *GitHub Actions*, tanto las de despliegue del arquetipo en *Maven Central* como las de escaneo del código con *SonarCloud* (incluyendo las propiedades del pom.xml relacionadas)
* *AUTOMÁTICO*: Este fichero *README.md* se elimina, y **se renombra el fichero HELP.md como el README.md del arquetipo**

## ¿Cómo usar el arquetipo?
El arquetipo lo podemos usar ejecutando el siguiente comando de Maven en la carpeta que queramos ubicar el proyecto
```
mvn -B archetype:generate -DgroupId=YOUR_PROJECT_GROUP_ID -DartifactId=YOUR_PROJECT_ARTIFACT_ID -Dversion=YOUR_PROJECT_VERSION -Dpackage=YOUR_PROJECT_PACKAGE -DarchetypeGroupId=com.campusdual -DarchetypeArtifactId=skeleton-backend-archetype -DarchetypeVersion=99.9.9-SNAPSHOT -DinteractiveMode=false
```
## ¿Cómo probar el arquetipo?
#### Descargando el fichero que se genera en GitHub Action
Descarga y descomprime (extensión *\*.tar.gz*) el fichero **backend-zip** que se encuentra añadido al resumen de la ejecución de la action del despliegue del arquetipo para tener una carpeta con el proyecto creado
![image](https://i.imgur.com/DOpyK5M.png)
#### Ejecutando el comando Maven
Ejecutar el siguiente comando Maven, que es el mismo que ejecuta la GitHub Action del paso anterior
```
mvn -B archetype:generate -DgroupId=com.campusdual -DartifactId=backendtest -Dversion=1.0.0-SNAPSHOT -Dpackage=com.campusdual.backendtest -DarchetypeGroupId=com.campusdual -DarchetypeArtifactId=skeleton-backend-archetype -DarchetypeVersion=99.9.9-SNAPSHOT -DinteractiveMode=false
```

