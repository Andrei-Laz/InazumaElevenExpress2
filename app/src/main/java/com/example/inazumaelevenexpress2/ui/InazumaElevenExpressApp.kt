package com.example.inazumaelevenexpress2.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.inazumaelevenexpress2.R
import com.example.inazumaelevenexpress2.ui.screens.CharactersScreen
import com.example.inazumaelevenexpress2.ui.screens.InazumaCharactersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InazumaElevenExpressApp() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.app_name),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            )
        }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val inazumaCharactersViewModel: InazumaCharactersViewModel =
                viewModel(factory = InazumaCharactersViewModel.Factory)
            CharactersScreen(
                uiState = inazumaCharactersViewModel.inazumaCharactersUiState,
                retryAction = inazumaCharactersViewModel::getCharacters,
                modifier = Modifier.fillMaxSize(),
                contentPadding = it
            )
        }
    }
}
