window.onload = () => {
    // loadStrategy();
}

function copy() {
    var content = document.getElementById('short-url');
    content.select();
    document.execCommand('copy');
    Swal.fire('복사', '단축 URL을 복사했어요!', 'success');
}


function shortUrl() {
    const originalUrl = document.getElementById('originalUrl').value;
    $("#url-result").text();

    $.ajax({
        type: 'POST',
        url: '/api/urls',
        data: JSON.stringify({
            originalUrl: originalUrl
        }),
        headers: {
            'Content-Type': 'application/json'
        },
        success: function (data) {
            $("#url-result").text( window.location.href + data.urlKey);
            $("#url-result").attr("href", window.location.href + data.urlKey);
        },
        error: function (xhr, status, error) {
            alert("https:// 단축 할 긴 주소를 입력하세요");
        }
    });
}
