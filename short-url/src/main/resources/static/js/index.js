
$(document).ready(function () {

  $('#shorten_url_btn').click(function () {
    console.log("shorten button click!");

    var longUrl = $('#long_url').val();

    $.ajax({
      method: "POST",
      url: "/urls",
      dataType: 'json',
      contentType: "application/json",
      data: JSON.stringify(longUrl)
    }).then(function (ApiResponse) {
      $('#short_url').append(
          '<div>' + ApiResponse.data.longUrl
          + '</div>' +
          '<div>' + ApiResponse.data.shortUrl
          + '</div>'
      )
    });

  });
});
