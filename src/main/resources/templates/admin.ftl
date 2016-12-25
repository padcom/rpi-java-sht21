<h1>Admin interface</h1>

<form action="/logout" method="post">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	<input type="submit" value="Logout"/>
</form>
