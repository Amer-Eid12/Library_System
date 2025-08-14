<!DOCTYPE html>
<html lang="en">
<head>
    <title>Librarians List</title>
    <meta name="layout" content="main">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <asset:stylesheet src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" />
</head>
<body>
<div class="container mt-5">
    <h1 class="mb-4">Librarians List</h1>

    <g:if test="${flash.message}">
        <div class="alert alert-warning">${flash.message}</div>
    </g:if>

    <table class="table table-striped table-hover">
        <thead>
        <tr>
            <th>Name</th>
            <th style="width: 160px;">Actions</th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${librarians}" var="librarian">
            <tr>
                <td>
                    <g:link action="show" id="${librarian.id}">${librarian.username}</g:link>
                </td>
                <td>
                    <g:link action="edit" id="${librarian.id}" class="btn btn-sm btn-primary me-2">Edit</g:link>
                    <g:form action="delete" method="POST" class="d-inline">
                        <g:hiddenField name="id" value="${librarian.id}"/>
                        <g:submitButton name="delete" value="Delete" class="btn btn-sm btn-danger"
                                        onclick="return confirm('Are you sure?')" />
                    </g:form>
                </td>
            </tr>
        </g:each>
        </tbody>
    </table>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
