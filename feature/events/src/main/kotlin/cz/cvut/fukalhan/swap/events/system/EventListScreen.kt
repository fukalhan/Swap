package cz.cvut.fukalhan.swap.events.system

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
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
import androidx.compose.ui.text.style.TextOverflow
import cz.cvut.fukalhan.design.presentation.ScreenState
import cz.cvut.fukalhan.design.theme.SwapAppTheme
import cz.cvut.fukalhan.design.system.components.screenstate.EmptyView
import cz.cvut.fukalhan.design.system.components.screenstate.FailureView
import cz.cvut.fukalhan.design.system.components.screenstate.LoadingView
import cz.cvut.fukalhan.design.theme.semiTransparentBlack
import cz.cvut.fukalhan.swap.events.R
import cz.cvut.fukalhan.swap.events.presentation.EventListState
import cz.cvut.fukalhan.swap.events.presentation.EventListViewModel
import cz.cvut.fukalhan.swap.events.presentation.EventState

@Composable
fun EventListScreen(
    viewModel: EventListViewModel,
    onScreenInit: (ScreenState) -> Unit,
    navigateToAddEvent: () -> Unit,
    navigateToEventDetail: (String) -> Unit
) {
    val eventListState by viewModel.eventListState.collectAsState()
    val effect = remember {
        {
            viewModel.getEvents()
        }
    }
    LaunchedEffect(Unit) {
        effect()
    }

    TopBar(onScreenInit, navigateToAddEvent)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ResolveState(eventListState) {
            navigateToEventDetail(it)
        }
    }
}

@Composable
fun TopBar(
    onScreenInit: (ScreenState) -> Unit,
    addEvent: () -> Unit
) {
    onScreenInit(
        ScreenState {
            Text(
                text = stringResource(R.string.swapEvents),
                style = SwapAppTheme.typography.screenTitle,
                modifier = Modifier.padding(start = SwapAppTheme.dimensions.sidePadding)
            )

            IconButton(onClick = addEvent) {
                Icon(
                    painter = painterResource(R.drawable.plus),
                    contentDescription = null,
                    tint = SwapAppTheme.colors.onPrimary,
                    modifier = Modifier.size(SwapAppTheme.dimensions.icon)
                )
            }
        }
    )
}

@Composable
fun ResolveState(
    state: EventListState,
    onEventClick: (String) -> Unit
) {
    when (state) {
        is EventListState.Loading -> LoadingView(semiTransparentBlack)
        is EventListState.Empty -> EmptyView(state.message)
        is EventListState.Failure -> FailureView(state.message)
        is EventListState.Success -> EventList(state.events, onEventClick)
        else -> Unit
    }
}

@Composable
fun EventList(
    events: List<EventState>,
    onEventClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(events) { event ->
            EventCard(event, onEventClick)
            Divider(color = SwapAppTheme.colors.onBackground, thickness = SwapAppTheme.dimensions.borderWidth)
        }
    }
}

@Composable
fun EventCard(
    event: EventState,
    onEventClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .clickable { onEventClick(event.id) }
            .padding(SwapAppTheme.dimensions.smallSidePadding)
    ) {
        Text(
            text = event.title,
            style = SwapAppTheme.typography.titleSecondary,
        )
        Text(
            text = event.date,
            style = SwapAppTheme.typography.body,
        )
        Text(
            text = event.description,
            style = SwapAppTheme.typography.body,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
