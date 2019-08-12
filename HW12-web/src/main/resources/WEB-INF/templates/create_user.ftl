<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
	</head>
	<body>
		<form id="create-form" action="/admin/userCreate" method="post" accept-charset="utf-8">
		    <h1>New User:</h1>
		
		    <div class="row">
		        <label for="holder-input">Name:</label>
		        <input id="holder-input" name="name" type="text" value="John Doe"/>
		    </div>
		
		    <div class="row">
		        <button type="submit">Save</button>
		    </div>
		</form>
	</body>
</html>