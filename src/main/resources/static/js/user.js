let index = {
  init: function () {
    $("#btn-join").on("click", () => { // ()=> 내부의 함수를 this를 바인딩하기 위해
      this.save();
    });

  },
  save: function () {
    let data = {
      username: $("#username").val(),
      password: $("#password").val(),
      email: $("#email").val()
    }

    commonMethod.ajaxTransmit("POST", "/auth/join", data, this.saveCallback, this.errCallback);
  },

  saveCallback: function () {
    alert('회원가입이 완료');
    location.href = "/";
  },

  errCallback: function (xhr) {
    data = xhr.responseJSON;
    commonMethod.setErr(data);
    commonMethod.setFieldErr(data.errors);
  }
}

index.init();