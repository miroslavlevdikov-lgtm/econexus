package com.econexus.pro.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.econexus.pro.presentation.viewmodels.ECNXPortfolioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ECNXCaseDetailsScreen(
    caseId: Int,
    onBackClick: () -> Unit,
    viewModel: ECNXPortfolioViewModel = hiltViewModel()
) {
    LaunchedEffect(caseId) {
        viewModel.loadCaseDetails(caseId)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val case = uiState.selectedCase

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Case Study") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (case != null) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .padding(16.dp)
                            .clip(RoundedCornerShape(20.dp))
                    ) {
                        AsyncImage(
                            model = case.imageUrl,
                            contentDescription = case.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.50f)),
                            contentAlignment = Alignment.BottomStart
                        ) {
                            Column(modifier = Modifier.padding(20.dp)) {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.85f)
                                ) {
                                    Text(
                                        text = case.sector,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = Color.White,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = case.title,
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = case.summary,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.White.copy(alpha = 0.9f),
                                    maxLines = 2
                                )
                            }
                        }
                    }
                }

                item {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(case.tags) { tag ->
                            SuggestionChip(
                                onClick = {},
                                label = { Text(tag) }
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    CaseSection(
                        icon = Icons.Filled.Warning,
                        title = "The Challenge",
                        content = case.challenge,
                        containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f),
                        iconColor = MaterialTheme.colorScheme.error
                    )
                }

                item {
                    CaseSection(
                        icon = Icons.Filled.Lightbulb,
                        title = "Our Solution",
                        content = case.solution,
                        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                        iconColor = MaterialTheme.colorScheme.primary
                    )
                }

                item {
                    CaseSection(
                        icon = Icons.Filled.EmojiEvents,
                        title = "Achieved Results",
                        content = case.result,
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.3f),
                        iconColor = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
        }
    }
}

@Composable
private fun CaseSection(
    icon: ImageVector,
    title: String,
    content: String,
    containerColor: Color,
    iconColor: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
            )
        }
    }
}