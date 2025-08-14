<!DOCTYPE html>
<html lang="en">
<head>
    <title>Edit Book</title>
    <meta name="layout" content="main">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <asset:stylesheet src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" />
</head>
<body>
<div class="container mt-5">
    <h1 class="mb-4">Edit Book</h1>

    <g:if test="${flash.message}">
        <div class="alert alert-warning">${flash.message}</div>
    </g:if>

    <g:form action="update" method="POST" class="needs-validation" novalidate="">
        <g:hiddenField name="id" value="${book?.id}" />

        <div class="mb-3">
            <label for="title" class="form-label">Title</label>
            <g:textField name="title" value="${book?.title}" class="form-control" required="true" />
            <g:if test="${book?.errors?.hasFieldErrors('title')}">
                <div class="text-danger">${book.errors.getFieldError('title').defaultMessage}</div>
            </g:if>
        </div>

        <div class="mb-3">
            <label for="isbn" class="form-label">ISBN</label>
            <g:textField name="isbn" id="isbn" value="${book?.isbn}" class="form-control" required="true" maxlength="17" />
            <g:if test="${book?.errors?.hasFieldErrors('isbn')}">
                <div class="text-danger">${book.errors.getFieldError('isbn').defaultMessage}</div>
            </g:if>
        </div>

        <div class="mb-3">
            <label for="author" class="form-label">Author</label>
            <g:textField name="author" value="${book?.author}" class="form-control" required="true" />
            <g:if test="${book?.errors?.hasFieldErrors('author')}">
                <div class="text-danger">${book.errors.getFieldError('author').defaultMessage}</div>
            </g:if>
        </div>

        <button type="submit" class="btn btn-success">Save Changes</button>
        <g:link action="index" class="btn btn-secondary ms-2">Cancel</g:link>
    </g:form>
</div>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const isbnInput = document.getElementById("isbn");

        isbnInput.addEventListener("input", function () {
            let value = isbnInput.value.replace(/\D/g, '');
            value = value.substring(0, 13);

            let formatted = '';
            if (value.length > 0) formatted += value.substring(0, 3);
            if (value.length > 3) formatted += '-' + value.substring(3, 5);
            if (value.length > 5) formatted += '-' + value.substring(5, 10);
            if (value.length > 10) formatted += '-' + value.substring(10, 12);
            if (value.length > 12) formatted += '-' + value.substring(12, 13);

            isbnInput.value = formatted;
        });
    });
</script>

</body>
</html>
