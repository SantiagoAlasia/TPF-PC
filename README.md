# TRABAJO PRACTICO FINAL - SISTEMA DE PROCESAMIENTO DE DATOS

## ANALISIS DE LA RED DE PETRI
Se pide determinar las propiedades de la red con la herramienta PIPE. Ademas, indicar cuales son los invariantes de Plaza y de Transicion de la red, realizando una breve descripcion de lo que representan en el modelo.

## IMPLEMENTACION 
Se implementara un monitor de concurrencia para la simulacion y ejecucion del modelo:
- Realizar una tabla, con los estados del sistema.
- Realizar una tabla, con los eventos del sistema.
- Determinar la cantidad de hilos nesesarios. Para ello revisar Referencia *1 del enunciado y el diagrama de ejemplo propuesto.
- Debemos tener una interfaz MonitorInterface y una clase monitor que la implemente. Consideraciones: El monitor debe ser agnostico a la red y solo debe tener un unico metodo publico (fireTransition).

Una vez tengamos el sistema anterior funcionando se debera implementar la semantica temporal. Las transiciones [T1, T3, T4, T6, T8, T9,T10} son transiciones TEMPORALES, a las cuales les deberemos asignar un tiempo en ms.
Se debera hacer un analisis temporal analitico como practico, justificando los resultados obtenidos. Es nesesario variar los tiempos elegidos, analizar los resultados y obtener conclusiones.

Para la resolucion de los conflictos, se debera considerar dos POLITICAS distintas. 
  1. Politica aleatoria.
  2. Politica de procesamiento priorizada.

Referencia *1, Ariculo cantidad de hilos: https://www.researchgate.net/publication/358104149_Algoritmos_para_determinar_cantidad_y_responsabilidad_de_hilos_en_sistemas_embebidos_modelados_con_Redes_de_Petri_S_3_PR1

## REQUERIMIENTOS
- El proyecto debe ser modelado con objetos de JAVA, haciendo uso de un monitor de concurrencia para guiar la ejecucion de la RdP.
  1. El programa debe tener una clase main que al correrla inicio el programa.
  2. El programa debe finalizar, no puede quedar hilos activos al terminar la ejecucion.
- Implementar un objeto Politica que cumpla con los objetivos establecidos.
- Hacer un Diagrama de Clases que modele el sistema.
- Hacer un Diagrama de Secuencia que muestre el disparo exitoso de una transicion que este sensiblizada, mostrando el uso de politica. (Ejemplo en el LEV)
- Indicar la cantidad de hilos nesearios y justificar.
- Hacer uso de un Log para el analisis de las transiciones disparadas. Este analisis debe ser de 200 invariantes completados (para cada ejecucion), y debera mostrar el cumplimiento de las politicas en la distribucion de la carga en los invariantes, como asi la cantidad de cada tipo de invariante (justificando el resultado).
- Hacer un analisis de tiempos, el programa debe durar entre 20 y 40 segundos.
- Mostrar e interpretar los invariantes de plaza y transiciones que posee la red.
- Verificar el cumplimiento de invariantes de plazas luego de cada disparo de la red.
- Verificar el cumplimiento de invariantes de transiciones mediante el analisis de un archivo lof de las transiciones disparadas al finalizar la ejecucion. (Para esto hay que usar expresiones regulares)

## ENTREGABLES
- Un archivo de imagen con el diagrama de clases, y con el diagrama de secuencias. (Agregarlo en documentacion)
- Codigo fuente. (Agregarlo en una carpeta src)
- Un informe. (Agregarlo en documentacion)
