package dev.isaacaragon.taskflow.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
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

                    // Limpiar campos
                    viewModel.onIdChange("")
                    viewModel.onTitleChange("")

                    // Abrir pantalla emergente
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

                                Column {

                                    Text(
                                        text = "ID: ${tarea.id}",
                                        fontWeight = FontWeight.Bold
                                    )

                                    Text(
                                        text = tarea.title
                                    )
                                }

                                Checkbox(
                                    checked = tarea.completed,
                                    onCheckedChange = {}
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}