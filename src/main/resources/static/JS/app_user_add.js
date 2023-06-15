document.addEventListener("DOMContentLoaded", () => {
    //各ボタン
    const addAccount = document.getElementById("js-addAccount");
  
    //エラー
    const errarId = document.getElementById("js-errarId");
    const errarName = document.getElementById("js-errarName");
    const errarPass = document.getElementById("js-errarPass");
    //input
    const inputId = document.getElementById("js-inputId");
    const inputName = document.getElementById("js-inputName");
    const inputPass = document.getElementById("js-inputPass");
  
    //エラーメッセージ非表示
    errarId.style.display = "none";
    errarName.style.display = "none";
    errarPass.style.display = "none";
  
  
    //addボタンクリック時処理
    addAccount.addEventListener("click", () => {
      const idText = inputId.value;
      const passText = inputPass.value;
      const nameText = inputName.value;
  
      //判別用FLAG
      let flg = 0;
  
      if (idText == null || idText == "" || idText < 4) {
        errarId.style.display = "block";
        flg = 1;
      } else {
        errarId.style.display = "none";
      }
      if (passText == null || passText == "" || passText < 4) {
        errarPass.style.display = "block";
        flg = 1;
      } else {
        errarPass.style.display = "none";
      }
      if (nameText == null || nameText == "") {
        errarName.style.display = "block";
        flg = 1;
      } else {
        errarName.style.display = "none";
      }
  
      if (flg === 0) {
        //データ格納用
        const data = {
          id: 0,
          loginId: idText,
          password: passText,
          name: nameText,
          responsibleId: 1,
        };
  
        console.log("click");
        console.log(data);
  
        fetch("/insertUser", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(data),
        })
          .then((response) => {
            if (response.ok) {
              console.log("POST request processed");
              window.location.href = "/index";
              window.setTimeout(function () {
                alert("add your account successfuly");
              }, 1000);
            } else {
              console.error("failed");
              // ポップアップを表示
              window.setTimeout(function () {
                alert("add your account failed");
              }, 1000);
            }
          })
          .catch((error) => {
            console.error("Error:", error);
          });
      }
    });
  });
  