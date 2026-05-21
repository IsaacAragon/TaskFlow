package dev.isaacaragon.taskflow.repository

import dev.isaacaragon.taskflow.model.Tarea
import dev.isaacaragon.taskflow.model.Prioridad
import dev.isaacaragon.taskflow.model.Estado

class RepositorioTareas {

    //"ALMACENAMIENTO TEMPORAL
    private val tareas = mutableListOf<Tarea>(
        Tarea(1, "Tarea Analisis", Prioridad.BAJO, Estado.PENDIENTE, false),
        Tarea(2, "Tarea Base de Datos", Prioridad.MEDIO, Estado.COMPLETADA, true),
        Tarea(3, "Tarea POO II", Prioridad.ALTO, Estado.EN_PROGRESO, false),
        Tarea(4, "Tarea Foro Ingles", Prioridad.BAJO, Estado.COMPLETADA, true),
        Tarea(5, "Tarea Redes", Prioridad.MEDIO, Estado.PENDIENTE, false)
    )

    fun obtenerTareas(): List<Tarea>  = tareas

    fun agregarTarea(tarea: Tarea) = tareas.add(tarea)

    fun obtenerTareaPorId(id: Int): Tarea? = tareas.find { it.id == id }

    fun eliminarTarea(tarea: Tarea) = tareas.remove(tarea)

    fun actualizarTarea(tareaActualizada: Tarea) {
        val indice = tareas.indexOfFirst { it.id == tareaActualizada.id }
        if (indice != -1) {
            tareas[indice] = tareaActualizada
        }
    }

    fun alternarTarea(tarea: Tarea) {
        val indice = tareas.indexOf(tarea)
        if (indice != -1) {
            // 1. Calculamos el nuevo valor (si estaba true, pasa a false y viceversa)
            val nuevoValorCompletada = !tarea.completada

            // 2. LÓGICA AUTOMÁTICA: Si se marca como completada, el estado cambia a COMPLETADA.
            // Si se desmarca, vuelve a PENDIENTE.
            val nuevoEstado = if (nuevoValorCompletada) Estado.COMPLETADA else Estado.PENDIENTE

            // 3. Actualizamos la lista con la copia de la tarea modificada
            tareas[indice] = tarea.copy(
                completada = nuevoValorCompletada,
                estado = nuevoEstado
            )
        }
    }
}
