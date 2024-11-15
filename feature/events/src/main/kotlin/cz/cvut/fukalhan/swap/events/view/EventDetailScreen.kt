package cz.cvut.fukalhan.swap.events.view

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import cz.cvut.fukalhan.design.presentation.ComposeViewModel
import cz.cvut.fukalhan.design.system.components.BasicHeader
import cz.cvut.fukalhan.design.system.components.SmallUserInfo
import cz.cvut.fukalhan.design.theme.SwapAppTheme
import cz.cvut.fukalhan.design.system.model.BasicHeaderVo
import cz.cvut.fukalhan.design.system.model.SmallUserInfoVo
import cz.cvut.fukalhan.design.wrappers.ScreenContentWrapper
import cz.cvut.fukalhan.design.R
import cz.cvut.fukalhan.design.presentation.PreviewViewModel
import cz.cvut.fukalhan.design.presentation.StringModel
import cz.cvut.fukalhan.design.presentation.UiState
import cz.cvut.fukalhan.design.system.components.Icon
import cz.cvut.fukalhan.design.system.model.IconVo
import cz.cvut.fukalhan.swap.events.model.eventdetail.EventDetailScreenEvent
import cz.cvut.fukalhan.swap.events.model.eventdetail.EventDetailScreenVo
import cz.cvut.fukalhan.swap.events.model.eventdetail.OrganizerInfoVo
import cz.cvut.fukalhan.swap.events.tools.getBitmapFromImage
import cz.cvut.fukalhan.swap.eventsdata.model.Location

@Composable
fun EventDetailScreen(
    viewModel: ComposeViewModel<EventDetailScreenVo, EventDetailScreenEvent>,
) {
    val viewState by viewModel.viewState.collectAsState()

    ScreenContentWrapper(
        state = viewState
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                BasicHeader(
                    model = BasicHeaderVo(
                        onBackClick = {
                            viewModel.onEvent(EventDetailScreenEvent.OnBackClick)
                        }
                    )
                )
            }
        ) { padding ->

            EventDetailContent(
                model = viewState.data,
                sendEvent = viewModel::onEvent,
                modifier = Modifier.padding(padding)
            )
        }
    }
}

@Composable
private fun EventDetailContent(
    model: EventDetailScreenVo,
    sendEvent: (EventDetailScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)
    ) {
        Text(
            modifier = Modifier.padding(top = 12.dp),
            text = model.name,
            style = SwapAppTheme.typography.titlePrimary,
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                model = IconVo(
                    res = R.drawable.ic_calendar,
                    tint = SwapAppTheme.colors.onBackground
                )
            )

            Text(
                text = model.date,
                style = SwapAppTheme.typography.body,
            )
        }

        Spacer(modifier = Modifier.height(SwapAppTheme.dimensions.smallSpacer))

        Text(
            text = stringResource(R.string.event_detail_description),
            style = SwapAppTheme.typography.button,
            color = SwapAppTheme.colors.onBackground
        )

        Text(
            text = model.description,
            style = SwapAppTheme.typography.body,
        )

        model.location?.let {
            MapView(
                eventLocation = LatLng(
                    it.lat,
                    it.lng
                )
            )
        }

        EventSubscription(
            isUserSubscribed = model.isUserSubscribed,
            sendEvent = sendEvent
        )

        model.organizerInfo?.let {
            SmallUserInfo(
                model = SmallUserInfoVo(
                    userId = model.organizerInfo.id,
                    profilePicUri = model.organizerInfo.profilePic,
                    username = model.organizerInfo.username
                ),
                onClick = {
                    sendEvent(
                        EventDetailScreenEvent.OnOrganizerProfileClick(id = model.organizerInfo.id)
                    )
                }
            )
        }

        /*ParticipantListView(
            participants = event.participants,
            koinViewModel(),
            onParticipantClick = navigateToUserProfile
        )*/
    }
}

private const val DEFAULT_ZOOM = 10f
private const val DEFAULT_TILT = 0f
private const val DEFAULT_BEARING = 0f

@Composable
private fun MapView(eventLocation: LatLng) {
    val markerState = rememberMarkerState(position = eventLocation)
    val cameraPosition = rememberCameraPositionState {
        position = CameraPosition(
            eventLocation,
            DEFAULT_ZOOM,
            DEFAULT_TILT,
            DEFAULT_BEARING
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
                    getBitmapFromImage(LocalContext.current, R.drawable.ic_location)
                )
            )
        }
    }
}

@Composable
fun EventSubscription(
    isUserSubscribed: Boolean,
    sendEvent: (EventDetailScreenEvent) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(
                if (isUserSubscribed) {
                    R.string.event_detail_user_subscribed
                } else {
                    R.string.event_detail_user_not_subscribed
                }
            ),
            style = SwapAppTheme.typography.body,
        )

        Button(
            colors = ButtonDefaults.buttonColors(SwapAppTheme.colors.primary),
            onClick = {
                sendEvent(
                    EventDetailScreenEvent.UserSubscriptionChanged(isSubscribed = !isUserSubscribed)
                )
            }
        ) {
            Text(
                text = stringResource(
                    if (isUserSubscribed) {
                        R.string.event_detail_unsubscribe
                    } else {
                        R.string.event_detail_subscribe
                    }
                ),
                style = SwapAppTheme.typography.button,
            )
        }
    }
}

@Preview
@Composable
internal fun EventDetailScreenPreview() {
    EventDetailScreen(
        viewModel = PreviewViewModel(
            state = UiState(
                data = EventDetailScreenVo(
                    name = "Swap Event",
                    date = "24.12.2024",
                    description = "A swap event is a community gathering where people bring clothes," +
                            "accessories, or unused items to exchange with others." +
                            "Participants can trade their gently used belongings," +
                            "fostering sustainability and reducing waste.",
                    location = Location(
                        lat = 50.0755,
                        lng = 14.4378
                    ),
                    organizerInfo = OrganizerInfoVo(
                        id = "",
                        profilePic = Uri.EMPTY,
                        username = "Organizer",
                        joinDate = StringModel.String("17.6.2020"),
                        rating = 4.5f
                    ),
                    isUserSubscribed = true
                )
            )
        )
    )
}
