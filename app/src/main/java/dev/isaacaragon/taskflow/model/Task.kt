package dev.isaacaragon.taskflow.model

enum class Prioridad {
    BAJO, MEDIO, ALTO
}

enum class Estado {
    PENDIENTE, EN_PROGRESO, COMPLETADA
}

data class Tarea(
    val id: Int,
    val titulo: String,
    val prioridad: Prioridad = Prioridad.MEDIO,
    val estado: Estado = Estado.PENDIENTE,
    val completada: Boolean = false
)
