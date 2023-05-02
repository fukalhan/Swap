package cz.cvut.fukalhan.swap.events.system.eventdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import cz.cvut.fukalhan.design.presentation.ScreenState
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.design.system.components.screenstate.FailureView
import cz.cvut.fukalhan.design.system.components.screenstate.LoadingView
import cz.cvut.fukalhan.swap.events.R
import cz.cvut.fukalhan.swap.events.presentation.eventdetail.EventDetailState
import cz.cvut.fukalhan.swap.events.presentation.eventdetail.EventDetailViewModel
import cz.cvut.fukalhan.swap.events.presentation.eventdetail.EventState
import org.koin.androidx.compose.koinViewModel

@Composable
fun EventDetailScreen(
    eventId: String,
    viewModel: EventDetailViewModel,
    onScreenInit: (ScreenState) -> Unit,
    navigateBack: () -> Unit,
    navigateToOrganizerProfile: (String) -> Unit
) {
    val eventDetailState by viewModel.eventDetailState.collectAsState()
    val effect = remember {
        {
            viewModel.getEventDetail(eventId)
        }
    }
    LaunchedEffect(Unit) {
        effect()
    }

    EventDetailTopBar(onScreenInit, navigateBack)
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        ResolveState(eventDetailState) {
            navigateToOrganizerProfile(it)
        }
    }
}

@Composable
fun EventDetailTopBar(
    onScreenInit: (ScreenState) -> Unit,
    navigateBack: () -> Unit
) {
    onScreenInit(
        ScreenState {
            Row(
                modifier = Modifier.fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = navigateBack,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_back),
                        contentDescription = null,
                        tint = SwapAppTheme.colors.buttonText,
                        modifier = Modifier.size(SwapAppTheme.dimensions.icon)
                    )
                }
                Text(
                    text = stringResource(R.string.eventDetail),
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
    state: EventDetailState,
    navigateToOrganizerProfile: (String) -> Unit
) {
    when (state) {
        is EventDetailState.Loading -> LoadingView()
        is EventDetailState.Success -> EventDetail(state.event, navigateToOrganizerProfile)
        is EventDetailState.Failure -> FailureView(state.message)
        else -> Unit
    }
}

@Composable
fun EventDetail(
    event: EventState,
    navigateToOrganizerProfile: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(SwapAppTheme.dimensions.sidePadding)
            .fillMaxSize()
    ) {
        Text(
            text = event.title,
            style = SwapAppTheme.typography.titlePrimary,
            color = SwapAppTheme.colors.textPrimary
        )

        Text(
            text = event.date,
            style = SwapAppTheme.typography.titleSecondary,
            color = SwapAppTheme.colors.textSecondary
        )

        Spacer(modifier = Modifier.height(SwapAppTheme.dimensions.mediumSpacer))

        Text(
            text = stringResource(R.string.eventDescription),
            style = SwapAppTheme.typography.titleSecondary,
            color = SwapAppTheme.colors.textPrimary
        )

        Text(
            text = event.description,
            style = SwapAppTheme.typography.body,
            color = SwapAppTheme.colors.textSecondary
        )

        OrganizerInfo(
            userId = event.organizerId,
            viewModel = koinViewModel(),
            navigateToUserProfile = navigateToOrganizerProfile
        )
    }
}
