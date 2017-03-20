var editButtonHTML="<button class='btn btn-xs btn-default' data-toggle='modal'"+
"data-target='#editTableRowModal' onclick='editRowModalHTML(this)'>Edit</button>";
var deleteButtonHTML="<button class='btn btn-xs btn-danger' data-toggle='modal'"+
"data-target='#deleteRowModal' onclick='editDeleteModalHTML(this)'>Delete</button>";

var idNumber;
var column;

var createEntryButtonHTML="<button class='btn btn-xs btn-primary' data-toggle='modal'"+
"data-target='#addEntryModal' onclick='getColumns()'>Add Entry</button>";

var addColumnButtonHTML="<button class='btn btn-xs btn-primary' data-toggle='modal'"+
"data-target='#addColumnModal'>Add</button>";
var deleteColumnButtonHTML="<button class='btn btn-xs btn-danger' data-toggle='modal'"+
"data-target='#deleteColumnModal' onclick='getColumns()'>Delete</button>";





function displayInventory(){
	var url= "/EZlog/displayInventory";
	var xmlhttp=null;

	if (window.XMLHttpRequest)
	{// code for IE7+, Firefox, Chrome, Opera, Safari

		xmlhttp=new XMLHttpRequest();
	}else{// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	var onreadystatechange=function(){
		if (xmlhttp.readyState==4 && xmlhttp.status==200){
			var output="";
			if (xmlhttp.responseText!="]"){	
				var jsonData = JSON.parse(xmlhttp.responseText);
				var name;
				var entry = jsonData[0];
				output+="<tr>";
				for (name in entry) {
					output+="<th>";
					output+=name;
					output+="</th>";
				}
				output+="<th class='btn-group'>" +
				addColumnButtonHTML+deleteColumnButtonHTML+ "</th>"
				output+="</tr>";	
				for (i = 0; i < jsonData.length; i++) {
					output+="<tr id='row-" + i +"'>";
					for(var key in jsonData[i]) {
						output+="<td>";
						var value = jsonData[i][key];
						output+=value;
						output+="</td>";
					}
					output+="<td class='btn-group'>"+
					editButtonHTML+deleteButtonHTML+"</td>";
					output+="</tr>";
				}
				for(var key in jsonData[0]) {
					output+="<td></td>";
				}
				output+="<td>"+createEntryButtonHTML+ "</td>";
			}
			else{
				output+="<tr> <th> Database is empty </th> </tr>";
				output+="<tr><td>"+createEntryButtonHTML+ "</tr></td>";
			}
			document.getElementById("inventory").innerHTML=output;
		}
	}
	xmlhttp.onload = onreadystatechange;
	xmlhttp.open("GET",url,true);
	xmlhttp.send(name);
}

