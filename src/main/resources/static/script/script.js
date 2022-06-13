function send() {
    $.ajax({
        cache : false,
        url: "/api",
        type: "post",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify({
            url: $('#target-url').val(),
        }),
        success: function (data) {
            Swal.fire('생성 성공', '단축 URL이 만들어졌어요!', 'success');
            $('#result-url').val(location.href + data.shortUrl);
        },
        error: function (e) {
            Swal.fire('생성 실패', '다시 한 번 시도해주세요.', 'error');
        }
    });
}

function copyToClipBoard() {
    var content = document.getElementById('result-url');
    content.select();
    document.execCommand('copy');
    Swal.fire('복사', '단축 URL을 복사했어요!', 'success');
}