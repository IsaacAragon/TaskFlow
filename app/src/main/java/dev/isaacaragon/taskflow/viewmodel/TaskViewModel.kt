package dev.isaacaragon.taskflow.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dev.isaacaragon.taskflow.model.Prioridad
import dev.isaacaragon.taskflow.model.Estado
import dev.isaacaragon.taskflow.model.Tarea
import dev.isaacaragon.taskflow.repository.RepositorioTareas

class TaskViewModel : ViewModel() {

    private val repositorio = RepositorioTareas()

    var tareas by mutableStateOf(listOf<Tarea>())
        private set

    var id by mutableStateOf("")
        private set

    var titulo by mutableStateOf("")
        private set

    var prioridad by mutableStateOf(Prioridad.MEDIO)
        private set

    var estado by mutableStateOf(Estado.PENDIENTE)
        private set

    var completada by mutableStateOf(false)
        private set

    var estaEditando by mutableStateOf(false)
        private set

    init {
        cargarTareas()
    }

    fun alCambiarId(nuevoId: String) {
        id = nuevoId
    }

    fun alCambiarTitulo(nuevoTitulo: String) {
        titulo = nuevoTitulo
    }

    fun alCambiarPrioridad(nuevaPrioridad: Prioridad) {
        prioridad = nuevaPrioridad
    }

    fun alCambiarEstado(nuevoEstado: Estado) {
        estado = nuevoEstado
        if (nuevoEstado == Estado.COMPLETADA) {
            completada = true
        } else if (completada) {
            completada = false
        }
    }

    fun alCambiarCompletada(nuevoCompletada: Boolean) {
        completada = nuevoCompletada
        if (nuevoCompletada) {
            estado = Estado.COMPLETADA
        } else if (estado == Estado.COMPLETADA) {
            estado = Estado.PENDIENTE
        }
    }

    private fun cargarTareas() {
        tareas = repositorio.obtenerTareas()
            .sortedByDescending { it.prioridad }
            .toList()
    }

    fun cargarTarea(idTarea: Int?) {
        if (idTarea == null) {
            limpiarFormulario()
            estaEditando = false
            return
        }

        val tarea = repositorio.obtenerTareaPorId(idTarea)
        tarea?.let {
            id = it.id.toString()
            titulo = it.titulo
            prioridad = it.prioridad
            estado = it.estado
            completada = it.completada
            estaEditando = true
        }
    }

    fun agregarTarea() {
        if (titulo.isNotEmpty()) {
            val siguienteId = (tareas.maxOfOrNull { it.id } ?: 0) + 1
            val tarea = Tarea(
                id = siguienteId,
                titulo = titulo,
                prioridad = prioridad,
                estado = estado,
                completada = completada
            )
            repositorio.agregarTarea(tarea)
            cargarTareas()
            limpiarFormulario()
        }
    }

    fun actualizarTarea() {
        if (id.isNotEmpty() && titulo.isNotEmpty()) {
            val tarea = Tarea(
                id = id.toInt(),
                titulo = titulo,
                prioridad = prioridad,
                estado = estado,
                completada = completada
            )
            repositorio.actualizarTarea(tarea)
            cargarTareas()
            limpiarFormulario()
            estaEditando = false
        }
    }

    fun eliminarTarea(tarea: Tarea) {
        repositorio.eliminarTarea(tarea)
        cargarTareas()
    }

    fun alternarTarea(tarea: Tarea) {
        repositorio.alternarTarea(tarea)
        cargarTareas()
    }

    fun limpiarFormulario() {
        id = ""
        titulo = ""
        prioridad = Prioridad.MEDIO
        estado = Estado.PENDIENTE
        completada = false
        estaEditando = false
    }
}
