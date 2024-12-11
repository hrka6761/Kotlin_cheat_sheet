package ir.hrka.kotlin.ui.screens.cheatsheets

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ir.hrka.kotlin.MainActivity
import ir.hrka.kotlin.R
import ir.hrka.kotlin.core.ExecutionState.Start
import ir.hrka.kotlin.core.ExecutionState.Loading
import ir.hrka.kotlin.core.ExecutionState.Stop
import ir.hrka.kotlin.core.Constants.UPDATED_ID_KEY
import ir.hrka.kotlin.core.utilities.Resource
import ir.hrka.kotlin.core.utilities.Screen.Point
import ir.hrka.kotlin.core.utilities.extractFileName
import ir.hrka.kotlin.core.utilities.splitByCapitalLetters
import ir.hrka.kotlin.domain.entities.db.KotlinTopicModel

@Composable
fun CheatSheetScreen(
    activity: MainActivity,
    navHostController: NavHostController,
    githubVersionName: String?,
    githubVersionSuffix: String?
) {

    val viewModel: CheatSheetsViewModel = hiltViewModel()
    val snackBarHostState = remember { SnackbarHostState() }
    val cheatSheets by viewModel.cheatSheets.collectAsState()
    val executionState by viewModel.executionState.collectAsState()
    val failedState by viewModel.failedState.collectAsState()
    val hasUpdateForCheatSheetsList by viewModel.hasUpdateForCheatSheetsList.collectAsState()
    val hasUpdateForCheatSheetsContent by viewModel.hasUpdateForCheatSheetsContent.collectAsState()
    val saveCheatsheetsListResult by viewModel.saveCheatsheetsListResult.collectAsState()
    val updateCheatsheetsOnDBResult by viewModel.updateCheatsheetsOnDBResult.collectAsState()
    val updatedId = navHostController.currentBackStackEntry
        ?.savedStateHandle
        ?.get<Int>(UPDATED_ID_KEY)

    updatedId?.let { id -> viewModel.updateCheatSheetsList(id - 1) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { CheatSheetsAppBar(navHostController) },
        snackbarHost = {
            SnackbarHost(
                modifier = Modifier
                    .fillMaxWidth(),
                hostState = snackBarHostState
            )
        }
    ) { innerPaddings ->

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(if (failedState) 1f else 0f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier
                        .width(60.dp)
                        .height(60.dp),
                    painter = painterResource(R.drawable.error),
                    contentDescription = null
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    text = "Failed to fetch the data",
                    textAlign = TextAlign.Center
                )
            }

            LazyVerticalStaggeredGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPaddings),
                columns = StaggeredGridCells.Fixed(1),
                contentPadding = PaddingValues(8.dp)
            ) {
                cheatSheets.data?.let {
                    items(it.size) { index ->
                        CheatSheetItem(it[index], navHostController)
                    }
                }
            }

            CircularProgressIndicator(
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .alpha(if (executionState == Loading) 1f else 0f),
                strokeWidth = 2.dp
            )
        }
    }

    LaunchedEffect(Unit) {
        if (executionState == Start) {
            viewModel.setExecutionState(Loading)
            viewModel.checkNewUpdateForCheatsheetsList(githubVersionName)
        }
    }

    LaunchedEffect(hasUpdateForCheatSheetsList) {
        if (executionState != Stop) {
            if (hasUpdateForCheatSheetsList == true)
                viewModel.getCheatSheetsFromGithub()
            else if (hasUpdateForCheatSheetsList == false)
                viewModel.checkNewUpdateForCheatsheetsContent(githubVersionName)
        }
    }

    LaunchedEffect(hasUpdateForCheatSheetsContent) {
        if (executionState != Stop) {
            if (hasUpdateForCheatSheetsContent == true)
                viewModel.updateCheatsheetsInDatabase(githubVersionSuffix)
            else if (hasUpdateForCheatSheetsContent == false)
                viewModel.getCheatSheetFromDatabase()
        }
    }

    LaunchedEffect(updateCheatsheetsOnDBResult) {
        if (executionState != Stop) {
            when (updateCheatsheetsOnDBResult) {
                is Resource.Initial -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {
                    viewModel.saveVersionName(githubVersionName!!)
                    viewModel.getCheatSheetFromDatabase()
                }

                is Resource.Error -> {
                    viewModel.setExecutionState(Stop)
                    snackBarHostState.showSnackbar(
                        message = updateCheatsheetsOnDBResult.error?.errorMsg ?: "",
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    LaunchedEffect(cheatSheets) {
        if (executionState != Stop) {
            when (cheatSheets) {
                is Resource.Initial -> {}
                is Resource.Loading -> {
                    if (hasUpdateForCheatSheetsList == true) {
                        snackBarHostState.showSnackbar(
                            message = activity.getString(R.string.fetching_new_cheatsheets_list_msg),
                            duration = SnackbarDuration.Long
                        )
                    }
                }

                is Resource.Success -> {
                    if (cheatSheets.data?.isEmpty() != false) {
                        viewModel.setExecutionState(Stop)
                        viewModel.setFailedState(true)
                        snackBarHostState.showSnackbar(
                            message = activity.getString(R.string.no_cheatsheets_msg),
                            duration = SnackbarDuration.Long
                        )
                        return@LaunchedEffect
                    }

                    if (hasUpdateForCheatSheetsList == true)
                        viewModel.saveCheatsheetsOnDB(githubVersionName!!)
                    else
                        viewModel.setExecutionState(Stop)
                }

                is Resource.Error -> {
                    viewModel.setExecutionState(Stop)
                    viewModel.setFailedState(true)
                    snackBarHostState.showSnackbar(
                        message = cheatSheets.error?.errorMsg.toString(),
                        duration = SnackbarDuration.Long
                    )
                }
            }
        }
    }

    LaunchedEffect(saveCheatsheetsListResult) {
        if (executionState != Stop) {
            when (saveCheatsheetsListResult) {
                is Resource.Initial -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {
                    viewModel.saveVersionName(githubVersionName!!)
                    viewModel.setExecutionState(Stop)
                }

                is Resource.Error -> {
                    viewModel.setExecutionState(Stop)
                    snackBarHostState.showSnackbar(
                        message = saveCheatsheetsListResult.error?.errorMsg.toString(),
                        duration = SnackbarDuration.Long
                    )
                }
            }
        }
    }

    BackHandler {
        navHostController.popBackStack()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheatSheetsAppBar(navHostController: NavHostController) {
    CenterAlignedTopAppBar(
        title = { Text(stringResource(R.string.cheatsheet_app_bar_title)) },
        navigationIcon = {
            IconButton(
                onClick = { navHostController.popBackStack() }
            ) {
                Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = null)
            }
        }
    )
}

@Composable
fun CheatSheetItem(
    cheatSheet: KotlinTopicModel,
    navHostController: NavHostController,
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .clickable {
                    navHostController.navigate(
                        Point.appendArg(
                            cheatSheet.name,
                            cheatSheet.hasContentUpdated.toString(),
                            cheatSheet.id.toString()
                        )
                    )
                }
                .padding(8.dp)
        ) {
            val (id, title, label) = createRefs()

            Text(
                modifier = Modifier
                    .width(25.dp)
                    .height(25.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(50)
                    )
                    .constrainAs(id) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                text = cheatSheet.id.toString(),
            )

            Text(
                modifier = Modifier.constrainAs(title) {
                    start.linkTo(id.end, margin = 8.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
                text = cheatSheet.name
                    .extractFileName()
                    .splitByCapitalLetters(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            if (cheatSheet.hasContentUpdated)
                Text(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(50)
                        )
                        .padding(horizontal = 4.dp)
                        .constrainAs(label) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        },
                    text = stringResource(R.string.updated_label_txt),
                    textAlign = TextAlign.Center,
                    fontStyle = FontStyle.Italic,
                    fontSize = 8.sp,
                    color = MaterialTheme.colorScheme.surface,
                    fontWeight = FontWeight.Bold
                )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CheatSheetScreenPreview() {
    CheatSheetScreen(MainActivity(), rememberNavController(), "", "")
}