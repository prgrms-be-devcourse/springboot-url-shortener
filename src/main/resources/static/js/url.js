function loadShortUrl() {
    $("#t3-div-btn").remove();
    $("#info-button").remove();
    $("#statistics").text("");

    const originalUrl = document.getElementById('originalUrl').value;
    const strategy = document.getElementById('strategy').value;

    $.ajax({
        type: 'POST',
        url: '/api/urls',
        data: JSON.stringify({
            originalUrl: originalUrl,
            strategy: strategy
        }),
        headers: {
            'Content-Type': 'application/json'
        },
        success: function (data) {
            $("#t3-div").text(window.location.href + data.urlKey);
            const container = document.getElementById('button-group');
            makeButton("t3-div-btn", "btn btn-secondary", "복사", container, () => copy("t3-div-btn"))
            makeButton("info-button", "btn btn-secondary", "통계", container, () => loadInfo());
        },
        error: function () {
            location.reload();
            $("#result").remove();
            alert("유효하지 않은 url 입니다. 다시 입력해주세요.");
        }
    });

    function loadInfo() {

        const originalUrl = document.getElementById('originalUrl').value;

        $.ajax({
            type: 'GET',
            url: '/api/counts?url='  + originalUrl,

            success: function (data) {
                $("#statistics").text("해당 사이트에 대한 요청 횟수 : " + data);
            },
            error: function () {
                $("#statistics").text("조회에 실패했습니다. 재시도 바랍니다.");
            }
        });
    }
}

function makeButton(id, className, innerText, parent, command) {
    const button = document.createElement('button');
    button.id = id;
    button.className = className;
    button.innerText = innerText;
    button.onclick = command;
    button.style.cursor = 'pointer';
    button.style.margin = '5px';
    parent.appendChild(button);
}


function copy(btnID) {
    const copyBtn = document.getElementById(btnID);
    const textElement = document.getElementById(btnID.replace('-btn', ''));
    let text = textElement.textContent;

    if (text) {
        navigator.clipboard.writeText(text)
            .then(() => {
                if (copyBtn.textContent !== '완료') {
                    const originalText = copyBtn.textContent;
                    copyBtn.textContent = '완료';
                    setTimeout(() => {
                        copyBtn.textContent = originalText;
                    }, 500);
                }
            })
    }
}
