const key = 'shortUrlId';

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
            originUrl: $('#url').val(),
            method: $('#method').val(),
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/short-urls',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
            success: function (response) {
                const shortUrlInfo = document.getElementsByClassName('short-url-info');
                const shortenUrl = document.getElementById('shortenUrl');
                const originUrl = document.getElementById('originUrl');
                const numberOfRequests = document.getElementById('numberOfRequests');

                shortUrlInfo[0].classList.remove('none');
                shortenUrl.innerText = 'http://localhost:8080/' + response.shortenUrl;
                originUrl.innerText = response.originUrl;
                numberOfRequests.innerText = response.numberOfRequests;
                localStorage.setItem(key, response.shortUrlId);
            }
        }).fail(function(response) {
            errorResponse = response.responseJSON;
            alert(errorResponse.errors[0].cause);
        });
    },

    redirect: function () {
        $.ajax({
            type: 'PUT',
            url: `/api/v1/short-urls/${localStorage.getItem(key)}`,
            success: function (response) {
                const originUrl = response.originUrl;
                const numberOfRequests = document.getElementById('numberOfRequests');

                const anchor = document.createElement('a');
                anchor.setAttribute('href', `${originUrl}`);
                anchor.setAttribute('target', '_blank');

                anchor.click();

                numberOfRequests.innerText = response.numberOfRequests;
            }
        });
    }
};

main.init();
