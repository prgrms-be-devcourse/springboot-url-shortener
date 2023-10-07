window.onload = () => {
    loadResult();
}

function copy() {
    var content = document.getElementById('short-url');
    content.select();
    document.execCommand('copy');
    Swal.fire('복사', '단축 URL을 복사했어요!', 'success');
}

function loadResult() {
    document.getElementById('short-url-result').text
        = window.location.href + document.getElementById('short-url-result').text;
}
