package com.matheus.movieapp.presentation.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer

@Composable
fun ShimmerEffect() {
    Column(modifier = Modifier.padding(16.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .placeholder(
                    visible = true,
                    color = Color.Gray,
                    shape = MaterialTheme.shapes.medium,
                    highlight = PlaceholderHighlight.shimmer()
                )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(20.dp)
                .placeholder(
                    visible = true,
                    color = Color.Gray,
                    shape = MaterialTheme.shapes.small,
                    highlight = PlaceholderHighlight.shimmer()
                )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(14.dp)
                .placeholder(
                    visible = true,
                    color = Color.Gray,
                    shape = MaterialTheme.shapes.small,
                    highlight = PlaceholderHighlight.shimmer()
                )
        )
    }
}

@Preview
@Composable
fun PreviewShimmerEffect() {
    ShimmerEffect()
}