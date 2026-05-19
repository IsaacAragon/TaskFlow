package dev.isaacaragon.taskflow.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.isaacaragon.taskflow.model.Priority
import dev.isaacaragon.taskflow.viewmodel.TaskViewModel


@Composable
fun ListScreen(
    viewModel: TaskViewModel = viewModel(),
    abrirPantallaTarea: () -> Unit
) {

    Scaffold(

        floatingActionButton = {

            FloatingActionButton(

                onClick = {
                    viewModel.clearForm()
                    abrirPantallaTarea()
                }

            ) {

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar tarea"
                )
            }
        }

    ) { padding ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)

        ) {

            Text(
                text = "Lista de tareas",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            if (viewModel.tasks.isEmpty()) {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {

                    Text("No hay tareas registradas")
                }

            } else {

                LazyColumn {

                    items(viewModel.tasks) { tarea ->

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {

                            Row(

                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),

                                horizontalArrangement =
                                    Arrangement.SpaceBetween,

                                verticalAlignment =
                                    Alignment.CenterVertically

                            ) {

                                Column(modifier = Modifier.weight(1f)) {

                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "ID: ${tarea.id}",
                                            fontWeight = FontWeight.Bold,
                                            style = MaterialTheme.typography.labelMedium
                                        )
                                        Spacer(modifier = Modifier.padding(4.dp))
                                        PriorityBadge(tarea.priority)
                                    }

                                    Text(
                                        text = tarea.title,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    
                                    Text(
                                        text = "Estado: ${tarea.status}",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Checkbox(
                                        checked = tarea.completed,
                                        onCheckedChange = { viewModel.toggleTask(tarea) }
                                    )

                                    IconButton(onClick = {
                                        viewModel.loadTask(tarea.id)
                                        abrirPantallaTarea()
                                    }) {
                                        Icon(Icons.Default.Edit, contentDescription = "Editar")
                                    }

                                    IconButton(onClick = {
                                        viewModel.removeTask(tarea)
                                    }) {
                                        Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PriorityBadge(priority: Priority) {
    val color = when (priority) {
        Priority.LOW -> Color.Green
        Priority.MEDIUM -> Color.Yellow
        Priority.HIGH -> Color.Red
    }
    Surface(
        color = color.copy(alpha = 0.2f),
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = priority.name,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
            style = MaterialTheme.typography.labelSmall,
            color = color
        )
    }
}