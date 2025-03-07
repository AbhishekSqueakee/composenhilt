package com.composenhilt.compose.rockets_screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Scale
import com.composenhilt.R
import com.composenhilt.data.room_database.rockets.RocketsModel
import com.composenhilt.compose.generic_compose_views.CustomToolbar
import com.composenhilt.compose.generic_compose_views.ShowDialog
import com.composenhilt.compose.generic_compose_views.StartDefaultLoader
import com.composenhilt.utils.extensions.getProgressDrawable
import com.composenhilt.utils.screen_routes.Screens.ROCKET_DETAILS_SCREEN
import kotlinx.coroutines.Dispatchers

@Composable
internal fun RocketsScreen(navController: NavController) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            CustomToolbar(R.string.list_of_rockets)
        },
        content = { paddingValue ->
            ListOfRockets(navController = navController, paddingValues = paddingValue)
        })
}

@Composable
private fun ListOfRockets(
    navController: NavController,
    paddingValues: PaddingValues,
    rocketsViewModel: RocketsViewModel = hiltViewModel()
) {
    val isLoading = rocketsViewModel.loading.observeAsState(initial = false).value
    if (isLoading) StartDefaultLoader()
    val error = rocketsViewModel.error.observeAsState(initial = "").value
    if (error != null && error.isNotEmpty()) ShowDialog(
        title = stringResource(id = R.string.error),
        message = error
    )
    val context = LocalContext.current
    val rocketModelList =
        rocketsViewModel.rocketsModelStateFlow.collectAsState(initial = mutableListOf()).value
    LazyColumn {
        items(rocketModelList, key = { rocketModel -> rocketModel.id }) {
            RocketItemView(rocketModel = it) { selectedrocketDataValue ->
                Toast.makeText(
                    context,
                    selectedrocketDataValue.name.toString(),
                    Toast.LENGTH_SHORT
                ).show()
                navController.navigate(ROCKET_DETAILS_SCREEN + "/${selectedrocketDataValue.id}")
            }
        }
    }
}

private fun getRocketImage(rocketModel: RocketsModel): String {
    var image = "";
    rocketModel.flickr_images?.let {
        if (it.isNotEmpty()) {
            image = it[0]
        }
    }
    return image;
}

@Composable
private fun RocketItemView(
    rocketModel: RocketsModel,
    listener: (RocketsModel) -> Unit
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .height(125.dp)
            .fillMaxWidth()
            .padding(5.dp)
            .clickable {
                listener(rocketModel)
            },
        elevation = 3.dp,
        shape = RoundedCornerShape(9.dp),
        backgroundColor = Color.DarkGray
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = context).apply {
                    data(getRocketImage(rocketModel))
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
                    .fillMaxHeight()
                    .width(width = 150.dp)
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .wrapContentWidth()
                    .fillMaxHeight()
                    .padding(start = 15.dp),
            ) {
                Column {
                    Text(
                        rocketModel.name.toString(),
                        style = TextStyle(fontSize = 15.sp, textAlign = TextAlign.Center),
                        color = Color.White,
                    )
                    Text(
                        rocketModel.type.toString(),
                        style = TextStyle(fontSize = 15.sp, textAlign = TextAlign.Center),
                        color = Color.White,
                    )
                }
            }
        }
    }
}