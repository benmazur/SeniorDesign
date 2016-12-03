<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<link href="EZ_logo.gif" rel="icon">
<title>EZlog</title>

<!-- Bootstrap -->
<link href="bootstrap.css" rel="stylesheet">
<script src="bootstrap.js"></script>
<script src="jquery-3.1.1.js"></script>
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<nav class="navbar navbar-default" style="background-color: #454545">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="#" style="color: #fff"> EZLOG </a>
			</div>
		</div>
	</nav>
	<div class='col-md-3'></div>
	<div class='col-md-6'>
		<?php
   include 'seconddiv.html';
  ?>
		<div class="table-responsive">
			<table class="table">
				<col width="12%">
				<col width="44%">
				<col width="44%">
				
				<tr>
					<th>Price</th>
					<th>Item</th>
					<th>Tag</th>
				</tr>
				
					<!-- Start of Example Items -->		
				<tr>
					<td>a</td>
					<td>b</td>
					<td>c</td>
				</tr>
				<tr>
					<td>d</td>
					<td>e</td>
					<td>f</td>
				</tr>
				<tr style="border-bottom:solid 1px #ddd">
					<td>g</td>
					<td>h</td>
					<td>i</td>
				</tr>
				<tr>
					<td>j</td>
				</tr>
					<!-- End of Example Items -->	

			</table>
			
		</div>
	</div>
	<div class='col-md-3'></div>


</body>
</html>