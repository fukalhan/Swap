package cz.cvut.fukalhan.design.system.model

/**
 * View object for basic bottom footer with buttons and optional additional content
 *
 * @property primaryButton mandatory primary button
 * @property secondaryButton optional secondary button
 */
data class FooterVo(
    val primaryButton: ButtonVo.Basic,
    val secondaryButton: ButtonVo.Basic? = null,
)