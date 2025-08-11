# TPFinalConcurrente2025

## Compilación y Ejecución del Proyecto

Asegúrate de estar en la raíz del proyecto (`TPFinalConcurrente2025`).

### 1. Compilar todos los archivos Java

En PowerShell o CMD:

```
javac -d bin src\hilos\*.java src\log\*.java src\monitor\*.java src\politica\*.java src\Main.java
```

En Git Bash o terminal compatible con Unix:

```
javac -d bin src/hilos/*.java src/log/*.java src/monitor/*.java src/politica/*.java src/Main.java
```

### 2. Ejecutar el programa principal

Si `Main.java` **NO** tiene declaración de paquete (no tiene `package` al inicio):

```
java -cp bin Main
```

Si `Main.java` **TIENE** declaración de paquete, por ejemplo `package src;`, ejecuta:

```
java -cp bin src.Main
```

### Notas
- Asegúrate de que la carpeta `bin` exista y esté vacía antes de compilar si quieres limpiar compilaciones anteriores. Remove-Item -Recurse -Force .\bin\* (para vaciar el bin)
- Si agregas nuevas clases, recuerda incluirlas en la compilación.
- Si tienes problemas, revisa los mensajes de error para ver si faltan archivos o hay errores de sintaxis.
