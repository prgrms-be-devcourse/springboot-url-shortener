function send() {
    $.ajax({
        cache: false,
        url: "/api/urls",
        type: "post",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify({
            url: $('#target-url').val(),
        }),
        success: function (data) {
            Swal.fire('생성 성공', '단축 URL이 만들어졌어요!', 'success');
            $('#result-url').val(location.href + data.shortUrl);
            $('#count').text(data.count + '명의 사람들이 이 링크를 사용했어요!');
        },
        error: function (e) {
            let message = e.responseJSON.message;
            Swal.fire('생성 실패', message, 'error');
        }
    });
}

function copyToClipBoard() {
    var content = document.getElementById('result-url');
    content.select();
    document.execCommand('copy');
    Swal.fire('복사', '단축 URL을 복사했어요!', 'success');
}