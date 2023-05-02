package cz.cvut.fukalhan.swap.events.system.eventdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import cz.cvut.fukalhan.design.presentation.ScreenState
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.design.system.components.screenstate.FailSnackMessage
import cz.cvut.fukalhan.design.system.components.screenstate.FailureView
import cz.cvut.fukalhan.design.system.components.screenstate.LoadingView
import cz.cvut.fukalhan.design.system.components.screenstate.SuccessSnackMessage
import cz.cvut.fukalhan.design.system.semiTransparentBlack
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
    navigateToUserProfile: (String) -> Unit
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
        ResolveState(
            eventDetailState,
            navigateToUserProfile = {
                navigateToUserProfile(it)
            },
            onParticipateButtonClick = { eventId ->
                Firebase.auth.currentUser?.let { user ->
                    viewModel.addParticipantToEvent(eventId, user.uid)
                }
            },
            onCancelParticipationButtonClick = { eventId ->
                Firebase.auth.currentUser?.let { user ->
                    viewModel.removeParticipantFromEvent(eventId, user.uid)
                }
            }
        )
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
    navigateToUserProfile: (String) -> Unit,
    onParticipateButtonClick: (String) -> Unit,
    onCancelParticipationButtonClick: (String) -> Unit
) {
    when (state) {
        is EventDetailState.Loading -> LoadingView(semiTransparentBlack)
        is EventDetailState.AddParticipantToEventSuccess -> SuccessSnackMessage(state.message)
        is EventDetailState.AddParticipantToEventFail -> FailSnackMessage(state.message)
        is EventDetailState.RemoveParticipantFromEventSuccess -> SuccessSnackMessage(state.message)
        is EventDetailState.RemoveParticipantFromEventFail -> FailSnackMessage(state.message)
        is EventDetailState.Success -> EventDetail(
            state.event,
            navigateToUserProfile,
            onParticipateButtonClick,
            onCancelParticipationButtonClick
        )
        is EventDetailState.Failure -> FailureView(state.message)
        else -> Unit
    }
}

@Composable
fun EventDetail(
    event: EventState,
    navigateToUserProfile: (String) -> Unit,
    onParticipateButtonClick: (String) -> Unit,
    onCancelParticipationButtonClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(SwapAppTheme.dimensions.sidePadding)
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Top
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
        }

        Row(
            modifier = Modifier
                .padding(SwapAppTheme.dimensions.sidePadding)
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Firebase.auth.currentUser?.let { user ->
                if (event.participants.contains(user.uid)) {
                    Text(
                        text = stringResource(R.string.userRegisteredToEvent),
                        style = SwapAppTheme.typography.titleSecondary,
                        color = SwapAppTheme.colors.textPrimary
                    )

                    Button(
                        onClick = {
                            onCancelParticipationButtonClick(event.id)
                        },
                        colors = ButtonDefaults.buttonColors(SwapAppTheme.colors.primary)
                    ) {
                        Text(
                            text = stringResource(R.string.unsubscribe),
                            style = SwapAppTheme.typography.button,
                            color = SwapAppTheme.colors.buttonText
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Button(
                            onClick = {
                                onParticipateButtonClick(event.id)
                            },
                            colors = ButtonDefaults.buttonColors(SwapAppTheme.colors.primary)
                        ) {
                            Text(
                                text = stringResource(R.string.participate),
                                style = SwapAppTheme.typography.button,
                                color = SwapAppTheme.colors.buttonText
                            )
                        }
                    }
                }
            }
        }

        Surface(
            elevation = SwapAppTheme.dimensions.elevation,
            modifier = Modifier
                .padding(bottom = SwapAppTheme.dimensions.smallSidePadding)
                .wrapContentHeight()
                .fillMaxWidth()
        ) {
            OrganizerInfo(
                userId = event.organizerId,
                viewModel = koinViewModel(),
                navigateToUserProfile = navigateToUserProfile
            )
        }

        Surface(
            elevation = SwapAppTheme.dimensions.elevation,
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
        ) {
            ParticipantListView(
                participants = event.participants,
                koinViewModel(),
                onParticipantClick = navigateToUserProfile
            )
        }
    }
}
