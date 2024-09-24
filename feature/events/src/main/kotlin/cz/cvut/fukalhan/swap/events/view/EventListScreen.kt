package cz.cvut.fukalhan.swap.events.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cz.cvut.fukalhan.design.theme.SwapAppTheme
import cz.cvut.fukalhan.design.R
import cz.cvut.fukalhan.design.presentation.ComposeViewModel
import cz.cvut.fukalhan.design.presentation.PreviewViewModel
import cz.cvut.fukalhan.design.presentation.UiState
import cz.cvut.fukalhan.design.system.components.Icon
import cz.cvut.fukalhan.design.system.model.IconVo
import cz.cvut.fukalhan.design.wrappers.ScreenContentWrapper
import cz.cvut.fukalhan.swap.events.model.EventListScreenEvent
import cz.cvut.fukalhan.swap.events.model.EventListScreenVo
import cz.cvut.fukalhan.swap.events.model.EventVo

@Composable
fun EventListScreen(
    viewModel: ComposeViewModel<EventListScreenVo, EventListScreenEvent>
) {
    val viewState by viewModel.viewState.collectAsState()

    ScreenContentWrapper(
        state = viewState
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { padding ->
            Box(
                modifier = Modifier.padding(padding),
                contentAlignment = Alignment.BottomEnd
            ) {
                EventListContent(
                    events = viewState.data.events,
                    sendEvent = viewModel::onEvent
                )
                
                FloatingActionButton(
                    modifier = Modifier.padding(
                        bottom = 20.dp,
                        end = 20.dp
                    ),
                    backgroundColor = SwapAppTheme.colors.primary,
                    onClick = {
                        viewModel.onEvent(EventListScreenEvent.OnAddEventClick)
                    }
                ) {
                    Icon(
                        model = IconVo(
                            res = R.drawable.ic_plus,
                            tint = SwapAppTheme.colors.onPrimary
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun EventListContent(
    events: List<EventVo>,
    sendEvent: (EventListScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = stringResource(id = R.string.event_list),
            style = SwapAppTheme.typography.screenTitle,
            color = SwapAppTheme.colors.onSurface
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 6.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(events) { index, event ->
                EventCard(
                    event = event,
                    onClick = sendEvent,
                    modifier = Modifier.padding(
                        top = if (index == 0) 8.dp else 0.dp
                    )
                )
            }
        }
    }
}

@Composable
private fun EventCard(
    event: EventVo,
    onClick: (EventListScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable {
            onClick(
                EventListScreenEvent.OnEventClick(
                    id = event.id
                )
            )
        },
        colors = CardDefaults.cardColors(
            containerColor = SwapAppTheme.colors.background
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp,
            pressedElevation = 8.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 12.dp,
                    vertical = 10.dp
                )
        ) {
            Text(
                text = event.name,
                style = SwapAppTheme.typography.titleSecondary,
                modifier = Modifier.padding(bottom = 4.dp)
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
                    text = event.date,
                    style = SwapAppTheme.typography.body,
                )

                Spacer(modifier = Modifier.width(12.dp))

                Icon(
                    model = IconVo(
                        res = R.drawable.ic_location,
                        tint = SwapAppTheme.colors.onBackground
                    )
                )

                Text(
                    text = event.location,
                    style = SwapAppTheme.typography.body,
                )
            }

            Text(
                text = event.description,
                style = SwapAppTheme.typography.body,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
internal fun EventListScreenPreview() {
    EventListScreen(
        viewModel = PreviewViewModel(
            state = UiState(
                data = EventListScreenVo(
                    events = listOf(
                        EventVo(
                            id = "",
                            name = "Swap Event",
                            date = "Date",
                            location = "Location",
                            description = "Description of the event"
                        ),
                        EventVo(
                            id = "",
                            name = "Swap Event",
                            date = "Date",
                            location = "Location",
                            description = "Very very long description of the event that overflows"
                        )
                    )
                )
            )
        )
    )
}
