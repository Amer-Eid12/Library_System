<!DOCTYPE html>
<html lang="en">
<head>
    <title>Create New Library</title>
    <meta name="layout" content="main" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <asset:stylesheet src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" />
</head>
<body>
<div class="container mt-5">
    <h1 class="mb-4">Create New Library</h1>

    <g:if test="${flash.message}">
        <div class="alert alert-success">${flash.message}</div>
    </g:if>

    <g:form action="saveLibrary" method="post">
        <div class="mb-3">
            <label for="name" class="form-label">Library Name</label>
            <g:textField name="name" value="${library?.name ?: ''}" required="true" class="form-control" />
            <g:if test="${library?.errors?.hasFieldErrors('name')}">
                <div class="text-danger mt-1">
                    <g:eachError bean="${library}" field="name">
                        <div><g:message error="${it}" /></div>
                    </g:eachError>
                </div>
            </g:if>
        </div>

        <button type="submit" class="btn btn-success">Create Library</button>
        <g:link action="index" class="btn btn-secondary ms-2">Back to Home</g:link>
    </g:form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
