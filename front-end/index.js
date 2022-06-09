
function getShortUrl() {
    var inputUrl = $('.input').val();
    console.log(inputUrl);
    $('.input').val('');
    let data = {
        'url': inputUrl,
        'algorithmType': "BASE62"
    };

    $.ajax({
        type: "POST",
        url: "http://localhost:8080/convert",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function(response) {
            alert('메시지 전송 성공');
            console.log(response);
            let shortUrl = response['shortUrl'];
            addHtml(inputUrl, shortUrl);
        },
        error: function(xhr, ajaxSettions, thrownError) {
            console.log(thrownError);
        }
    });
}

function addHtml(inputUrl, shortUrl) {
    let tempHtml = `
        
            <div class="url-item">
            <span>${inputUrl}</span>
                <span>${shortUrl}</span>
            </div>
        
    `;
    $('.url-list').append(tempHtml);
}