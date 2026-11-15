# Trabajo Práctico Final – Sistema de Procesamiento de Datos – Programación Concurrente 2025
Facultad de Ciencias Exactas, Físicas y Naturales (FCEFyN) – Universidad Nacional de Córdoba (UNC)

## ¿De qué se trata?

El proyecto simula en **Java** un sistema de procesamiento de datos modelado con una **red de Petri**. Cada dato que llega al sistema es tomado de un buffer de entrada, procesado por una unidad de procesamiento y depositado en un buffer de salida. La ejecución de la red está guiada por un **monitor de concurrencia**, que garantiza exclusión mutua y decide qué hilo puede disparar cada transición.

### El modelo

| Elemento | Descripción |
|---|---|
| **P0** | Plaza idle: cola de arribo de datos (3 tokens iniciales) |
| **P1** | Dato accediendo al buffer a través del bus |
| **P2** | Recurso compartido: bus de acceso al buffer |
| **P3** | Buffer de datos a procesar |
| **P6** | Recurso compartido: unidad de procesamiento |
| **P11** | Buffer de salida |

Cada dato se procesa por uno de tres modos:

- **Simple:** una etapa (P7).
- **Medio:** dos etapas (P4, P5).
- **Alto:** tres etapas (P8, P9, P10).

La elección del modo es un **conflicto** de la red que se resuelve mediante una política.

## Características principales

- **Monitor de concurrencia** (`MonitorInterface`) con `fireTransition(int transition)` como único método público. El monitor es **agnóstico a la red**: no referencia transiciones puntuales, por lo que cambiar la red no requiere modificarlo.
- **Semántica temporal** en las transiciones {T1, T3, T4, T6, T8, T9, T10}, con tiempos configurables en milisegundos.
- **Dos políticas de resolución de conflictos**, ejecutadas y analizadas por separado:
  1. **Aleatoria:** los datos se procesan por cualquiera de los tres modos al azar.
  2. **Priorizada:** se prioriza el modo de procesamiento simple.
- **Cantidad de hilos** determinada con el algoritmo del artículo de referencia (ver abajo), maximizando el paralelismo.
- **Verificación de invariantes:**
  - Invariantes de plaza: se verifican después de cada disparo.
  - Invariantes de transición: se verifican al finalizar, analizando el log de disparos con **expresiones regulares**.
- **Log de ejecución** para el análisis posterior de resultados.
- Cada ejecución completa **200 invariantes** y debe finalizar **sin hilos activos**, con una duración de entre **20 y 40 segundos**.

## Estructura del repositorio

```
.
├── src/             # Código fuente Java
├── docs/            # Informe, diagrama de clases y diagramas de secuencia
├── logs/            # Logs de ejecución
├── pipe/            # Red de Petri (.xml) para PIPE
└── README.md
```

## Análisis de la red

Las propiedades de la red (deadlock, vivacidad, seguridad) y los invariantes de plaza y de transición se determinaron con **[PIPE](https://pipe2.sourceforge.net/)** (Platform Independent Petri net Editor). El archivo de la red está en `pipe/`.

## Documentación

- Diagrama de clases
- Diagrama de secuencia del disparo exitoso de una transición (con uso de la política)
- Informe con criterios adoptados, análisis de tiempos y resultados de las ejecuciones

## Referencias

- Enunciado del TP Final de Programación Concurrente 2025, FCEFyN – UNC.
- **Cantidad y responsabilidad de hilos:** *Algoritmos para determinar cantidad y responsabilidad de hilos en sistemas embebidos modelados con Redes de Petri.* ResearchGate.
  <https://www.researchgate.net/publication/358104149_Algoritmos_para_determinar_cantidad_y_responsabilidad_de_hilos_en_sistemas_embebidos_modelados_con_Redes_de_Petri_S_3_PR1>

## Integrantes

- Feiguin, Lucia
- Monutti, Elena
- Alasia, Santiago
