package com.example.compose.clean_art.retrofit

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.toRoute
import com.example.myapplication.utils.navigation.ScreenHomeTwo
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TutorialsScreen(modifier: Modifier = Modifier,it : NavBackStackEntry) {

    val args = it.toRoute<ScreenHomeTwo>()

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var bottomSheetShow by remember {
        mutableStateOf(false)
    }

    Scaffold { paddingValues ->
        if (bottomSheetShow) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = { bottomSheetShow = false }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                ) {
                    Text("Hi I'm ${args.title} my id is ${args.id}")
                    Text("----------")
                    Text("You Can close Bottom From Here")
                    Spacer(modifier = Modifier.height(100.dp))
                    Button(onClick = {
                        scope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            bottomSheetShow = false
                        }
                    }) {
                        Text("Close")
                    }
                }
            }
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Button(onClick = {
                bottomSheetShow = true
            }) {
                Text("Show Bottom Sheet")
            }
        }
    }
}
