/**
 * @author TrungNV (trungnv_bks@gmail.com)
 */

// Loading Image Tag
var loading = $('<div id="loading" class="loading"></div><img id="loading_img" alt="loading" src="/images/loading.gif" />').appendTo(document.body).hide();

$(document).ready(function (){
	var myAjaxSetup = {
		beforeSend: function(xhr, settings) {
			const token = $("meta[name='_csrf']").attr("content");
			const header = $("meta[name='_csrf_header']").attr("content");
			xhr.setRequestHeader(header, token);
			loading.show();
			if(settings.hideLoading && settings.hideLoading === true){
				loading.hide();
			}

		},
		complete: function(xhr, stat) {
			loading.hide();
		}
	};
	$.ajaxSetup(myAjaxSetup);
	// setHeightTable()
})

function setHeightTable(){
	const headerFilter = $(".headerFilter").height()
	$(".wrapperTable").css({
		height: `calc(100vh - 55px - 2rem - 1.5rem - 1.25rem - ${headerFilter}px - 3rem)`
	})
}

/**
 *
 * @param file
 * @returns {Promise<unknown>}
 */
async function convertImageToBase64 (file){
	let reader = new FileReader();
	return new Promise(resolve => {
		reader.onload = ev => {
			resolve(ev.target.result)
		}
		reader.readAsDataURL(file)
	})
}

const Regexs = {
	ip: /^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/,
	isNumber: /^[0-9]+$/,
}

/**
 * This function update style of input when duplicate = true
 * @param element
 * @constructor
 */
function onDuplicateTrue(element){
	if(!element) return;
	// remove old className
	element.removeClass("isDuplicateFalse");
	// add class
	element.addClass("isDuplicateTrue");
}

/**
 * This function update style of input when duplicate = false
 * @param element
 * @constructor
 */
function onDuplicateFalse(element){
	if(!element) return;
	// remove old className
	element.removeClass("isDuplicateTrue");
	// add class
	element.addClass("isDuplicateFalse");
}

/**
 * This function clear style of input duplicate
 * @param element
 * @constructor
 */
function clearDuplicateStyle(element){
	if(!element) return;
	element.removeClass("isDuplicateTrue");
	element.removeClass("isDuplicateFalse");
}

function fillOption(listOption, element, className=""){
	let optionHtml = "";
	if(!element) return;
	listOption.forEach((item) => {
		optionHtml += `<option class="${className} text-uppercase" value="${item.value}">${item.label}</option>`
	})
	element.html(optionHtml);
}


function limitString(s = "", length) {
	if (!s){
		return "";
	}
	if (s.length > length) {
		return s.slice(0, length) + '...';
	} else {
		return s;
	}
}

/**
 * Config Next change page button
 * @returns 
 */
function nextButton(){
	let button = '<svg class="btn-change-page" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" style="vertical-align: top !important">'
	+ '<g clip-path="url(#clip0_103_2)">'
	+ '<path d="M0 0L24 12L0 24V0Z" fill="gray"/>'
	+ '</g>'
	+ '<defs>'
	+ '<clipPath id="clip0_103_2">'
	+ '<rect width="24" height="24" fill="gray"/>'
	+ '</clipPath>'
	+ '</defs>'
	+ '</svg>'
	return button;
}

/**
 * Config Previous Change page Button 
 * @returns 
 */
function previousButton (){
	let button = '<svg class="btn-change-page" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" style="vertical-align: top !important">'
	+ '<g clip-path="url(#clip0_103_4)">'
	+ '<path d="M0 12L24 0V24L0 12Z" fill="gray"/>'
	+ '</g>'
	+ '<defs>'
	+ '<clipPath id="clip0_103_4">'
	+ '<rect width="24" height="24" fill="gray"/>'
	+ '</clipPath>'
	+ '</defs>'
	+ '</svg>'
	return button;
} 

/**
 * Define Export File Name
 * @param {*} locationName 
 * @returns 
 */
function exportExcelName(locationName){
	var d = new Date();
                    var year = d.getFullYear();
                    var month = (d.getMonth() + 1).toString().padStart(2, "0");
                    var day = d.getDate().toString().padStart(2, "0");
                    return locationName + year + month + day;
}

