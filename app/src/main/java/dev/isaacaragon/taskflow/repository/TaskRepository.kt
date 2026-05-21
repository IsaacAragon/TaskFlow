package dev.isaacaragon.taskflow.repository

import dev.isaacaragon.taskflow.model.Tarea
import dev.isaacaragon.taskflow.model.Prioridad
import dev.isaacaragon.taskflow.model.Estado

class RepositorioTareas {
    private val tareas = mutableListOf<Tarea>(
        Tarea(1, "Tarea 1", Prioridad.BAJO, Estado.PENDIENTE, false),
        Tarea(2, "Tarea 2", Prioridad.MEDIO, Estado.COMPLETADA, true),
        Tarea(3, "Tarea 3", Prioridad.ALTO, Estado.EN_PROGRESO, false),
        Tarea(4, "Tarea 4", Prioridad.BAJO, Estado.COMPLETADA, true),
        Tarea(5, "Tarea 5", Prioridad.MEDIO, Estado.PENDIENTE, false)
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
            val nuevoValorCompletada = !tarea.completada
            val nuevoEstado = if (nuevoValorCompletada) Estado.COMPLETADA else Estado.PENDIENTE
            tareas[indice] = tarea.copy(
                completada = nuevoValorCompletada,
                estado = nuevoEstado
            )
        }
    }
}
