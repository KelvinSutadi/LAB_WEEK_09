package com.example.lab_week_09

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.lab_week_09.ui.theme.*
import com.example.lab_week_09.ui.theme.LAB_WEEK_09Theme

//Declare a data class called Student
data class Student(
    var name: String
)

//Previously we extend AppCompatActivity,
//now we extend ComponentActivity
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Here, we use setContent instead of setContentView
        setContent {
            //Here, we wrap our content with the theme
            //You can check out the LAB_WEEK_09Theme inside Theme.kt
            LAB_WEEK_09Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    //We use Modifier.fillMaxSize() to make the surface fill the whole screen
                    modifier = Modifier.fillMaxSize(),
                    //We use MaterialTheme.colorScheme.background to get the background color
                    //and set it as the color of the surface
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Remember navController for navigation
                    val navController = rememberNavController()
                    //Here we call App() instead of Home()
                    App(navController = navController)
                }
            }
        }
    }
}

//Here, we create a composable function called App
//This will be the root composable of the app
@Composable
fun App(navController: NavHostController) {
    //Here, we use NavHost to create a navigation graph
    //We pass the navController as a parameter
    //We also set the startDestination to "home"
    //This means that the app will start with the Home composable
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        //Route "home"
        composable("home") {
            Home { navController.navigate("resultContent/?listData=$it") }
        }

        //Route "resultContent"
        composable(
            "resultContent/?listData={listData}",
            arguments = listOf(navArgument("listData") {
                type = NavType.StringType
            })
        ) {
            ResultContent(it.arguments?.getString("listData").orEmpty())
        }
    }
}

//Home now accepts a navigation lambda
@Composable
fun Home(
    navigateFromHomeToResult: (String) -> Unit
) {
    val listData = remember {
        mutableStateListOf(
            Student("Tanu"),
            Student("Tina"),
            Student("Tono")
        )
    }
    var inputField by remember { mutableStateOf(Student("")) }

    HomeContent(
        listData,
        inputField,
        { input -> inputField = inputField.copy(input) },
        {
            if (inputField.name.isNotBlank()) {
                listData.add(inputField)
                inputField = Student("")
            }
        },
        { navigateFromHomeToResult(listData.toList().toString()) }
    )
}

// Updated HomeContent using custom UI elements from Elements.kt
//Updated HomeContent with navigation button
@Composable
fun HomeContent(
    listData: SnapshotStateList<Student>,
    inputField: Student,
    onInputValueChange: (String) -> Unit,
    onButtonClick: () -> Unit,
    navigateFromHomeToResult: () -> Unit
) {
    LazyColumn {
        item {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Here, we call the OnBackgroundTitleText UI Element
                OnBackgroundTitleText(
                    text = stringResource(id = R.string.enter_item)
                )

                //Here, we use TextField to display a text input field
                OnBackgroundTextField(
                    value = inputField.name,
                    label = stringResource(id = R.string.enter_item),
                    keyboardType = KeyboardType.Text,
                    onValueChange = { onInputValueChange(it) }
                )

                //Two buttons side by side
                Row {
                    PrimaryTextButton(
                        text = stringResource(id = R.string.button_click)
                    ) {
                        onButtonClick()
                    }

                    PrimaryTextButton(
                        text = stringResource(id = R.string.button_navigate)
                    ) {
                        navigateFromHomeToResult()
                    }
                }
            }
        }

        items(listData) { item ->
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Here, we call the OnBackgroundItemText UI Element
                OnBackgroundItemText(text = item.name)
            }
        }
    }
}

//ResultContent page for displaying passed data
@Composable
fun ResultContent(listData: String) {
    Column(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OnBackgroundItemText(text = listData)
    }
}

//Here, we create a preview function of the Home composable
//This function is specifically used to show a preview of the Home composable
//This is only for development purpose
@Preview(showBackground = true)
@Composable
fun PreviewHome() {
    //Preview just shows home without navigation
    Home { }
}
