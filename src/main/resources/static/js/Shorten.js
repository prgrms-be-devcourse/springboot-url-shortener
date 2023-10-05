function shorten() {
    const inputUrl = $('#url').val()

    const requestURL = 'http://localhost:8080/api/v1/shorten'

    $.post(requestURL, {url: inputUrl})
        .done(function (data) {
            $('#shortenedUrl').val(data)
        })
        .fail(function (error) {
            console.log(error)
        })
}
