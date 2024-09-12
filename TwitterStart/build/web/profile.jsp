<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Profile</title>
</head>
<body>
    <h2>Welcome ${profileUser.username}!</h2>
<nav>
    <ul>
        <li><a href="TweetController?action=list_tweets">Home</a></li>
        <li><a href="Profile">Profile</a></li>
    </ul>
</nav>
    <!-- Display Profile Picture -->
    <c:if test="${filename != null}">
        <img src="GetImage?username=${profileUser.username}" width="240" height="330"/>
    </c:if>

    <!-- Profile Picture Upload -->
    <h3>Add A Profile Picture!</h3>
    <form action="Upload" method="post" enctype="multipart/form-data">
        <div id="data">
            <input type="file" accept="image/*" name="file">
        </div>
        <div id="buttons">
            <label>&nbsp;</label>
            <input type="submit" value="Upload">
        </div>
    </form>

    <!-- Follow/Unfollow Logic -->
    <c:choose>
        <c:when test="${isFollowing}">
            <form action="FollowServlet" method="post">
                <input type="hidden" name="username" value="${profileUser.username}"/>
                <input type="hidden" name="action" value="unfollow"/>
                <button type="submit">Unfollow</button>
            </form>
        </c:when>
        <c:otherwise>
            <form action="FollowServlet" method="post">
                <input type="hidden" name="username" value="${profileUser.username}"/>
                <input type="hidden" name="action" value="follow"/>
              
            </form>
        </c:otherwise>
    </c:choose>

    <!-- Following List -->
    <h3>Following:</h3>
    <c:if test="${not empty followingList}">
        <ul>
            <c:forEach var="user" items="${followingList}">
                <li>${user.username} (ID: ${user.id})</li>
            </c:forEach>
        </ul>
    </c:if>
    <c:if test="${empty followingList}">
        <p>You are not following anyone.</p>
    </c:if>

    <!-- Followers List -->
    <h3>Followers:</h3>
    <c:if test="${not empty followersList}">
        <ul>
            <c:forEach var="user" items="${followersList}">
                <li>${user.username} (ID: ${user.id})</li>
            </c:forEach>
        </ul>
    </c:if>
    <c:if test="${empty followersList}">
        <p>No one is following you yet.</p>
    </c:if>

    <!-- Unfollow by ID -->
    <h3>Unfollow by ID:</h3>
    <form action="FollowServlet" method="post">
        <label for="unfollowId">Enter ID of user to unfollow:</label>
        <input type="text" id="unfollowId" name="unfollowId" required/>
        <input type="hidden" name="action" value="unfollowById"/>
        <button type="submit">Unfollow</button>
    </form>

    <!-- Follow by ID -->
    <h3>Follow by ID:</h3>
    <form action="FollowServlet" method="post">
        <label for="followId">Enter ID of user to follow:</label>
        <input type="text" id="followId" name="followId" required/>
        <input type="hidden" name="action" value="followById"/>
        <button type="submit">Follow</button>
    </form>

    <!-- Display User Tweets -->
    <h3>Your Tweets:</h3>
    <c:if test="${not empty tweets}">
        <c:forEach var="tweet" items="${tweets}">
            <div>
                <p><strong>${tweet.username}</strong>: ${tweet.text}</p>
                <p><em>${tweet.timestamp}</em></p>
                <p>Likes: ${tweet.like_count}</p>
                <hr>
            </div>
        </c:forEach>
    </c:if>
    <c:if test="${empty tweets}">
        <p>You haven't posted any tweets yet.</p>
    </c:if>

</body>
</html>
