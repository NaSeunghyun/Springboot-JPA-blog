var commonMethod = {

  ajaxTransmit: function (method, url, data, fn_doneCallback, fn_failCallback) {

    $.ajax({
      type: method,
      url: url,
      data: JSON.stringify(data), // body데이터
      contentType: "application/json; charset=utf-8", // body타입 MIME
      dataType: "json",
      beforeSend: function () {
        $("#loader").show();
        $(".error_msg").empty();
      },
      complete: function () {
        $("#loader").hide();
      }
    }).done(function (resp) {
      fn_doneCallback();
    }).fail(function (xhr, textStatus) {
      fn_failCallback(xhr);
    });
  },

  setErr: function (data) {

    if (!data.message) {
      return;
    }
    $("#global_err").html(data.message);
  },

  setFieldErr: function (data) {

    if (!data) {
      return;
    }

    $(".field-err").removeClass("field-err");

    for (var i = 0; i < data.length; i++) {
      var field = data[i].field;
      $("#" + field).addClass("field-err");

      var errId = field + "_err";
      var message = data[i].reason;
      $("#" + errId).html(message);
    }
  }
}