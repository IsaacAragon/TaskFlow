package dev.isaacaragon.taskflow.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.isaacaragon.taskflow.model.Priority
import dev.isaacaragon.taskflow.model.Status
import dev.isaacaragon.taskflow.viewmodel.TaskViewModel

@Composable
fun TaskScreen(
    viewModel: TaskViewModel = viewModel(),
    cerrarDialogo: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { cerrarDialogo() },
        title = {
            Text(if (viewModel.isEditing) "Editar tarea" else "Nueva tarea")
        },
        text = {
            Column {
                // Campo ID
                OutlinedTextField(
                    value = viewModel.id,
                    onValueChange = { viewModel.onIdChange(it) },
                    label = { Text("ID") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !viewModel.isEditing // Disable ID editing if already exists
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Campo título
                OutlinedTextField(
                    value = viewModel.title,
                    onValueChange = { viewModel.onTitleChange(it) },
                    label = { Text("Título") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Selector de Prioridad
                PrioritySelector(
                    selectedPriority = viewModel.priority,
                    onPrioritySelected = { viewModel.onPriorityChange(it) }
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Selector de Estado
                StatusSelector(
                    selectedStatus = viewModel.status,
                    onStatusSelected = { viewModel.onStatusChange(it) }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (viewModel.id.isNotEmpty() && viewModel.title.isNotEmpty()) {
                        if (viewModel.isEditing) {
                            viewModel.updateTask()
                        } else {
                            viewModel.addTask()
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
fun PrioritySelector(
    selectedPriority: Priority,
    onPrioritySelected: (Priority) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedPriority.name,
            onValueChange = {},
            readOnly = true,
            label = { Text("Prioridad") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            Priority.values().forEach { priority ->
                DropdownMenuItem(
                    text = { Text(priority.name) },
                    onClick = {
                        onPrioritySelected(priority)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatusSelector(
    selectedStatus: Status,
    onStatusSelected: (Status) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedStatus.name,
            onValueChange = {},
            readOnly = true,
            label = { Text("Estado") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            Status.values().forEach { status ->
                DropdownMenuItem(
                    text = { Text(status.name) },
                    onClick = {
                        onStatusSelected(status)
                        expanded = false
                    }
                )
            }
        }
    }
}