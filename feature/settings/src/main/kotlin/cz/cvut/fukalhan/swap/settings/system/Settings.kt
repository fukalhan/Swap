package cz.cvut.fukalhan.swap.settings.system

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import cz.cvut.fukalhan.design.presentation.ScreenState
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.design.system.components.screenstate.FailSnackMessage
import cz.cvut.fukalhan.design.system.components.screenstate.FailureView
import cz.cvut.fukalhan.design.system.components.screenstate.LoadingView
import cz.cvut.fukalhan.design.system.components.screenstate.SuccessSnackMessage
import cz.cvut.fukalhan.design.system.semiTransparentBlack
import cz.cvut.fukalhan.swap.settings.R
import cz.cvut.fukalhan.swap.settings.presentation.Failure
import cz.cvut.fukalhan.swap.settings.presentation.Loading
import cz.cvut.fukalhan.swap.settings.presentation.ProfilePictureChangeFail
import cz.cvut.fukalhan.swap.settings.presentation.ProfilePictureChangeSuccess
import cz.cvut.fukalhan.swap.settings.presentation.SettingsState
import cz.cvut.fukalhan.swap.settings.presentation.SettingsViewModel
import cz.cvut.fukalhan.swap.settings.presentation.UserDataLoaded

@Composable
fun SettingsScreen(
    onScreenInit: (ScreenState) -> Unit,
    viewModel: SettingsViewModel,
    onNavigateBack: () -> Unit,
    signOut: () -> Unit
) {
    val settingsState by viewModel.settingsState.collectAsState()
    val effect = remember {
        {
            Firebase.auth.currentUser?.let {
                viewModel.getUserData(it.uid)
            }
        }
    }
    LaunchedEffect(Unit) {
        effect()
    }

    TopBar(onScreenInit, onNavigateBack)
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        ResolveState(settingsState, signOut) { uri ->
            Firebase.auth.currentUser?.let { user ->
                viewModel.changeProfilePicture(user.uid, uri)
            }
        }
    }
}

@Composable
fun TopBar(
    onInitScreen: (ScreenState) -> Unit,
    onNavigateBack: () -> Unit,
) {
    onInitScreen(
        ScreenState {
            Row(
                modifier = Modifier.fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_back),
                        contentDescription = null,
                        tint = SwapAppTheme.colors.buttonText,
                        modifier = Modifier.size(SwapAppTheme.dimensions.icon)
                    )
                }
                Text(
                    text = stringResource(R.string.settings),
                    style = SwapAppTheme.typography.screenTitle,
                    color = SwapAppTheme.colors.buttonText,
                    modifier = Modifier.padding(start = SwapAppTheme.dimensions.sidePadding)
                )
            }
        }
    )
}

@Composable
fun ResolveState(
    state: SettingsState,
    signOut: () -> Unit,
    onProfileImageChange: (Uri) -> Unit,
) {
    when (state) {
        is Loading -> LoadingView(semiTransparentBlack)
        is Failure -> FailureView(state.message)
        is UserDataLoaded -> Settings(state, onProfileImageChange, signOut)
        is ProfilePictureChangeSuccess -> SuccessSnackMessage(state.message)
        is ProfilePictureChangeFail -> FailSnackMessage(state.message)
        else -> {}
    }
}

@Composable
fun Settings(
    state: UserDataLoaded,
    onProfileImageChange: (Uri) -> Unit,
    signOut: () -> Unit
) {
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                onProfileImageChange(it)
            }
        }
    )

    Column(
        modifier = Modifier
            .background(SwapAppTheme.colors.backgroundSecondary)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ProfilePicture(state.profilePic) {
            singlePhotoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }
        SignOutButton(signOut)
    }
}

@Composable
fun ProfilePicture(
    uri: Uri,
    onClick: () -> Unit
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(uri)
            .placeholder(R.drawable.profile_pic_placeholder)
            .crossfade(true)
            .build(),
        placeholder = painterResource(cz.cvut.fukalhan.design.R.drawable.profile_pic_placeholder),
        contentDescription = null,
        modifier = Modifier
            .clip(CircleShape)
            .size(150.dp)
            .clickable(onClick = onClick),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun SignOutButton(
    signOut: () -> Unit
) {
    Button(
        onClick = {
            val auth = Firebase.auth
            auth.signOut()
            signOut()
        },
        colors = ButtonDefaults.buttonColors(SwapAppTheme.colors.buttonPrimary)
    ) {
        Text(
            text = stringResource(R.string.signOut),
            style = SwapAppTheme.typography.button,
            color = SwapAppTheme.colors.buttonText
        )
    }
}
