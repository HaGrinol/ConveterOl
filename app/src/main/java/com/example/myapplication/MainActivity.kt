package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SwapVerticalCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color

class ConverterViewModel(application: Application) : AndroidViewModel(application){

}
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainScreen()
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedIndex by remember { mutableIntStateOf(0) }
    val items =
        listOf(
            "Валюта" to Icons.Default.MonetizationOn,

        )
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(
                Modifier.background(MaterialTheme.colorScheme.background.copy(0.9f))
                    .fillMaxHeight().padding(top = 30.dp, start = 5.dp, end = 30.dp)
                    .selectableGroup(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                items.forEachIndexed { index, item ->
                    val (text, icon) = item

                    NavigationDrawerItem(
                        label = { Text(text) },
                        modifier = Modifier.padding(10.dp),
                        selected = selectedIndex == index,
                        onClick = { selectedIndex = index },
                        icon = { Icon(imageVector = icon, contentDescription = null) }
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    modifier = Modifier,
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Конвертер валют",
                                overflow = TextOverflow.Ellipsis,
                                fontSize = 18.sp
                            )
                            Spacer(modifier = Modifier.padding(10.dp))
                            HorizontalDivider(modifier = Modifier.height(16.dp).width(1.dp))
                            Text(
                                text = "$92.50",
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                            )
                        }
                    },

                    navigationIcon = {
                        IconButton(onClick = { scope.launch {drawerState.open()}}) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Меню"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Filled.Refresh,
                                contentDescription = "Обновить"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        titleContentColor = MaterialTheme.colorScheme.primary
                    )
                )
            },
            content = { innerPadding ->
                Greeting(
                    name = "User",
                    modifier = Modifier.padding(innerPadding)
                )
            }
        )
    }
}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var textField1 by remember { mutableStateOf("") }
    var expanded1 by remember {  mutableStateOf(false) }
    val valutes = listOf("USD", "EUR", "RUB")
    var selectedCurrency1 by remember { mutableStateOf("USD") }
    var textField2 by remember { mutableStateOf("") }
    var expanded2 by remember {  mutableStateOf(false) }
    var selectedCurrency2 by remember { mutableStateOf("EUR") }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ){
            TextField(
                modifier = Modifier.clickable{expanded1 != expanded1},
                value = textField1,
                onValueChange = { newText ->
                    if (newText.isEmpty()) {
                        textField1 = ""
                    } else if (newText.count { it == '.' } <= 1 && newText.all { it.isDigit() || it == '.' }) {
                        textField1 = newText
                    }
                },
                label = { Text("Введите значение") },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                shape = RoundedCornerShape(15.dp)
            )
            Spacer( modifier = Modifier.width(12.dp))
            Box() {
                Button(
                    onClick = { expanded1 = !expanded1 },
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    colors = ButtonColors(
                        MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary,
                        disabledContainerColor = MaterialTheme.colorScheme.secondary,
                        disabledContentColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text(
                        text = selectedCurrency1,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }

                if (expanded1) {
                    DropdownMenu(
                        expanded = expanded1,
                        onDismissRequest = { expanded1 = false },

                    ) {
                        valutes.forEach { currency ->
                            DropdownMenuItem(
                                text = { Text(currency) },
                                onClick = {
                                    selectedCurrency1 = currency
                                    expanded1 = false
                                }
                            )
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.size(15.dp))
        IconButton(
            modifier = Modifier.align(alignment = Alignment.End) .padding(end = 20.dp),
            onClick = {
                val selectedCurrency0 = selectedCurrency1;
                selectedCurrency1 = selectedCurrency2;
                selectedCurrency2 = selectedCurrency0
            }
        ){
            Icon(
                modifier = Modifier.size(120.dp),
                imageVector = Icons.Filled.SwapVerticalCircle,
                contentDescription = "Поменять местами",
                tint = MaterialTheme.colorScheme.secondary
            )
        }
        Spacer(modifier = Modifier.size(15.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ){
            TextField(
                modifier = Modifier.clickable{expanded2 != expanded2},
                value = textField2,
                onValueChange = { newText ->
                    if (newText.isEmpty()) {
                        textField2 = ""
                    } else if (newText.count { it == '.' } <= 1 && newText.all { it.isDigit() || it == '.' }) {
                        textField2 = newText
                    }
                },
                label = { Text("Введите значение") },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                shape = RoundedCornerShape(15.dp)
            )
            Spacer( modifier = Modifier.width(12.dp))
            Box() {
                Button(
                    onClick = { expanded2 = !expanded2 },
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    colors = ButtonColors(
                        MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary,
                        disabledContainerColor = MaterialTheme.colorScheme.secondary,
                        disabledContentColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text(
                        text = selectedCurrency2,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }

                if (expanded2) {
                    DropdownMenu(
                        expanded = expanded2,
                        onDismissRequest = { expanded2 = false },

                        ) {
                        valutes.forEach { currency ->
                            DropdownMenuItem(
                                text = { Text(currency) },
                                onClick = {
                                    selectedCurrency2 = currency
                                    expanded2 = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_10a")
@Composable
fun MainScreenPreview() {
    MyApplicationTheme {
        MainScreen()
    }
}

@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}