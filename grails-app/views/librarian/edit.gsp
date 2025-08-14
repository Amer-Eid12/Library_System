<!DOCTYPE html>
<html lang="en">
<head>
    <title>Edit Student</title>
    <meta name="layout" content="main">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <asset:stylesheet src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" />
</head>
<body>
<div class="container mt-5">
    <h1 class="mb-4">Edit Student</h1>

    <g:if test="${flash.message}">
        <div class="alert alert-warning">${flash.message}</div>
    </g:if>

    <g:form action="update" method="post" class="needs-validation" novalidate="">
        <g:hiddenField name="id" value="${librarian?.id}" />

        <div class="mb-3">
            <label for="name" class="form-label">Name</label>
            <g:textField name="name" value="${librarian?.username}" class="form-control" id="name" required="true" />
            <g:if test="${librarian?.errors?.hasFieldErrors('username')}">
                <div class="text-danger">${librarian.errors.getFieldError('username').defaultMessage}</div>
            </g:if>
        </div>

        <button type="submit" class="btn btn-success">Save</button>
        <g:link action="index" class="btn btn-secondary ms-2">Cancel</g:link>
    </g:form>
</div>
</body>
</html>
