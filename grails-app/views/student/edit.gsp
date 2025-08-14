<!DOCTYPE html>
<html lang="en">
<head>
    <title>Edit Student</title>
    <meta name="layout" content="main" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <asset:stylesheet src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" />
</head>
<body>
<div class="container mt-5">
    <h1 class="mb-4">Edit Student</h1>

    <g:form action="update" method="post">
        <g:hiddenField name="id" value="${student.id}"/>
        <div class="mb-3">
            <label for="name" class="form-label">Name</label>
            <g:textField id="name" name="name" value="${student.username}" class="form-control" required="true"/>
        </div>

        <button type="submit" class="btn btn-success">Save</button>
        <g:link action="index" class="btn btn-secondary ms-2">Cancel</g:link>
    </g:form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
