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

function pay(){
	var url= "/EZlog/pay";
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
			document.getElementById("total").innerHTML="";
			document.getElementById("pay-button").innerHTML="";

		}
	}
	xmlhttp.onload = onreadystatechange;
	xmlhttp.open("GET",url,true);
	xmlhttp.send(name);
}
function deleteFromVirtualCart(x){
	//var x = document.getElementById("unknownItemTag").innerHTML;
	//var url= "/EZlog/deleteFromVirtualCart?val=" + val;
	var url= "/EZlog/deleteFromVirtualCart?val=" + x;
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

		}
	}
	xmlhttp.onload = onreadystatechange;
	xmlhttp.open("POST",url,true);
	xmlhttp.send(name);
}

function addToInventoryDatabase(){
	var title = document.getElementById("unknownItemTitle").value;
	var tag = document.getElementById("unknownItemTag").innerHTML;
	var price = document.getElementById("unknownItemPrice").value;
	
	
	var url= "/EZlog/addItemToInventory?title="+title+"&tag="+tag+"&price="+price;
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

		}
	}
	xmlhttp.onload = onreadystatechange;
	xmlhttp.open("POST",url,true);
	xmlhttp.send(name);
}

