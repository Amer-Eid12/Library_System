<!DOCTYPE html>
<html lang="en">
<head>
    <title>Students List</title>
    <meta name="layout" content="main" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <asset:stylesheet src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" />
</head>

<body>
<div class="container mt-5">
    <h1 class="mb-4">Students List</h1>

    <table class="table table-striped table-hover">
        <thead class="table-dark">
        <tr>
            <th>Name</th>
            <th>No. of Borrowed Books</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${students}" var="student">
            <tr>
                <td><g:link action="show" id="${student.id}">${student.username}</g:link></td>
                <td>${student.bookBorrowed}</td>
                <td>
                    <g:link action="edit" id="${student.id}" class="btn btn-sm btn-primary me-2">Edit</g:link>
                    <g:form action="delete" method="POST" style="display:inline;">
                        <g:hiddenField name="id" value="${student.id}"/>
                        <g:submitButton name="delete" value="Delete" class="btn btn-sm btn-danger"
                                        onclick="return confirm('Are you sure?')"/>
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
