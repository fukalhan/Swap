package cz.cvut.fukalhan.swap.events.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cz.cvut.fukalhan.design.presentation.StringModel
import cz.cvut.fukalhan.design.theme.SwapAppTheme
import cz.cvut.fukalhan.design.system.components.Footer
import cz.cvut.fukalhan.design.system.components.TextInput
import cz.cvut.fukalhan.design.system.model.ButtonVo
import cz.cvut.fukalhan.design.system.model.FooterVo
import cz.cvut.fukalhan.design.system.model.TextInputVo
import cz.cvut.fukalhan.design.wrappers.ScreenContentWrapper
import cz.cvut.fukalhan.design.R
import cz.cvut.fukalhan.design.presentation.ComposeViewModel
import cz.cvut.fukalhan.design.presentation.PreviewViewModel
import cz.cvut.fukalhan.design.presentation.UiState
import cz.cvut.fukalhan.design.system.components.BasicHeader
import cz.cvut.fukalhan.design.system.components.DatePicker
import cz.cvut.fukalhan.design.system.components.SelectInput
import cz.cvut.fukalhan.design.system.model.BasicHeaderVo
import cz.cvut.fukalhan.design.system.model.CharCounterVo
import cz.cvut.fukalhan.design.system.model.DatePickerVo
import cz.cvut.fukalhan.design.system.model.IconVo
import cz.cvut.fukalhan.design.system.model.SelectInputVo
import cz.cvut.fukalhan.swap.events.model.addevent.AddEventScreenEvent
import cz.cvut.fukalhan.swap.events.model.addevent.AddEventScreenVo

@Composable
fun AddEventScreen(
    viewModel: ComposeViewModel<AddEventScreenVo, AddEventScreenEvent>,
    navigateBack: () -> Unit
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
                        onBackClick = navigateBack
                    )
                )
            },
            bottomBar = {
                Footer(
                    model = FooterVo(
                        primaryButton = ButtonVo.Basic(
                            label = StringModel.Resource(id = R.string.save),
                            onClick = {
                                viewModel.onEvent(AddEventScreenEvent.OnSaveEventClick)
                            },
                            enabled = viewState.data.saveButtonEnabled
                        ),
                        secondaryButton = ButtonVo.Basic(
                            label = StringModel.Resource(id = R.string.cancel),
                            onClick = {
                                viewModel.onEvent(AddEventScreenEvent.OnCancelEvent)
                            }
                        )
                    )
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {

                if (viewState.data.showDatePicker) {
                    DatePicker(
                        model = DatePickerVo(),
                        onDismiss = {
                            viewModel.onEvent(
                                AddEventScreenEvent.ChangeDatePickerVisibility(visible = false)
                            )
                        }
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(20.dp),
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 20.dp),
                        text = stringResource(id = R.string.add_event),
                        color = SwapAppTheme.colors.onBackground,
                        style = SwapAppTheme.typography.screenTitle
                    )

                    TextInput(
                        model = TextInputVo(
                            value = viewState.data.name,
                            label = StringModel.Resource(id = R.string.event_name),
                            onValueChange = {
                                viewModel.onEvent(AddEventScreenEvent.EventNameChanged(it))
                            }
                        )
                    )

                    TextInput(
                        model = TextInputVo(
                            value = viewState.data.description,
                            label = StringModel.Resource(id = R.string.event_description),
                            placeholder = StringModel.Resource(id = R.string.event_description_placeholder),
                            charCounter = CharCounterVo(
                                current = viewState.data.description.length,
                                limit = AddEventScreenVo.DESCRIPTION_CHAR_LIMIT
                            ),
                            singleLine = false,
                            minLines = 3,
                            maxLines = 3,
                            onValueChange = {
                                viewModel.onEvent(AddEventScreenEvent.EventNameChanged(it))
                            }
                        )
                    )

                    SelectInput(
                        modifier = Modifier.padding(bottom = 15.dp),
                        model = SelectInputVo(
                            value = viewState.data.dateTime,
                            placeholder = StringModel.Resource(id = R.string.event_date_choose),
                            label = StringModel.Resource(id = R.string.event_date),
                            endIcon = IconVo(res = R.drawable.ic_calendar)
                        ),
                        onClick = {
                            viewModel.onEvent(
                                AddEventScreenEvent.ChangeDatePickerVisibility(visible = true)
                            )
                        }
                    )

                    SelectInput(
                        model = SelectInputVo(
                            value = viewState.data.location,
                            placeholder = StringModel.Resource(id = R.string.event_location_choose),
                            label = StringModel.Resource(id = R.string.event_location),
                            endIcon = IconVo(res = R.drawable.ic_location)
                        ),
                        onClick = {
                            viewModel.onEvent(
                                AddEventScreenEvent.ChangeLocationPickerVisibility(visible = true)
                            )
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
internal fun AddEventScreenPreview() {
    AddEventScreen(
        viewModel = PreviewViewModel(
            state = UiState(
                data = AddEventScreenVo()
            )
        )
    ) { }
}
