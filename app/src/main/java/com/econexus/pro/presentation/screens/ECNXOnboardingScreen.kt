package com.econexus.pro.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Analytics
import androidx.compose.material.icons.rounded.Nature
import androidx.compose.material.icons.rounded.Recycling
import androidx.compose.material.icons.rounded.Verified
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch

@Composable
fun ECNXOnboardingScreen(
    onFinishOnboarding: () -> Unit,
    viewModel: com.econexus.pro.presentation.viewmodels.ECNXOnboardingViewModel = hiltViewModel()
) {
    val pagerState = rememberPagerState(pageCount = { onboardingPages.size })
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            OnboardingBottomBar(
                isLastPage = pagerState.currentPage == onboardingPages.size - 1,
                onNextClicked = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                },
                onGetStartedClicked = {
                    viewModel.completeOnboarding()
                    onFinishOnboarding()
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { page ->
                OnboardingPage(page = onboardingPages[page])
            }

            Row(
                modifier = Modifier
                    .padding(bottom = 28.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(onboardingPages.size) { iteration ->
                    val isActive = pagerState.currentPage == iteration
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                            .clip(RoundedCornerShape(50))
                            .background(
                                if (isActive) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.outline.copy(alpha = 0.35f)
                            )
                            .width(if (isActive) 28.dp else 10.dp)
                            .height(10.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun OnboardingPage(page: OnboardingPageData) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(160.dp)
                .clip(RoundedCornerShape(40.dp))
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = page.icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(80.dp)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = page.title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 26.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = page.description,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = 26.sp
        )
    }
}

@Composable
private fun OnboardingBottomBar(
    isLastPage: Boolean,
    onNextClicked: () -> Unit,
    onGetStartedClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp)
    ) {
        if (isLastPage) {
            FilledTonalButton(
                onClick = onGetStartedClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(50)
            ) {
                Icon(
                    Icons.Rounded.Verified,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "START EXPLORING",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.5.sp
                )
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = onGetStartedClicked,
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        text = "Skip",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Button(
                    onClick = onNextClicked,
                    contentPadding = PaddingValues(horizontal = 36.dp, vertical = 14.dp),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        text = "Continue",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

data class OnboardingPageData(
    val title: String,
    val description: String,
    val icon: ImageVector
)

private val onboardingPages = listOf(
    OnboardingPageData(
        title = "Environmental Intelligence",
        description = "Access industry-leading environmental consulting services — from EIA to ESG strategy — all in one unified platform.",
        icon = Icons.Rounded.Nature
    ),
    OnboardingPageData(
        title = "Data-Driven Outcomes",
        description = "Book expert consultations, track project milestones, and receive evidence-based recommendations tailored to your sector.",
        icon = Icons.Rounded.Analytics
    ),
    OnboardingPageData(
        title = "Circular by Design",
        description = "Explore our portfolio of zero-waste and circular economy projects driving real savings and regulatory compliance.",
        icon = Icons.Rounded.Recycling
    ),
    OnboardingPageData(
        title = "Compliance Assured",
        description = "Stay ahead of evolving environmental regulations with proactive advisory and audit services from our certified specialists.",
        icon = Icons.Rounded.Verified
    )
)