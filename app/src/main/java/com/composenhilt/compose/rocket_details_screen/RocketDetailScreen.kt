package com.composenhilt.compose.rocket_details_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.composenhilt.data.room_database.rockets.RocketsModel
import com.composenhilt.compose.generic_compose_views.CustomToolbar
import com.composenhilt.compose.generic_compose_views.ShowDialog
import com.composenhilt.compose.generic_compose_views.StartDefaultLoader

const val ROCKET_ID_KEY = "rocket_id_key"

@Composable
internal fun RocketDetailsScreen(
    rocketId: String,
    rocketDetailsViewModel: RocketDetailsViewModel = hiltViewModel()
) {
    rocketDetailsViewModel.queryRocketById(rocketId)
    val rocketData = rocketDetailsViewModel.rocketDetails.collectAsState().value
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            CustomToolbar(rocketData.name ?: "")
        },
        content = { paddingValue ->
            val isLoading = rocketDetailsViewModel.loading.observeAsState(initial = false).value
            if (isLoading) StartDefaultLoader()
            val error = rocketDetailsViewModel.error.observeAsState(initial = null).value
            if (error != null) ShowDialog(title = error, message = "")
            RocketDetailsView(rocketData, paddingValue)
        })
}

@Composable
private fun RocketDetailsView(
    rocketData: RocketsModel,
    paddingValues: PaddingValues
) {
    val context = LocalContext.current
    if (rocketData.flickr_images?.isNotEmpty() == true) {
    Box(contentAlignment = Alignment.TopCenter) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            RocketCarouselView(rocketData.flickr_images, context)
            Text(
                text = rocketData.name ?: "",
                style = TextStyle(
                    fontSize = 21.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                ),
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = rocketData.type ?: "",
                style = TextStyle(
                    fontSize = 21.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                ),
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = rocketData.country ?: "",
                style = TextStyle(
                    fontSize = 21.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                ),
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = rocketData.description ?: "",
                style = TextStyle(
                    fontSize = 21.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                ),
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
            LinkAnnotationText(rocketData.name, rocketData.wikipedia)
            }
        }
    }
}

@Composable
fun LinkAnnotationText(name: String?, link: String?) {
    val annotatedLinkString: AnnotatedString = remember {
        buildAnnotatedString {

            val styleCenter = SpanStyle(
                color = Color(0xff64B5F6),
                fontSize = 20.sp,
                textDecoration = TextDecoration.Underline
            )

            withLink(LinkAnnotation.Url(url = link?:"https://www.google.com")) {
                withStyle(
                    style = styleCenter
                ) {
                    append(name)
                }
            }
        }
    }
    Text(annotatedLinkString)

}

