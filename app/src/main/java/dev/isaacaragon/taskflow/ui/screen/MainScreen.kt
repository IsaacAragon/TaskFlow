package dev.isaacaragon.taskflow.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import dev.isaacaragon.taskflow.viewmodel.TaskViewModel


import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MainScreen() {

    val viewModel: TaskViewModel = viewModel()

    var mostrarDialogo by remember {
        mutableStateOf(false)
    }

        ListScreen(

            viewModel = viewModel,

            abrirPantallaTarea = {
                mostrarDialogo = true
            }
        )

        if (mostrarDialogo) {

            TaskScreen(

                viewModel = viewModel,

                cerrarDialogo = {
                    mostrarDialogo = false
                }
            )
        }
    }