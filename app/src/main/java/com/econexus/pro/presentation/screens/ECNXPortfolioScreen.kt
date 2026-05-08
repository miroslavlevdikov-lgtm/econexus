package com.econexus.pro.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.econexus.pro.domain.model.ECNXCase
import com.econexus.pro.presentation.viewmodels.ECNXPortfolioViewModel

@Composable
fun ECNXPortfolioScreen(
    onCaseClick: (Int) -> Unit,
    viewModel: ECNXPortfolioViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Impact Portfolio",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 22.sp
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Text(
                    text = "Proven results across industries — each project a blueprint for measurable environmental progress.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(12.dp))
        }

        items(uiState.cases) { case ->
            PortfolioCaseCard(case = case, onClick = { onCaseClick(case.id) })
        }
    }
}

@Composable
private fun PortfolioCaseCard(case: ECNXCase, onClick: () -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
                contentAlignment = Alignment.BottomStart
            ) {
                AsyncImage(
                    model = case.imageUrl,
                    contentDescription = case.sector,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(androidx.compose.ui.graphics.Color.Black.copy(alpha = 0.38f)),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Text(
                        text = case.sector.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = androidx.compose.ui.graphics.Color.White,
                        letterSpacing = 1.5.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            Column(modifier = Modifier.padding(horizontal = 18.dp, vertical = 16.dp)) {
                Text(
                    text = case.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = case.summary,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    case.tags.take(3).forEach { tag ->
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Text(
                                text = tag,
                                style = MaterialTheme.typography.labelSmall,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }
}