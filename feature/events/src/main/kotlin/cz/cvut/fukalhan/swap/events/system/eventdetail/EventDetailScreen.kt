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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
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
import cz.cvut.fukalhan.swap.events.tools.getBitmapFromImage
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
                        modifier = Modifier.size(SwapAppTheme.dimensions.icon)
                    )
                }
                Text(
                    text = stringResource(R.string.eventDetail),
                    style = SwapAppTheme.typography.screenTitle,
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
    onSubscribeToEventButtonClick: (String) -> Unit,
    onCancelSubscriptionButtonClick: (String) -> Unit
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
            )

            Text(
                text = event.date,
                style = SwapAppTheme.typography.titleSecondary,
            )

            Spacer(modifier = Modifier.height(SwapAppTheme.dimensions.smallSpacer))

            Text(
                text = stringResource(R.string.eventDescription),
                style = SwapAppTheme.typography.titleSecondary,
            )

            Text(
                text = event.description,
                style = SwapAppTheme.typography.body,
            )
        }

        MapView(eventLocation = LatLng(event.location.lat, event.location.lng))

        EventSubscription(
            event.participants,
            subscribeToEvent = {
                onSubscribeToEventButtonClick(event.id)
            },
            cancelSubscriptionToEvent = {
                onCancelSubscriptionButtonClick(event.id)
            }
        )

        OrganizerInfo(
            userId = event.organizerId,
            viewModel = koinViewModel(),
            navigateToUserProfile = navigateToUserProfile
        )

        ParticipantListView(
            participants = event.participants,
            koinViewModel(),
            onParticipantClick = navigateToUserProfile
        )
    }
}

@Composable
fun MapView(eventLocation: LatLng) {
    val markerState = rememberMarkerState(position = eventLocation)
    val cameraPosition = rememberCameraPositionState {
        position = CameraPosition(
            eventLocation,
            10f,
            0f,
            0f
        )
    }

    Box(
        modifier = Modifier
            .padding(SwapAppTheme.dimensions.smallSidePadding)
            .fillMaxWidth()
            .height(170.dp)
    ) {
        GoogleMap(cameraPositionState = cameraPosition) {
            Marker(
                state = markerState,
                icon = BitmapDescriptorFactory.fromBitmap(
                    getBitmapFromImage(LocalContext.current, R.drawable.location)
                )
            )
        }
    }
}

@Composable
fun EventSubscription(
    participants: List<String>,
    subscribeToEvent: () -> Unit,
    cancelSubscriptionToEvent: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(SwapAppTheme.dimensions.sidePadding)
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Firebase.auth.currentUser?.let { user ->
            if (participants.contains(user.uid)) {
                Text(
                    text = stringResource(R.string.userRegisteredToEvent),
                    style = SwapAppTheme.typography.titleSecondary,
                )

                Button(
                    onClick = cancelSubscriptionToEvent,
                    colors = ButtonDefaults.buttonColors(SwapAppTheme.colors.primary)
                ) {
                    Text(
                        text = stringResource(R.string.unsubscribe),
                        style = SwapAppTheme.typography.button,
                    )
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Button(
                        onClick = subscribeToEvent,
                        colors = ButtonDefaults.buttonColors(SwapAppTheme.colors.primary)
                    ) {
                        Text(
                            text = stringResource(R.string.participate),
                            style = SwapAppTheme.typography.button,
                        )
                    }
                }
            }
        }
    }
}
