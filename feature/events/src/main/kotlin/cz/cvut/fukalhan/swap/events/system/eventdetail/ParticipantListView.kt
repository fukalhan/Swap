package cz.cvut.fukalhan.swap.events.system.eventdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cz.cvut.fukalhan.design.theme.SwapAppTheme
import cz.cvut.fukalhan.design.system.components.screenstate.EmptyView
import cz.cvut.fukalhan.design.system.components.screenstate.FailureView
import cz.cvut.fukalhan.design.system.components.screenstate.LoadingView
import cz.cvut.fukalhan.swap.events.R
import cz.cvut.fukalhan.swap.events.presentation.eventdetail.ParticipantInfo
import cz.cvut.fukalhan.swap.events.presentation.eventdetail.ParticipantListState
import cz.cvut.fukalhan.swap.events.presentation.eventdetail.ParticipantListViewModel

@Composable
fun ParticipantListView(
    participants: List<String>,
    viewModel: ParticipantListViewModel,
    onParticipantClick: (String) -> Unit
) {
    val participantListState by viewModel.participantListState.collectAsState()
    val effect = remember {
        {
            viewModel.getParticipantList(participants)
        }
    }
    LaunchedEffect(Unit) {
        effect()
    }

    Surface(
        elevation = SwapAppTheme.dimensions.elevation,
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(SwapAppTheme.dimensions.smallSidePadding),
        ) {
            Text(
                text = stringResource(id = R.string.participants),
                style = SwapAppTheme.typography.titleSecondary,
            )

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                ResolveState(participantListState) {
                    onParticipantClick(it)
                }
            }
        }
    }
}

@Composable
fun ResolveState(
    state: ParticipantListState,
    onParticipantClick: (String) -> Unit
) {
    when (state) {
        is ParticipantListState.Loading -> LoadingView()
        is ParticipantListState.Success -> ParticipantList(
            state.participantsCount,
            state.participants,
            onParticipantClick
        )
        is ParticipantListState.Empty -> EmptyView(state.message)
        is ParticipantListState.Failure -> FailureView(state.message)
        else -> Unit
    }
}

@Composable
fun ParticipantList(
    participantsCount: String,
    participants: List<ParticipantInfo>,
    onParticipantClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = participantsCount,
            style = SwapAppTheme.typography.body,
            color = SwapAppTheme.colors.onBackground
        )

        Divider(color = SwapAppTheme.colors.onBackground, thickness = SwapAppTheme.dimensions.borderWidth)

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(participants) { user ->
                UserInfo(
                    id = user.id,
                    profilePic = user.profilePic,
                    username = user.username,
                    onUserClick = {
                        onParticipantClick(it)
                    }
                )
                Divider(color = SwapAppTheme.colors.onBackground, thickness = SwapAppTheme.dimensions.borderWidth)
            }
        }
    }
}
