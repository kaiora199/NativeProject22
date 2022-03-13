package com.example.myapplication

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

const val PATH_HOME = "home"
const val PATH_TYPE = "typing"
const val PATH_NOTES = "notes"

@Composable
fun MainFrontView() {
    val userViewModelImp = viewModel<UserView>()
    if (userViewModelImp.username.value.isEmpty()){
        LoginScreen(userViewModelImp)
    }else{
        LoggedInScreen()
    }
}

@Composable
fun LoggedInScreen() {
    val navController = rememberNavController()

    Scaffold(
        topBar = { TopBarContent()},
        bottomBar = { BotBarContent(navController)},
        content = { KKontent(navController)}
    )
}

@Composable
fun Front() {
    Column(modifier= Modifier
        .fillMaxSize()
        .background(Color(0xFF31BCFC))) {
        
    }
}

@Composable
fun Note(noteVVM: DiaryView) {
    var diaryNote by remember{ mutableStateOf("")}
    Column(modifier= Modifier
        .fillMaxSize()
        .background(Color(0xFF87D6FA))
    , horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly) {

        OutlinedTextField(
            value = diaryNote,
            onValueChange = {diaryNote=it},
            label = { Text(text = "Your note")},

        )
        OutlinedButton(onClick = {noteVVM.addEntry(DiaryData(diaryNote))}) {
            Text(text = "Add your entry")

        }
        noteVVM.entries.value.forEach{
            Divider(thickness = 1.dp)
            Text(text = it.noteData)
        }
    }
}

@Composable
fun KKontent(navController: NavHostController) {
    val noteVVM = viewModel<DiaryView>()
    NavHost(navController = navController, startDestination = PATH_HOME ){
        composable(route = PATH_HOME){ Front()}
        composable(route = PATH_TYPE){ Note(noteVVM)}

    }
}

@Composable
fun TopBarContent() {
    val userViewModelImp = viewModel<UserView>()
        Row(modifier= Modifier
            .fillMaxWidth()
            .background(Color(0xFF6DCAF5))
            .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically){
            Text(text = "Logged in as " + userViewModelImp.username.value)
            OutlinedButton(onClick = {userViewModelImp.userLogout()}) {
                Text(text = "Log out")
                
            }
        }
}

@Composable
fun BotBarContent(navController: NavHostController) {
    Row(modifier= Modifier
        .fillMaxWidth()
        .background(Color(0xFF6DCAF5))
        .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically){
        Icon(
            painter = painterResource(id = R.drawable.ic_menu) , 
            contentDescription = "menu",
            modifier = Modifier.clickable { navController.navigate(PATH_HOME) }
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_notes) ,
            contentDescription = "add",
            modifier = Modifier.clickable { navController.navigate(PATH_NOTES) }
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_add) ,
            contentDescription = "notes",
            modifier = Modifier.clickable { navController.navigate(PATH_TYPE) }
        )
    }
}

@Composable
fun LoginScreen(userViewModelImp: UserView) {
    var email by remember{
        mutableStateOf("")
    }
    var password by remember{
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = email, 
            onValueChange = {email=it},
            label = { Text(text = "Email")}
        )
        OutlinedTextField(
            value = password,
            onValueChange = {password=it},
            label = { Text(text = "Password")},
            visualTransformation = PasswordVisualTransformation()
        )
        OutlinedButton(onClick = { userViewModelImp.userLogin(email,password)}) {
            Text(text = "Login")

        }
    }
}
        