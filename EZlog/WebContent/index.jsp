<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<link href="EZ_logo.gif" rel="icon">
<title>EZlog</title>

<!-- Bootstrap -->
<script src="jquery-3.1.1.js"></script>
<link href="bootstrap.css" rel="stylesheet">
<script src="myjava.js"></script>
<script src="bluetooth.js"></script>
<script src="bootstrap.js"></script>


<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

<title>Login Success Page</title>
</head>
<body style='background-color: #F0F0F0' onload="displayInventory()">
	<%
		//allow access only if session exists
		String user = (String) session.getAttribute("user");
		String userName = null;
		String sessionID = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("user"))
					;
				userName = cookie.getValue();
				if (cookie.getName().equals("JSESSIONID"))
					sessionID = cookie.getValue();
			}
		}
	%>
	<nav class="navbar navbar-default" style="background-color: #454545">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" style="color: #fff"> EZLOG </a>
		</div>
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<form class="navbar-form navbar-right" style="margin: 0px;"
				action="<%=response.encodeURL("LogoutServlet")%>" method="post">
				<button type="submit" value="Logout"
					class="btn btn-danger navbar-btn" style="padding: 1 px">Logout</button>
			</form>
			<div class="navbar-form navbar-right">
				<div class="form-group">
					<input type="text" class="form-control" placeholder="Search"
						onkeyup="searchDatabase(this.value)">
				</div>
			</div>
			<div class="btn-group navbar-right" role="group">
				<button value="bluetooth" class="btn btn-primary navbar-btn"
					style="padding: 1 px" onclick="bluetooth()">Pair Bluetooth</button>
				<!-- <button value="create-database" class="btn btn-primary navbar-btn"
					style="padding: 1 px" data-toggle="modal"
					data-target="#createDatabaseModal">Create Database</button>
					 -->
			</div>

		</div>
	</div>
	</nav>
	<br>
	<br>

	<div class="row">
		<div class='col-md-2'></div>
		<div class='col-md-8'>
			<div class="table-responsive">
				<table class="table" id="inventory"></table>
			</div>
		</div>
		<div class='col-md-2'></div>
	</div>



	<div id="includedModals"></div>

	<!-- need to encode all the URLs where we want session information to be passed -->
</body>
</html>