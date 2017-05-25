package com.example.poweranimal.web_lib.Google.LocationService.Utils.Request.Model.Common

/**
 * Created by felixproehl on 25.04.17.
 */

/**
 *
 * Supported languages of Google Maps Services
 *
 * Check out the list. The list may contain more languages in the future.
 *
 * Last review: April 29th 2017

 * @see <a href="https://developers.google.com/maps/faq#languagesupport">FAQ Language Support</a>
 */
enum class Language constructor(private val mLanguageCode: String) {

    ARABIC("ar"),
    BULGARIAN("bg"),
    BENGALI("bn"),
    CATALAN("ca"),
    CZECH("cs"),
    DANISH("da"),
    GERMAN("de"),
    GREEK("el"),
    ENGLISH("en"),
    ENGLISH_AUS("en-AU"),
    ENGLISH_GB("en-GB"),
    SPANISH("es"),
    BASQUE("eu"),
    FARSI("fa"),
    FINNISH("fi"),
    FILIPINO("fil"),
    FRENCH("fr"),
    GALICIAN("gl"),
    GUJARATI("gu"),
    HINDI("hi"),
    CROATIAN("hr"),
    HUNGARIAN("hu"),
    INDONESIAN("id"),
    ITALIAN("it"),
    HEBREW("iw"),
    JAPANESE("ja"),
    KANNADA("kn"),
    KOREAN("ko"),
    LITHUANIA("lt"),
    LATVIAN("lv"),
    MALAYALAM("ml"),
    MARATHI("mr"),
    DUTCH("nl"),
    NORWEGIAN("no"),
    POLISH("pl"),
    PORTUGUESE("pt"),
    PORTUGUESE_BRAZIL("pt-BR"),
    PORTUGUESE_PORTUGAL("pt-PT"),
    ROMANIAN("ro"),
    RUSSIAN("ru"),
    SLOVAK("sk"),
    SLOVENIAN("sl"),
    SERBIAN("sr"),
    SWEDISH("sv"),
    TAMIL("ta"),
    TELUGU("te"),
    THAI("th"),
    TAGALOG("tl"),
    TURKISH("tr"),
    UKRAINIAN("uk"),
    VIETNAMESE("vi"),
    CHINESE_SIMPLIFIED("zh-CN"),
    CHINESE_TRADITIONAL("zh-TW");

    override fun toString(): String {
        return mLanguageCode
    }
}
