package cz.cvut.fukalhan.swap.login.system.common

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.login.presentation.common.Failure
import cz.cvut.fukalhan.swap.login.presentation.common.Loading
import cz.cvut.fukalhan.swap.login.presentation.common.LoginState
import cz.cvut.fukalhan.swap.login.presentation.common.Success

@Composable
fun OnSuccessState(
    loginState: LoginState,
    navigateToMainScreen: () -> Unit
) {
    if (loginState is Success) {
        navigateToMainScreen()
    }
}

@Composable
fun OnFailState(loginState: LoginState) {
    if (loginState is Failure) {
        val context = LocalContext.current
        Toast.makeText(context, stringResource(loginState.message), Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun LoadingView(signInState: LoginState) {
    if (signInState is Loading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(SwapAppTheme.dimensions.icon),
                color = SwapAppTheme.colors.primary
            )
        }
    }
}