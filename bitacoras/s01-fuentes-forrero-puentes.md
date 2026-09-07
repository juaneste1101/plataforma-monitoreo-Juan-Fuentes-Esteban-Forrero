# Bitacora grupal - Semana 01

## 1. Datos de la actividad

- **Estudiantes:** Juan Fuentes, Esteban Forrero, Pierre Puentes
- **Equipo:** Juan-Fuentes-Esteban-Forrero-Pierre-Puentes
- **Semana:** 01
- **Fecha del laboratorio:** 2026-03-29
- **Fecha del taller:** 2026-03-29
- **Tema principal:** Ingesta confiable de datos y manejo de excepciones
- **Pregunta de la semana:** ¿Cómo procesar un archivo de texto corrupto sin que el sistema colapse?

## 2. Prediccion antes de ejecutar

1. **Que creo que va a ocurrir?**
   Creemos que al leer el archivo `lecturas.csv` con un texto como "texto_basura" en la columna de temperatura, la conversión a numérico (`Double.parseDouble`) fallará y detendrá la ejecución del programa.
2. **Que parte del programa o del algoritmo puede fallar?**
   El parseo de variables dentro del ciclo `while` encargado de leer cada línea del archivo.
3. **Como comprobare mi prediccion?**
   Ejecutaremos el procesador inyectando líneas mal formadas o con valores físicos imposibles (ej. -999) en el CSV sin utilizar bloques `try/catch`.

## 3. Evidencia del laboratorio

### Resultado observado
Al ejecutar el sistema sin protección, la terminal devolvió un `NumberFormatException` en el primer dato basura. El programa abortó inmediatamente y no se procesó el resto del archivo.

### Diferencia entre la prediccion y el resultado
El resultado validó la predicción. Java es estricto con el tipado; un error de conversión no manejado es fatal.

### Error o comportamiento inesperado
- **Que ocurrio?** Caída del sistema al intentar convertir letras a números.
- **Por que ocurrio?** Ausencia de control en el flujo alternativo frente a datos corruptos.
- **Como lo corregimos o que falta corregir?** Implementamos un bloque `try/catch` para capturar excepciones por línea, derivando los datos erróneos a una lista de "cuarentena" y permitiendo que el bucle continúe iterando.

## 4. Explicacion en lenguaje llano

El manejo de excepciones funciona como el control de calidad en una fábrica. El programa intenta ("try") empacar cada producto (línea de texto). Si un producto viene defectuoso, en lugar de apagar toda la fábrica, atrapa ("catch") el producto malo, lo manda a una caja de revisión (cuarentena), y la máquina sigue empacando el resto sin detener la producción.

### Ejemplo o analogia
Es como estar leyendo una lista de compras y encontrarse con una palabra tachada o ilegible. No botas la lista entera ni dejas de hacer las compras; te saltas ese artículo defectuoso, compras lo demás y revisas el problema al final.

## 5. El vacio que encontre

- **Mi duda concreta es:** ¿Abusar de `try/catch` dentro de un bucle masivo afecta el rendimiento en tiempo de ejecución?
- **Lo que ya puedo explicar es:** Su uso previene cierres abruptos y permite segmentar datos útiles de basura.
- **Para resolver la duda consulte:** Documentación sobre la Máquina Virtual de Java (JVM).
- **Ahora lo entiendo asi:** El bloque `try` casi no consume recursos si los datos son correctos. El impacto en rendimiento (creación del objeto Exception y el stack trace) ocurre solo cuando se dispara el `catch`.

## 6. Trazado de la solucion

Trazado del comportamiento ante dos escenarios en el `ProcesadorIngesta`.

| Paso | Estado de los datos o estructura | Decision o resultado |
|---|---|---|
| 1 | `EST-001,2026-03-29 08:00,12.4,5.1` | Parseo numérico exitoso. El registro se guarda en la lista válida. |
| 2 | `EST-002,texto_basura,60.0,5.5` | Dispara `NumberFormatException`. |
| 3 | Ejecución del `catch` | Se captura el error, se arma un log de "texto_basura" y se añade a cuarentena. |
| 4 | Continuidad | El bucle retoma la línea 3. El sistema no se cae. |

## 7. Decision de diseño

- **Problema que debiamos resolver:** Cómo aislar los registros corruptos sin alterar el archivo original `.csv`.
- **Estructura, algoritmo o estrategia elegida:** Listas dinámicas (`ArrayList`) para separar en memoria los datos válidos y los de cuarentena.
- **Alternativa descartada:** Eliminar directamente las líneas defectuosas del archivo de texto.
- **Por que elegimos la primera:** Modificar la fuente de datos rompe la trazabilidad. Las listas en memoria mantienen intacta la evidencia cruda.
- **Que evidencia respalda la decision:** La consola reporta un resumen con el conteo de datos limpios y en cuarentena sin reescribir el disco.

## 8. Aporte al proyecto

- **Archivo(s) o modulo(s) trabajado(s):** `src/ProcesadorIngesta.java`, `src/LecturaSensor.java`.
- **Cambio realizado:** Lógica de validación física, aislamiento por excepciones y ruteo a listas en memoria.
- **Como se conecta con la capa anterior:** Actúa como el puente seguro entre el archivo bruto del sistema operativo y las estructuras de datos de la plataforma.
- **Que queda pendiente para la siguiente semana:** Cargar la data validada a un TAD de arreglo dinámico y a una matriz bidimensional.

## 9. Commits realizados

| Commit | Mensaje | Que demuestra |
|---|---|---|
| `(Añadir hash)` | `S01: verifica ingesta confiable con dataset de prueba y excepciones` | Funcionalidad core terminada. |
| `(Añadir hash)` | `DOC: actualiza README y repositorio para tercer integrante Pierre` | Trabajo colaborativo. |

## 10. Reexplicacion final

Para garantizar la estabilidad del sistema frente a archivos corruptos, encapsulamos el parseo en un bloque `try/catch`. Esta estructura intercepta el `NumberFormatException`, desviando el registro defectuoso a un contenedor de cuarentena. Al no interrumpir el bucle de lectura, procesamos los datos válidos en O(n) manteniendo la continuidad operativa.

## 11. Reflexion individual

1. **Lo que ahora puedo hacer y antes no podia:** Aislar errores físicos de sensores (-999) y lógicos (cadenas en campos numéricos) programáticamente.
2. **El error o supuesto que mas me enseno:** Asumir que toda falla venía del código, cuando en realidad la basura proviene del mundo real (hardware o ruido).
3. **La pregunta que llevaria a la proxima clase:** Si escalamos el `.csv` a gigabytes, ¿esta aproximación de guardarlo todo en listas RAM sigue siendo viable?
4. **Que parte del trabajo fue realmente mia:** El análisis de los límites físicos y lógicos debatido entre Juan, Esteban y Pierre.

## Lista de verificacion antes de entregar

- [x] Escribi la prediccion antes de consultar el resultado.
- [x] Inclui evidencia concreta del laboratorio.
- [x] Explique un concepto sin depender de jerga.
- [x] Registre un vacio, una duda o un error real.
- [x] Trace al menos un caso paso a paso.
- [x] Justifique una decision del proyecto y una alternativa descartada.
- [x] Registre mis commits y mi aporte individual.
- [x] Deje claro que queda pendiente.
- [x] Renombre el archivo con el formato requerido.