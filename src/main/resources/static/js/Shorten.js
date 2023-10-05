function shorten() {
    const inputUrl = $('#url').val()
    const encodeMode = 'BASE_62'

    const requestURL = '/api/v1/shorten'

    $.post(requestURL,
        {
            url: inputUrl,
            encodeMode: encodeMode
        })
        .done(function (data) {
            $('#shortenedUrl').val(data)
        })
        .fail(function (error) {
            console.log(error)
        })
}