function addEntryToInventory(){
	var url= "/EZlog/addToInventory?";
	var kvpairs = [];
	var form = document.getElementById('addEntryForm');// get the form somehow
	for ( var i = 0; i < form.elements.length; i++ ) {
		var e = form.elements[i];
		kvpairs.push(encodeURIComponent(e.id) + "=" + encodeURIComponent(e.value));
	}
	var queryString = kvpairs.join("&");
	url+="counter="+ form.elements.length + "&" + queryString;

	var xmlhttp=null;
	if (window.XMLHttpRequest)
	{// code for IE7+, Firefox, Chrome, Opera, Safari

		xmlhttp=new XMLHttpRequest();
	}
	else
	{// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	var onreadystatechange=function()
	{
		if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{
			displayInventory();
			$("#addEntryModal").modal("hide");
			for (var i=0; i<form.elements.length; i++){
				document.getElementById("input_"+i).value="";
			}


		}
	}

	if (document.getElementById("input_0").value!=""){
		xmlhttp.onload = onreadystatechange;
		xmlhttp.open("POST",url,true);
		xmlhttp.send(name);
	}
	else{
		alert("ID field is mandatory.");
	}
}

function getColumns(){
	var url= "/EZlog/addToInventory";
	var xmlhttp=null;
	if (window.XMLHttpRequest)
	{// code for IE7+, Firefox, Chrome, Opera, Safari

		xmlhttp=new XMLHttpRequest();
	}
	else
	{// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	var onreadystatechange=function()
	{
		if (xmlhttp.readyState==4 && xmlhttp.status==200){
			var output ="";
			var outputForDeleteColumnModal="<form><div class='form-group'>"+
      "<label for='sel1'>Columns</label><select class='form-control' id='column-deleted'>"+
      "<option></option>";

			output+="<form id='addEntryForm'>";
			var jsonData = JSON.parse(xmlhttp.responseText);
			for (var i = 0; i < jsonData.length; i++) {
				for(var key in jsonData[i]) {
					var value = jsonData[i][key];
					output+="<div>"+ value +"<input type='text' class='form-control'"+
					"placeholder='" + value+ "' id = 'input_" +i + "'></div><br>";
					if (value != "ID" && value != "Name" && value != "Description"){
					outputForDeleteColumnModal+="<option>" + value + "</option>";
					}
				}
			}
			output+="</form>";
			outputForDeleteColumnModal+="</select></div></form>";
			document.getElementById("addEntryModalBody").innerHTML=output;
			document.getElementById("deleteColumnModalBody").innerHTML=outputForDeleteColumnModal;
		}
	}
	xmlhttp.onload = onreadystatechange;
	xmlhttp.open("GET",url,true);
	xmlhttp.send(name);
}


function editDeleteModalHTML(x){
	var index = ($(x).closest('tr').index());
	var table = document.getElementById("inventory");
	var row = table.rows[index];
	var name = row.cells[1].innerHTML;
	//set global idNumber
	idNumber = row.cells[0].innerHTML;
	var output="Are you sure you want to delete the row where Name is '"+ name + "'?";
	document.getElementById("deleteRowModalBody").innerHTML=output;
}

function deleteRow(){
	var url= "/EZlog/deleteRowFromDatabase?val=" + idNumber;
	var xmlhttp=null;
	if (window.XMLHttpRequest)
	{// code for IE7+, Firefox, Chrome, Opera, Safari

		xmlhttp=new XMLHttpRequest();
	}
	else
	{// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	var onreadystatechange=function()
	{
		if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{
			displayInventory();
			$("#deleteRowModal").modal("hide");
		}
	}
	xmlhttp.onload = onreadystatechange;
	xmlhttp.open("POST",url,true);
	xmlhttp.send(name);
}

function editRowModalHTML(x){
	var index = ($(x).closest('tr').index());
	var table = document.getElementById("inventory");
	var row1 = table.rows[index];
	var row2 = table.rows[0];
	var columnCount = (table.rows[0].cells.length)-1;
	var output ="";
	output+="<form id='editRowForm'>";
	for (var i=0; i<columnCount; i++){
		var column = (row2.cells[i].innerHTML);
		var value = (row1.cells[i].innerHTML);
		if (i==0){
			output += "<div id='edit-div_"+ i +"'>"+ column +"<input type='text' class='form-control'"+
			"value='" + value + "' id = 'edit-input_" +i + "' disabled></div><br>";
		}else{
			output += "<div id='edit-div_"+ i +"'>"+ column +"<input type='text' class='form-control'"+
			"value='" + value + "' id = 'edit-input_" +i + "'></div><br>";
		}
	}
	output += "</form>";
	document.getElementById("editTableRowModalBody").innerHTML = output;
}

function editRow(){
	var url= "/EZlog/editInventory?";
	var xmlhttp=null;
	var kvpairs = [];
	var x = document.getElementById("editRowForm").elements.length;
	for (var i=0; i<x; i++){
		var value = (document.getElementById("edit-input_"+i).value);
		var name = "edit-input_"+i;
		kvpairs.push(encodeURIComponent(name) + "=" + encodeURIComponent(value));
	}
	var queryString = kvpairs.join("&");
	url+="counter="+ x + "&" + queryString;
	if (window.XMLHttpRequest)
	{// code for IE7+, Firefox, Chrome, Opera, Safari

		xmlhttp=new XMLHttpRequest();
	}
	else
	{// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	var onreadystatechange=function()
	{
		if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{
			displayInventory();
			$("#editTableRowModal").modal("hide");
		}
	}
	xmlhttp.onload = onreadystatechange;
	xmlhttp.open("POST",url,true);
	xmlhttp.send(name);
}

function addColumn(){
	var url= "/EZlog/addColumnToDatabase?";
	var xmlhttp=null;
	var columnName = document.getElementById("column-name").value;
	var columnType = document.getElementById("column-type").value;
	url+="columnName=" + columnName + "&columnType=" + columnType;
	if (window.XMLHttpRequest)
	{// code for IE7+, Firefox, Chrome, Opera, Safari

		xmlhttp=new XMLHttpRequest();
	}
	else
	{// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	var onreadystatechange=function()
	{
		if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{
			displayInventory();
			$("#addColumnModal").modal("hide");
		}
	}
	xmlhttp.onload = onreadystatechange;
	xmlhttp.open("POST",url,true);
	xmlhttp.send(name);
}

function editDeleteModalHTML(x){
	var index = ($(x).closest('tr').index());
	var table = document.getElementById("inventory");
	var row = table.rows[index];
	var name = row.cells[1].innerHTML;
	//set global idNumber
	idNumber = row.cells[0].innerHTML;
	var output="Are you sure you want to delete the row '"+ name + "'?";
	document.getElementById("deleteRowModalBody").innerHTML=output;
}

function editDeleteColumnModalHTML(x){
	var index = ($(x).closest('th').index());
	var table = document.getElementById("inventory");
	var row = table.rows[index];
	var name = row.cells[1].innerHTML;
	//set global idNumber
}


$('table').on('click', 'th', function(e) {
	alert("hi");
	alert($(this).html());
	selectedColumn = $(this).html();
	var output="Are you sure you want to delete "+ selectedColumn + "'?";
	document.getElementById("deleteColumnModalBody").innerHTML=output;
	$("#deleteColumnModal").modal("show");
});

function deleteColumn(){
	var url= "/EZlog/deleteColumnFromDatabase?";
	var xmlhttp=null;
	var columnName = document.getElementById('column-deleted').value;
	url+="columnName=" + columnName;
	if (window.XMLHttpRequest)
	{// code for IE7+, Firefox, Chrome, Opera, Safari

		xmlhttp=new XMLHttpRequest();
	}
	else
	{// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	var onreadystatechange=function()
	{
		if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{
			displayInventory();
			$("#deleteColumnModal").modal("hide");
		}
	}
	xmlhttp.onload = onreadystatechange;
	xmlhttp.open("POST",url,true);
	xmlhttp.send(name);
}
//$("#inventory").on('dblclick', 'th', function(e) { 
//alert("hi");
//var time = e.delegateTarget.tHead.rows[0].cells[this.cellIndex]
//var day  = this.parentNode.cells[0];

//alert([$(day).text(), $(time).text()]);
//});



function showColumnModal(){
	$("#columnModal").modal("show");
}

$(function(){
	$("#includedModals").load("modals.html"); 
});
















function searchDatabase(x){
	var url= "/EZlog/SearchDatabase?val="+x;
	var xmlhttp=null;
	if (window.XMLHttpRequest)
	{// code for IE7+, Firefox, Chrome, Opera, Safari

		xmlhttp=new XMLHttpRequest();
	}else{// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	var onreadystatechange=function(){
		if (xmlhttp.readyState==4 && xmlhttp.status==200){
			var output="";
			if (xmlhttp.responseText!="]"){	
				var jsonData = JSON.parse(xmlhttp.responseText);
				var name;
				var entry = jsonData[0];
				output+="<tr>";
				for (name in entry) {
					output+="<th>";
					output+=name;
					output+="</th>";
				}
				output+="<th class='btn-group'>" +
				addColumnButtonHTML+deleteColumnButtonHTML+ "</th>"
				output+="</tr>";
				for (i = 0; i < jsonData.length; i++) {
					output+="<tr id='row-" + i +"'>";
					for(var key in jsonData[i]) {
						output+="<td>";
						var value = jsonData[i][key];
						output+=value;
						output+="</td>";
					}
					output+="<td class='btn-group'>"+
					editButtonHTML+deleteButtonHTML+"</td>";
					output+="</tr>";
				}
				for(var key in jsonData[0]) {
					output+="<td></td>";
				}
				output+="<td>"+createEntryButtonHTML+ "</td>";
			}
			else{
				output+="<tr> <th> Database is empty </th> </tr>";
				output+="<tr><td>"+createEntryButtonHTML+ "</tr></td>";
			}
			document.getElementById("inventory").innerHTML=output;
		}
	}
	xmlhttp.onload = onreadystatechange;
	xmlhttp.open("GET",url,true);
	xmlhttp.send(name);
}






function GetTags(){
	var url= "/EZlog/GetTags";
	var xmlhttp=null;
	if (window.XMLHttpRequest)
	{// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp=new XMLHttpRequest();
	}
	else
	{// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	var onreadystatechange=function()
	{
		if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{
			var html= xmlhttp.responseText.split("~");
			//html[0] is table of items in cart
			document.getElementById("tag-table").innerHTML=html[0];
			if (html[2]){ //html[2] is total
				document.getElementById("total").innerHTML=html[2];
			}
			if (html[3]){ //html[3] is pay button
				document.getElementById("pay-button").innerHTML=html[3];
			}
			if (html[1]!=" "){//html[1] is unfound id
				alert(html[1] + " is not in the inventory system!");
				//document.getElementById("unknownItemTag").innerHTML=html[1];
				//$("#addItemModal").modal("show");
				deleteFromVirtualCart(html[1]);

			}
		}
	}
	xmlhttp.onload = onreadystatechange;
	xmlhttp.open("GET",url,true);
	xmlhttp.send(name);

	//setTimeout(GetTags, 2500);
}