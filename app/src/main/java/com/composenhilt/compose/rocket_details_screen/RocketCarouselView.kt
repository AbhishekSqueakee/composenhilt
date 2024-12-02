package com.composenhilt.compose.rocket_details_screen

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Scale
import com.composenhilt.R
import com.composenhilt.data.room_database.rockets.RocketsModel
import com.composenhilt.utils.extensions.getProgressDrawable
import kotlinx.coroutines.Dispatchers

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RocketCarouselView(rocketImages: List<String>?, context: Context) {
    val carouselState = rememberCarouselState { rocketImages?.size ?: 0 }

    HorizontalMultiBrowseCarousel(
        state = carouselState,
        preferredItemWidth = 300.dp,
        itemSpacing = 16.dp) { index->
            Box(modifier = Modifier.size(300.dp )) {
                AsyncImage(
                    model = ImageRequest.Builder(context = context).apply {
                        data(rocketImages!![index])
                        scale(Scale.FIT)
                        placeholder(getProgressDrawable(context))
                        error(R.drawable.ic_placeholder)
                        fallback(R.drawable.ic_placeholder)
                        memoryCachePolicy(CachePolicy.ENABLED)
                        dispatcher(Dispatchers.Default)
                    }.build(),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = 300.dp)
                )

        }
    }
}