const WKTYPE = {
    EXPORT: 1,
    IMPORT: 2,
    ALL: 3,
}

const WEBSOCKET_PROTOCOL = {
    CIDS_MONITOR_OPERATION_PRTSCN: "CIDS_MONITOR_OPERATION_PRTSCN"
}

const SEARCH_LOCATION_BY_TYPE = {
    export: [
        {label: "ALL", value: ""},
        {label: "Truck", value: 1},
        {label: "Security", value: 2},
        {label: "Enter MHS", value: 3},
        {label: "Exit MHS", value: 4},
    ],
    import: [
        {label: "ALL", value: ""},
        {label: "Truck", value: 5},
        {label: "Customs", value: 6},
        {label: "Exit MHS", value: 7},
        {label: "Enter MHS", value: 8},
    ],
    all: [
        {label: "ALL", value: ""},
        {label: "Truck", value: 1},
        {label: "Security", value: 2},
        {label: "Customs", value: 6},
        {label: "Enter MHS", value: 3},
        {label: "Exit MHS", value: 4},
    ]
}
const SEARCH_LOCATION_BY_TYPE_CHANGE = {
    export: [
        {label: "Truck", value: 1},
        {label: "Security", value: 2},
        {label: "Enter MHS", value: 3},
        {label: "Exit MHS", value: 4},
    ],
    import: [
        {label: "Truck", value: 5},
        {label: "Customs", value: 6},
        {label: "Exit MHS", value: 7},
        {label: "Enter MHS", value: 8},
    ],
    all: [
        {label: "Export Truck", value: 1},
        {label: "Export Security", value: 2},
        {label: "Export Enter MHS", value: 3},
        {label: "Export Exit MHS", value: 4},
        {label: "Import Truck", value: 5},
        {label: "Import Customs", value: 6},
        {label: "Import Exit MHS", value: 7},
        {label: "Import Enter MHS", value: 8},
    ]
}

const LOCATION_BY_TYPE = {
    import: SEARCH_LOCATION_BY_TYPE.import.slice(1, SEARCH_LOCATION_BY_TYPE.import.size),
    export: SEARCH_LOCATION_BY_TYPE.export.slice(1, SEARCH_LOCATION_BY_TYPE.export.size),
}

let SOCKET_URL="";

const LOG_LEVEL = {
    ERROR: "ERROR",
    INFO: "INFO"
}

const SYSTEM = {
    "OPR-SITE": "mgt_site",
    "OPR-MONITOR": "mon",
    "OPR-MANAGER": "mon_mgtt",
    "OPR-COLLECT": "data_colt",
}

