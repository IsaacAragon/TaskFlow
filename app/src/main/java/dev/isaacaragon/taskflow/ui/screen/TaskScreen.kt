package dev.isaacaragon.taskflow.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.isaacaragon.taskflow.viewmodel.TaskViewModel
import dev.isaacaragon.taskflow.model.Task

@Composable
fun TaskScreen(

    viewModel: TaskViewModel = viewModel(),

    cerrarDialogo: () -> Unit

) {

    AlertDialog(

        onDismissRequest = {
            cerrarDialogo()
        },

        title = {
            Text("Nueva tarea")
        },

        text = {

            Column {

                // Campo ID
                OutlinedTextField(

                    value = viewModel.id,

                    onValueChange = {
                        viewModel.onIdChange(it)
                    },

                    label = {
                        Text("ID")
                    },

                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                // Campo título
                OutlinedTextField(

                    value = viewModel.title,

                    onValueChange = {
                        viewModel.onTitleChange(it)
                    },

                    label = {
                        Text("Título")
                    },

                    modifier = Modifier.fillMaxWidth()
                )
            }
        },

        confirmButton = {

            Button(

                onClick = {

                    if (
                        viewModel.id.isNotEmpty() &&
                        viewModel.title.isNotEmpty()
                    ) {

                        viewModel.addTask(

                            Task(
                                id = viewModel.id.toInt(),
                                title = viewModel.title,
                                completed = false
                            )
                        )

                        cerrarDialogo()
                    }
                }
            ) {

                Text("Guardar")
            }
        },

        dismissButton = {

            TextButton(

                onClick = {
                    cerrarDialogo()
                }
            ) {

                Text("Cancelar")
            }
        }
    )
}