function send() {
    $.ajax({
        cache : false,
        url: "/",
        type: "post",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify({
            url: $('#target-url').val(),
        }),
        success: function (data) {
            $('#result-url').val(location.href + data.shortUrl);
        },
        error: function (e) {
            // Swal.fire('등록 실패', '다시 한 번 시도해주세요.', 'error');
        }
    });
}

