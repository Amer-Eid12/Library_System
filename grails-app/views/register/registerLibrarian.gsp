<!DOCTYPE html>
<html lang="en">
<head>
    <title>Register Librarian</title>
    <meta name="layout" content="main" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <asset:stylesheet src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" />
</head>
<body>
<div class="container mt-5">
    <h1 class="mb-4">Register Librarian</h1>

    <g:if test="${flash.message}">
        <div class="alert alert-warning">${flash.message}</div>
    </g:if>

    <g:form controller="register" action="saveLibrarian" enctype="multipart/form-data">
        <div class="mb-3">
            <label for="username" class="form-label">Username</label>
            <g:textField name="username" value="${librarian?.username}" class="form-control" required="true" />
            <g:if test="${librarian?.errors?.hasFieldErrors('username')}">
                <div class="text-danger">${librarian.errors.getFieldError('username')?.defaultMessage}</div>
            </g:if>
        </div>

        <div class="mb-3">
            <label for="password" class="form-label">Password</label>
            <g:passwordField name="password" class="form-control" required="true" />
        </div>

        <div class="mb-3">
            <label for="confirmPassword" class="form-label">Confirm Password</label>
            <g:passwordField name="confirmPassword" class="form-control" required="true" />
        </div>

        <div class="mb-3">
            <label for="library" class="form-label">Library</label>
            <g:select name="library.id"
                      from="${libraries}"
                      optionKey="id"
                      optionValue="name"
                      value="${librarian?.library?.id}"
                      noSelection="['':'-- Select Library --']"
                      class="form-select"
                      required="true" />
            <g:if test="${librarian?.errors?.hasFieldErrors('library')}">
                <div class="text-danger">${librarian.errors.getFieldError('library')?.defaultMessage}</div>
            </g:if>
        </div>

        <div class="mb-3">
            <label class="form-label">Upload Image</label>
            <input type="file" name="imageFile" accept="image/*" class="form-control" />
        </div>

        <button type="submit" class="btn btn-success">Register</button>
    </g:form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
