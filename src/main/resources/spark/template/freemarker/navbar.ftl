<!-- Navigation -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
    <div class="container">
        <a class="navbar-brand" href="/">BlogSpark</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive"
                aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="#">Home
                        <span class="sr-only">(current)</span>
                    </a>
                </li>
                <li class="nav-item">
                    <#if user??>
                        <a class="nav-link" href="/new-article">Create Article</a>
                    </#if>
                </li>
                <li class="nav-item">
                    <#if user??>
                        <#if user.role == "admin">
                            <a class="nav-link" href="/create-user">Add new user</a>
                        </#if>
                    </#if>
                </li>
                <li class="nav-item">
                    <#if user??>
                        <a class="nav-link" href="/logout">Log out (${user.username})</a>
                    <#else>
                        <a class="nav-link" href="/login">Log In</a>
                    </#if>
                </li>
            </ul>
        </div>
    </div>
</nav>