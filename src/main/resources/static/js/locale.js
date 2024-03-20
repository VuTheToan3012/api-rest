/**
 * @author TrungNV (trungnv_bks@gmail.com)
 * @since 2023-09-19
 */

// Detect Locale
function detectLocale() {
    var detectedLocale = "en";
    if ($.cookie("lang") !== undefined) {
        detectedLocale = $.cookie("lang");
    }

    return detectedLocale;
}

function initI18next() {
    i18next.init({
        lng: detectLocale(),
        resources: JSON.parse(window.sessionStorage.getItem("i18n_resource"))
    }, function (err, t) {
        jqueryI18next.init(i18next, $, {
            tName: 'localeMessage',
            i18nName: 'i18n',
            handleName: 'localize',
            selectorAttr: 'data-i18n',
            targetAttr: 'i18n-target',
            optionAttr: 'i18n-options',
            useOptionsAttr: false,
            parseDefaultValueFromContent: true
        });

        $('[data-i18n]').localize();
    });

}

// Load Locale Message from Server
function loadLocaleMessage() {
    $.ajax({
        type: "GET",
        url: "/api/locale/all-locale-message",
        dataType: "JSON",
        contentType: "application/json",
        success: function(json) {
            window.sessionStorage.setItem("i18n_resource", JSON.stringify(json));
            initI18next();
        },
        error: function(xhr, status, error) {
            console.log("Fail : loadLocaleMessage");
            console.log('error', error);
        }
    });
}