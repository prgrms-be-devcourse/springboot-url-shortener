function shorten() {
    const inputUrl = $('#url').val()
    const shortenMode = 'BASE_62'

    const requestURL = '/api/v1/shorten'

    $.post(requestURL,
        {
            url: inputUrl,
            shortenMode: shortenMode
        })
        .done(function (data) {
            $('#shortenedUrl').val(data)
        })
        .fail(function (error) {
            console.log(error)
        })
}
