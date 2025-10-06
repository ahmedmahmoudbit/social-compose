import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

val Int.verticalSpace: @Composable () -> Unit
    get() = {
        Spacer(modifier = Modifier.height(this.dp))
    }


val Int.horizontalSpace: @Composable () -> Unit
    get() = {
        Spacer(modifier = Modifier.width(this.dp))
    }
