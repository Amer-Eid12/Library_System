<!DOCTYPE html>
<html lang="en">
<head>
    <title>Librarian Details</title>
    <meta name="layout" content="main">
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <asset:stylesheet src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" />
</head>
<body>
<div class="container mt-5">
    <h2 class="mb-4">Librarian Details</h2>

    <div class="mb-3">
        <label class="form-label"><strong>Name:</strong></label>
        <p>${librarian.username}</p>
    </div>

    <div class="mb-3">
        <label class="form-label"><strong>Image:</strong></label>
        <g:if test="${librarian?.profileImage}">
            <img src="${createLink(controller: 'librarian', action: 'profileImage', id: librarian.id)}"
                 class="img-thumbnail" style="max-width: 150px;" alt="Profile Image" />
        </g:if>
        <g:if test="${!librarian?.profileImage}">
            <p><em>No image available.</em></p>
        </g:if>
    </div>

    <g:link action="index" class="btn btn-secondary mt-3">Back to List</g:link>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
