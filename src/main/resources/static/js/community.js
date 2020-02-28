/**
 * 提交回复
 */
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    //这里是前端校验
    comment2Target(questionId, 1, content);

}

function comment2Target(TargetId, type, content) {
    if (!content) {
        alert("不能回复空内容哦~");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": TargetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else {
                if (response.code == 2003) {
                    var isAccept = confirm(response.message);
                    if (isAccept == true) {
                        window.open("https://github.com/login/oauth/authorize?client_id=74a1e16659cdef0235ae&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", true);
                    }
                } else {
                    alert(response.message);
                }

            }
            console.log(response)
        },
        dataType: "json"
    });
}

function comment(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#input-" + id).val();
    comment2Target(id, 2, comments);
}

/**
 * 二级评论下拉
 */

function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);
    var attribute = e.getAttribute("comment-collapse");
    if (attribute == null) {
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length != 1) {
            //展开二级评论
            comments.addClass("in");
            e.setAttribute("comment-collapse", "in");
            e.classList.add("active");
        } else {
            $.getJSON("/comment/" + id, function (data) {
                console.log(data);
                var subCommentContainer = $("#comment-" + id);
                $.each(data.data.reverse(), function (index,comment) {
                    var mediaLeftElement = $("<div/>",{
                        "class": "media-left"
                    }).append($("<img/>",{
                        "class":"media-object img-rounded media-object-change",
                        "src":comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>",{
                        "class":"media-body"
                    }).append($("<h5/>",{
                        "class":"media-heading",
                        "html":comment.user.name
                    })).append($("<div/>",{
                        "html":comment.content
                    })).append($("<div/>",{
                        "class":"menu",
                    })).append($("<span/>",{
                        "class":"pull-right menu",
                        "html":moment(comment.gmtCreate).format('YYYY-MM-DD')
                    }));

                    var mediaElement = $("<div/>",{
                        "class":"media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>",{
                        "class": "col-lg-12 col-xs-12 col-sm-12 col-md-12 comments",
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
                //展开二级评论
                comments.addClass("in");
                e.setAttribute("comment-collapse", "in");
                e.classList.add("active");

            })
        }

    } else {
        //关闭二级评论
        comments.removeClass("in");
        e.removeAttribute("comment-collapse");
        e.classList.remove("active");
    }
}

/**
 * 提交标签
 * @param value
 */

function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var precious = $("#tag").val();

    if (precious.indexOf(value)==-1){
        if (precious){
            $("#tag").val(precious+","+value);
        }else{
            $("#tag").val(value);
        }
    }
}

/**
 * 点击表单显示提示框
 */
function showSeleteTag() {
    $("#selete-tag").show();

}