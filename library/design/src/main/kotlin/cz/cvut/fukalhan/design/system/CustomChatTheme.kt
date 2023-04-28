package cz.cvut.fukalhan.design.system

import androidx.compose.runtime.Composable
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.ui.theme.StreamColors

private val customChatColorPalette = StreamColors(
    textHighEmphasis = darkGrey,
    textLowEmphasis = grey,
    disabled = lightGrey,
    borders = grey,
    inputBackground = white,
    appBackground = white,
    barsBackground = lightGrey,
    linkBackground = lightGrey,
    overlay = semiTransparentBlack,
    overlayDark = white,
    primaryAccent = darkPeach,
    errorAccent = lightGrey,
    infoAccent = beige,
    highlight = peach,
    ownMessagesBackground = peach,
    otherMessagesBackground = white,
    deletedMessagesBackground = lightGrey,
    giphyMessageBackground = white,
    threadSeparatorGradientStart = grey,
    threadSeparatorGradientEnd = white,
)

@Composable
fun CustomChatTheme(
    content: @Composable () -> Unit
) {
    ChatTheme(colors = customChatColorPalette, content = content)
}
