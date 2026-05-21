package dev.isaacaragon.taskflow.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.isaacaragon.taskflow.model.Prioridad
import dev.isaacaragon.taskflow.model.Estado
import dev.isaacaragon.taskflow.viewmodel.TaskViewModel

@Composable
fun TaskScreen(
    viewModel: TaskViewModel = viewModel(),
    cerrarDialogo: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { cerrarDialogo() },
        title = {
            Text(if (viewModel.estaEditando) "Editar tarea" else "Nueva tarea")
        },
        text = {
            Column {
                // Campo título
                OutlinedTextField(
                    value = viewModel.titulo,
                    onValueChange = { viewModel.alCambiarTitulo(it) },
                    label = { Text("Título") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Selector de Prioridad
                SelectorPrioridad(
                    prioridadSeleccionada = viewModel.prioridad,
                    alSeleccionarPrioridad = { viewModel.alCambiarPrioridad(it) }
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Selector de Estado
                SelectorEstado(
                    estadoSeleccionado = viewModel.estado,
                    alSeleccionarEstado = { viewModel.alCambiarEstado(it) }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (viewModel.titulo.isNotEmpty()) {
                        if (viewModel.estaEditando) {
                            viewModel.actualizarTarea()
                        } else {
                            viewModel.agregarTarea()
                        }
                        cerrarDialogo()
                    }
                }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = { cerrarDialogo() }) {
                Text("Cancelar")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectorPrioridad(
    prioridadSeleccionada: Prioridad,
    alSeleccionarPrioridad: (Prioridad) -> Unit
) {
    var expandido by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expandido,
        onExpandedChange = { expandido = !expandido }
    ) {
        OutlinedTextField(
            value = prioridadSeleccionada.name,
            onValueChange = {},
            readOnly = true,
            label = { Text("Prioridad") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandido) },
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expandido,
            onDismissRequest = { expandido = false }
        ) {
            Prioridad.entries.forEach { prioridad ->
                DropdownMenuItem(
                    text = { Text(prioridad.name) },
                    onClick = {
                        alSeleccionarPrioridad(prioridad)
                        expandido = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectorEstado(
    estadoSeleccionado: Estado,
    alSeleccionarEstado: (Estado) -> Unit
) {
    var expandido by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expandido,
        onExpandedChange = { expandido = !expandido }
    ) {
        OutlinedTextField(
            value = estadoSeleccionado.name,
            onValueChange = {},
            readOnly = true,
            label = { Text("Estado") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandido) },
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expandido,
            onDismissRequest = { expandido = false }
        ) {
            Estado.entries.forEach { estado ->
                DropdownMenuItem(
                    text = { Text(estado.name) },
                    onClick = {
                        alSeleccionarEstado(estado)
                        expandido = false
                    }
                )
            }
        }
    }
}
