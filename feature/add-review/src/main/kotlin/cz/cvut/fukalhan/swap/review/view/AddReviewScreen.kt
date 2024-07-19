package cz.cvut.fukalhan.swap.review.view

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cz.cvut.fukalhan.design.presentation.ComposeViewModel
import cz.cvut.fukalhan.design.presentation.StringModel
import cz.cvut.fukalhan.design.theme.SwapAppTheme
import cz.cvut.fukalhan.design.system.components.Footer
import cz.cvut.fukalhan.design.system.components.UserInfoView
import cz.cvut.fukalhan.design.system.model.ButtonVo
import cz.cvut.fukalhan.design.system.model.FooterVo
import cz.cvut.fukalhan.design.wrappers.ScreenContentWrapper
import cz.cvut.fukalhan.swap.review.model.AddReviewScreenData
import cz.cvut.fukalhan.swap.review.model.AddReviewScreenEvent
import cz.cvut.fukalhan.design.R
import cz.cvut.fukalhan.design.presentation.PreviewViewModel
import cz.cvut.fukalhan.design.presentation.UiState
import cz.cvut.fukalhan.design.system.components.BasicHeader
import cz.cvut.fukalhan.design.system.components.Icon
import cz.cvut.fukalhan.design.system.components.TextInput
import cz.cvut.fukalhan.design.system.model.BasicHeaderVo
import cz.cvut.fukalhan.design.system.model.CharCounterVo
import cz.cvut.fukalhan.design.system.model.IconVo
import cz.cvut.fukalhan.design.system.model.TextInputVo
import cz.cvut.fukalhan.design.system.model.UserInfoViewVo

/**
 * Screen for adding reviews to users
 */
@Composable
fun AddReviewScreen(
    viewModel: ComposeViewModel<AddReviewScreenData, AddReviewScreenEvent>
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
                        title = StringModel.Resource(id = R.string.addReview),
                        onBackClick = {
                            viewModel.onEvent(AddReviewScreenEvent.OnBackClick )
                        }
                    )
                )
            },
            bottomBar = {
                Footer(
                    model = FooterVo(
                        primaryButton = ButtonVo.Basic(
                            label = StringModel.Resource(id = R.string.save),
                            onClick = {
                                viewModel.onEvent(AddReviewScreenEvent.OnSaveReviewClick)
                            },
                            enabled = viewState.data.allFieldsFilled
                        ),
                        secondaryButton = ButtonVo.Basic(
                            label = StringModel.Resource(id = R.string.cancel),
                            onClick = {
                                viewModel.onEvent(AddReviewScreenEvent.OnCancelReviewClick)
                            }
                        )
                    )
                )
            }
        ) { padding ->

            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                viewState.data.userInfo?.let {
                    UserInfoView(
                        model = it,
                        onClick = {
                            viewModel.onEvent(AddReviewScreenEvent.OnUserProfileClick)
                        }
                    )
                }

                Divider(
                    color = SwapAppTheme.colors.secondaryVariant.copy(
                        alpha = 0.5f
                    ),
                    thickness = 8.dp,
                )

                Column(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    RatingView(
                        rating = viewState.data.rating,
                        onRatingChange = {
                            viewModel.onEvent(AddReviewScreenEvent.OnRatingChange(it))
                        }
                    )

                    TextInput(
                        model = TextInputVo(
                            value = viewState.data.review,
                            onValueChange = {
                                viewModel.onEvent(AddReviewScreenEvent.OnReviewChange(it))
                            },
                            label = StringModel.Resource(id = R.string.reviewDescription),
                            placeholder = StringModel.Resource(id = R.string.reviewDescriptionPlaceholder),
                            singleLine = false,
                            minLines = 3,
                            maxLines = 3,
                            charCounter = CharCounterVo(
                                limit = AddReviewScreenData.REVIEW_CHAR_LIMIT,
                                current = viewState.data.review.length
                            )
                        )
                    )
                }
            }
        }
    }
}

/**
 * View for displaying rating stars input
 *
 * @param rating currently inputted rating
 * @param onRatingChange callback on rating input change
 */
@Composable
private fun RatingView(
    rating: Int,
    onRatingChange: (Int) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        for (i in AddReviewScreenData.MIN_RATING..AddReviewScreenData.MAX_RATING) {
            val filled = i <= rating
            IconButton(
                onClick = {
                    onRatingChange(i)
                }
            ) {
                Icon(
                    model = IconVo(
                        res = if (filled) {
                            R.drawable.filled_star
                        } else {
                            R.drawable.star
                        },
                        size = 35.dp
                    )
                )
            }
        }
    }
}

@Preview
@Composable
internal fun AddReviewScreenPreview() {
    AddReviewScreen(
        viewModel = PreviewViewModel(
            state = UiState(
                data = AddReviewScreenData(
                    userInfo = UserInfoViewVo(
                        username = "Username",
                        profilePicUri = Uri.EMPTY,
                        joinDate = StringModel.String("Joined on 12.5.2020"),
                        rating = 4f
                    )
                )
            )
        )
    )
}
