/**
 * @author TrungNV (trungnv_bks@gmail.com)
 * @since 2023-09-19
 */
$(document).ready(async function() {
    await initI18next();
    await loadLocaleMessage();
    await initializeWebSocketClient();
});