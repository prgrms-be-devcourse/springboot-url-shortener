const main = {
    init : function () {
        const _this = this;
        $('#btn-create').on('click', function () {
            _this.create();
        });

        $('#btn-redirect').on('click', function () {
            _this.redirect();
        });
    },

    create : function () {
        const data = {
            originUrl: $('#originUrlInput').val(),
            algorithm: $('#algorithmSelector').val(),
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/short-urls',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
            success: function (response) {
                const shortUrlInfo = document.getElementsByClassName('short-url-info');
                const shortUrl = document.getElementById('shortUrl');
                const originUrl = document.getElementById('originUrl');
                const requestCount = document.getElementById('requestCount');

                shortUrlInfo[0].classList.remove('none');
                shortUrl.innerText = 'http://localhost:8080/' + response.shortUrl;
                originUrl.innerText = response.originUrl;
                requestCount.innerText = response.requestCount;
            },
            error: function (error){
                console.log(error.resonseJSON.message);
            }
        });
    },

    redirect: function () {
        const arr = $('#shortUrl')[0].innerText.split('/');

        const data = {
            shortUrl: `${arr[arr.length - 1]}`
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/short-urls/short-url',
            dataType: 'json',
            contentType: 'application/json; utf-8',
            data: JSON.stringify(data),
            success: function (response) {
                const originUrl = response.originUrl;
                const requestCount = document.getElementById('requestCount');

                const anchor = document.createElement('a');
                anchor.setAttribute('href', `${originUrl}`);
                anchor.setAttribute('target', '_blank');

                anchor.click();

                requestCount.innerText = response.requestCount;
            }
        });
    }
};

main.init();
