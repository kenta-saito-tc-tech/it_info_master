document.addEventListener("DOMContentLoaded", () => {
    //各ボタン
    const loginButton = document.getElementById("js-loginButton");
  
    //エラー
    const errarId = document.getElementById("js-errarId");
    const errarPass = document.getElementById("js-errarPass");

    //input
    const inputId = document.getElementById("js-inputId");
    const inputPass = document.getElementById("js-inputPass");

  
    //エラーメッセージ非表示
    errarId.style.display = "none";
    errarPass.style.display = "none";
  
   
    //LOGINボタンクリック時処理
    loginButton.addEventListener("click", () => {
      const idText = inputId.value;
      const passText = inputPass.value;

      const data = { loginId: idText, password: passText };

      fetch("/log-check", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
      })
        .then((response) => {
          if (response.ok) {
            console.log("LOGIN request processed");
            window.location.href = "/menu";
            // 画面推移後にポップアップを表示
            window.setTimeout(function () {
              alert("login successfully");
            }, 1000);
          } else {
            console.error("LOGIN request failed");
            console.log(data);
            errarId.style.display = "block";
            errarPass.style.display = "block";
          }
        })
        .catch((error) => {
          console.error("Error:", error);
        });
    });
  });
  