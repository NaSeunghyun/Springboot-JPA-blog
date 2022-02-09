let index = {
  init: function () {
    $("#btn-join").on("click", () => { // ()=> 내부의 함수를 this를 바인딩하기 위해
      this.save();
    });

  },
  save: function () {
    let data = {
      userName: $("#username").val(),
      password: $("#password").val(),
      email: $("#email").val()
    }

    //ajax호출시 default가 비동기호출
    //
    $.ajax({
      type: "POST",
      url: "/auth/join",
      data: JSON.stringify(data), // body데이터
      contentType: "application/json; charset=utf-8", // body타입 MIME
      dataType: "json"
    }).done(function (resp) {
      alert('회원가입이 완료');
      location.href = "/";
    }).fail(function (error) {
      alert(JSON.stringify(error));
    });
  }
}

index.init();