/**
 * Define Sort Data table
 * @param {*} a 
 * @param {*} b 
 * @param {*} columns 
 * @returns 
 */
function sortData(a, b, columns, sortby) {
	if(sortby === "asc"){
		for (let i = 0; i < columns.length; i++) {
			if (a[columns[i]] < b[columns[i]]) {
				return 1;
			}
			if (a[columns[i]] > b[columns[i]]) {
				return -1;
			}
		}
		return 0;
	}
	else{
		for (let i = 0; i < columns.length; i++) {
			if (a[columns[i]] < b[columns[i]]) {
				return -1;
			}
			if (a[columns[i]] > b[columns[i]]) {
				return 1;
			}
		}
		return 0;
	}   
}

var isEventAssigned = false; // Flag to check if event is assigned
/**
 * Export Data To Excel
 * @param sortParams
 * @param columnWidths
 * @param tableId
 * @param fileName
 * @param dataOfRow // For Export History Data
 */
function exportDataToExcel(sortParams, columnWidths , tableId, fileName, dataOfRow){
	const sortLocation = {
		IMPORT :[
			{name : "Truck Dock" , order : 1},
			{name : "Customs" , order : 2},
			{name : "Exit MHS" , order : 3},
			{name : "Enter MHS" , order : 4},
		],
		EXPORT :[
			{name : "Truck Dock" , order : 1},
			{name : "Security" , order : 2},
			{name : "Enter MHS" , order : 3},
			{name : "Exit MHS" , order : 4},
		]
	}
	// Create the export button and append it to the specified element
	// Assign the export action to the button
	if (!isEventAssigned) {
		const table = tableId.DataTable();

		// Get the number of columns
		const numColumns = table.context[0].aoColumns.length;

		// Get column information from aoColumns
		var columns = table.context[0].aoColumns.map(function (column) {
			return {title: column.sTitle, data: column.data};
		});

		// Get column names
		const columnNames = table.columns().header().toArray().map(header => $(header).text());

		// Create a new workbook and a new worksheet
		var wb = new ExcelJS.Workbook();
		var ws = wb.addWorksheet("Sheet1");

		// Set the title for the file
		ws.mergeCells('A1:' + String.fromCharCode(65 + (numColumns - 1)) + '1');
		var titleCell = ws.getCell('A1');
		titleCell.value = fileName + new Date().toISOString().slice(0, 10).replace(/-/g, "");
		titleCell.alignment = {vertical: 'middle', horizontal: 'center'};
		titleCell.border = {
			top: {style: 'thin'},
			left: {style: 'thin'},
			bottom: {style: 'thin'},
			right: {style: 'thin'}
		};

		// Add column names to the worksheet
		columnNames.forEach((name, index) => {
			var cell = ws.getCell(2, index + 1);
			cell.value = name;
			cell.alignment = {vertical: 'middle', horizontal: 'left'};
			cell.border = {
				top: {style: 'thin'},
				left: {style: 'thin'},
				bottom: {style: 'thin'},
				right: {style: 'thin'}
			};
			cell.fill = {type: 'pattern', pattern: 'solid', fgColor: {argb: 'FF808080'}};
		});

		// Set column widths
		columns.forEach((column, index) => {
			if (columnWidths.hasOwnProperty(index)) { // If a width for this column is specified
				ws.getColumn(index + 1).width = columnWidths[index]; // Set the width to the specified value
			} else {
				ws.getColumn(index + 1).width = 15; // Set the width to 15 for all other columns
			}
		});

		// Write sorted data to Excel
		let rowIndex = 3; // Start from the third row
		if (dataOfRow !== undefined) {
			// New way of handling data
			if (dataOfRow.length === 0) {
				const buttons = [{
					text: $.localeMessage('confirm'), class: 'btn btn-outline-secondary btn-sm', click: function () {
						closeDialog();
					}
				}]
				showDialog($.localeMessage("result"), $.localeMessage(messageDialog.noData), buttons, null, false);

				return ;
			} else {
				// Sort dataOfRow based on the defined columns and order
				dataOfRow.sort(function (a, b) {
					for (var i = 0; i < sortParams.length; i++) {
						var param = sortParams[i];
						var aValue = a[param.index];
						var bValue = b[param.index];

						// Convert to lowercase if values are string
						if (typeof aValue === 'string') aValue = aValue.toLowerCase();
						if (typeof bValue === 'string') bValue = bValue.toLowerCase();

						if (aValue < bValue) {
							return param.order === 'asc' ? -1 : 1;
						}
						if (aValue > bValue) {
							return param.order === 'asc' ? 1 : -1;
						}
					}
					return 0;
				});

				// Write sorted data to Excel
				dataOfRow.forEach(function (data) {
					columns.forEach(function (column, colIdx) {
						var cell = ws.getCell(rowIndex, colIdx + 1);
						 let cellValue = data[colIdx];
						if (cellValue.length >= 32000) {
							// If it does, truncate it and add '...'
							cellValue = cellValue.substring(0, 32000) + '...';
						}
						// Check if the column is 'Usage' and replace 0 and 1 with 'N' and 'Y' respectively
						if (columnNames[colIdx] === 'Usage') {
							cellValue = cellValue === 0 ? 'N' : 'Y';
						}

						cell.value = cellValue;
						cell.alignment = { vertical: 'middle', horizontal: 'left' };
						cell.border = {
							top: {style:'thin'},
							left: {style:'thin'},
							bottom: {style:'thin'},
							right: {style:'thin'}
						};
					});
					rowIndex++;
				});
			}
		}
		else {
			let dataRows = table.context[0].aiDisplay.map(index => table.context[0].aoData[index]._aData);
			// Check if dataRows is empty
			if (dataRows.length === 0) {
				const buttons = [{
					text: $.localeMessage('confirm'), class: 'btn btn-outline-secondary btn-sm', click: function () {
						closeDialog();
					}
				}]
				showDialog($.localeMessage("result"), $.localeMessage(messageDialog.noData), buttons, null, false);
				return ;
			} else {
				// Sort dataRows based on the defined columns and order
				// Sort dataRows based on the defined columns and order
				dataRows.sort(function (a, b) {
					for (var i = 0; i < sortParams.length; i++) {
						var param = sortParams[i];
						var aValue = a[columns[param.index].data];
						var bValue = b[columns[param.index].data];

						// Special case for tblMonitor table
						if (table.context[0].sTableId === 'tblMonitor' && param.index === 3) {
							var type = a[columns[0].data]; // Assuming the first column contains IMPORT/EXPORT
							var orderMap = sortLocation[type]; // Get the corresponding order map

							// Find the order of aValue and bValue
							var aOrder = orderMap.find(x => x.name === aValue).order;
							var bOrder = orderMap.find(x => x.name === bValue).order;

							// Use the order for comparison
							aValue = aOrder;
							bValue = bOrder;
						} else {
							// Convert to lowercase if values are string
							if (typeof aValue === 'string') aValue = aValue.toLowerCase();
							if (typeof bValue === 'string') bValue = bValue.toLowerCase();
						}

						if (aValue < bValue) {
							return param.order === 'asc' ? -1 : 1;
						}
						if (aValue > bValue) {
							return param.order === 'asc' ? 1 : -1;
						}
					}
					return 0;
				});
			}

			// Write sorted data to Excel
			let rowIndex = 3; // Start from the third row
			dataRows.forEach(function (data) {
				columns.forEach(function (column, colIdx) {
					if (data.hasOwnProperty(column.data)) {
						var cell = ws.getCell(rowIndex, colIdx + 1);
						let cellValue = data[column.data];

						// Check if the column is 'Usage' and replace 0 and 1 with 'N' and 'Y' respectively
						if (columnNames[colIdx] === 'Usage') {
							cellValue = cellValue === 0 ? 'N' : 'Y';
						}
						cell.value = cellValue;
						cell.alignment = {vertical: 'middle', horizontal: 'left'};
						cell.border = {
							top: {style: 'thin'},
							left: {style: 'thin'},
							bottom: {style: 'thin'},
							right: {style: 'thin'}
						};
					}
				});
				rowIndex++;
			});
		}

		// Write the workbook to a buffer
		wb.xlsx.writeBuffer().then(function (buffer) {
			// Use FileSaver.js to save the file
			saveAs(new Blob([buffer]), fileName + new Date().toISOString().slice(0, 10).replace(/-/g, "") + ".xlsx");
		});
		// isEventAssigned = true;
	}
}




