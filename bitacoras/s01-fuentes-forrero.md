**Estudiantes:** Juan Fuentes y Esteban Forrero | **Fecha:** 2026-03-29 | **Hito:** hito-S01

### Fase 0 - Predicción (Antes de codificar)
> ¿Qué creo que va a pasar?
- Creemos que al leer el archivo CSV con datos corruptos o valores fuera de rango, el programa de Java va a colapsar si no se manejan las excepciones de forma adecuada.
- El mayor desafío será aislar los errores físicos (como sensores desconectados) de los errores lógicos del archivo sin detener la ejecución completa.

### Fase 1 - Choque (Durante el error)
> ¿Qué se rompió realmente?
- Al procesar líneas con textos inválidos en campos numéricos (como "texto_basura"), el método de parseo lanzó un error de tipo `NumberFormatException`. 
- Aprendimos que un sensor desconectado se identifica con el código `-999`, lo cual requiere una validación de rango físico estricta antes de aceptar el registro como válido.

### Fase 2 - Investigación
- ¿Qué herramientas usamos? Bloques `try/catch` estructurados: un nivel general para el flujo de lectura de archivos (`IOException`) y un nivel interno por cada línea para proteger la conversión de datos numéricos.
- Estudiamos cómo separar las lecturas válidas de las de cuarentena usando listas dinámicas (`ArrayList`).

### Fase 3 - Solución
- Fix aplicado: Creación de la clase `LecturaSensor` para aplicar validaciones físicas y una clase `ProcesadorIngesta` con control de excepciones que aísla los datos corruptos.
- Comandos Git ejecutados:
  ```bash
  git add .
  git commit -m "S01: implementa ingesta confiable y manejo de excepciones"
  git push origin main