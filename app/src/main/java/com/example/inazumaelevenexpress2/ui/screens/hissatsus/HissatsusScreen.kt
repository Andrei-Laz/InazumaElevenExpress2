package com.example.inazumaelevenexpress2.ui.screens.hissatsus

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.inazumaelevenexpress2.R
import com.example.inazumaelevenexpress2.model.Hissatsu
import com.example.inazumaelevenexpress2.model.enums.Element
import com.example.inazumaelevenexpress2.ui.theme.DarkOrange

@Composable
fun HissatsusScreen(
    uiState: HissatsusUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    when (uiState) {
        is HissatsusUiState.Loading ->
            LoadingScreen(modifier)

        is HissatsusUiState.Success ->
            HissatsusListScreen(
                hissatsus = uiState.hissatsus,
                modifier = modifier.padding(
                    start = dimensionResource(R.dimen.padding_medium),
                    top = dimensionResource(R.dimen.padding_medium),
                    end = dimensionResource(R.dimen.padding_medium)
                ),
                contentPadding = contentPadding
            )

        is HissatsusUiState.Error ->
            ErrorScreen(retryAction, modifier)
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.loading_failed))
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun HissatsuCard(
    hissatsu: Hissatsu,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = DarkOrange
        )
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = hissatsu.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                if (hissatsu.type != null) {
                    Text(
                        text = hissatsu.type.name.lowercase().replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            if (hissatsu.description.isNotBlank()) {
                Text(
                    text = hissatsu.description,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify,
                    color = Color.White
                )
                Spacer(Modifier.height(12.dp))
            }

            PowerBar(power = hissatsu.power)

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Power: ${hissatsu.power}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
private fun PowerBar(power: Int) {
    val maxPower = 100
    val progress = power.coerceIn(0, maxPower).toFloat() / maxPower

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(4.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(fraction = progress)
                .fillMaxHeight()
                .background(
                    color = when {
                        power >= 90 -> Color(0xFFFF6B6B) // Red for high power
                        power >= 70 -> Color(0xFFFFA500) // Orange for medium-high
                        power >= 50 -> Color(0xFFFFD700) // Yellow for medium
                        else -> Color(0xFF90EE90)        // Green for low
                    },
                    shape = RoundedCornerShape(4.dp, if (progress == 1f) 4.dp else 0.dp, if (progress == 1f) 4.dp else 0.dp, 4.dp)
                )
        )
    }
}

@Composable
private fun HissatsusListScreen(
    hissatsus: List<Hissatsu>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    if (hissatsus.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No hissatsus available",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        return
    }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(300.dp),
        modifier = modifier,
        contentPadding = contentPadding,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = hissatsus,
            key = { it.hissatsuId ?: it.name }
        ) { hissatsu ->
            HissatsuCard(
                hissatsu = hissatsu,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
