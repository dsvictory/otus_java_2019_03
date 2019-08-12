<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Users list!</title>
    <style type="text/css">
        body {
            padding: 50px;
        }
        .users, .users td {
            border: 1px solid lightgray;
            padding: 5px;
        }
    </style>
</head>
<body>
	<h1>Users list:</h1>
   <table border=1 class="users">
	  <#list users as user>
	    <tr>
	    	<td>${user.id}</td>
	    	<td>${user.name}</td>
	    </tr>
	  </#list>
	</table> 
	<p><a href="/admin">To the admin page...</a></p>
</body>
</html>