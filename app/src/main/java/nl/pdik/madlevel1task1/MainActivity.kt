package nl.pdik.madlevel1task1

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.pdik.madlevel1task1.model.Reminder
import nl.pdik.madlevel1task1.ui.theme.MadLevel1Task1Theme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MadLevel1Task1Theme {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ReminderListScreen()
                }
            }
        }
    }
}

@Composable
fun ReminderListScreen() {
    Scaffold(
        topBar =  {
            TopAppBar( title = { Text(text = stringResource(id = R.string.app_name))})
        },
        content = {padding -> ScreenContent(Modifier.padding(padding))  }
    )
}

@Composable
private fun ScreenContent(modifier: Modifier){
    val reminders = remember { mutableStateListOf<Reminder>() }
    Column(modifier = Modifier) {
        AddReminder(onReminderAdd = { reminders.add(Reminder(it))})
        Reminders(reminders)
    }
}

@Composable
private fun Reminders(reminders: List<Reminder>){
    LazyColumn{
        items(items = reminders, itemContent = {
                reminder ->
            Text(
                text = reminder.reminderData,
                Modifier.padding(16.dp)
            )
            Divider(
                color = Color.LightGray, modifier = Modifier.alpha(0.5f),
                thickness = 1.dp
            )
        })

    }
}
@Composable
private fun AddReminder(onReminderAdd: (String) -> Unit){
    val context = LocalContext.current

    var newReminderData by remember {
        mutableStateOf(String())
    }
    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(value = newReminderData,
            placeholder = {context.getString(R.string.enter_new_reminder)},
            onValueChange = {
                    newReminderData = it
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            modifier = Modifier.width(180.dp),
            shape = RoundedCornerShape(16.dp),
            onClick = { verifyAddReminder(context,newReminderData);
                onReminderAdd(newReminderData)
            }
        ) {
            Text(text = context.getString(R.string.add))
        }
    }
}
private fun verifyAddReminder(context: Context, reminderText: String){
    var toastText = "\"" + reminderText + "\""
    toastText += if(reminderText.isNotBlank()){
        context.getString(R.string.reminder_added)
    } else {
        context.getString(R.string.no_empty_reminder)
    }
    Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MadLevel1Task1Theme {
        ReminderListScreen();
    }
}