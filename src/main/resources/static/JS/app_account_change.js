document.addEventListener("DOMContentLoaded", () => {
  //各ボタン
  const changeName = document.getElementById("js-changeName");
  const changePass = document.getElementById("js-changePass");
  const deleteAccount = document.getElementById("js-deleteAccount");
  const backMenu = document.getElementById("js-backMenu");

  //エラー
  const errarName = document.getElementById("js-errarName");
  const errarOldPass = document.getElementById("js-errarOldPass");
  const errarNewPass = document.getElementById("js-errarNewPass");

  //input
  const inputName = document.getElementById("js-inputName");
  const inputOldPass = document.getElementById("js-inputOldPass");
  const inputNewPass = document.getElementById("js-inputNewPass");

  //エラーメッセージ非表示
  errarOldPass.style.display = "none";
  errarName.style.display = "none";
  errarNewPass.style.display = "none";

  //ID.PASS.ROLE
  const userId = document.getElementById("js-userId").value;
  const userPass = document.getElementById("js-userPass").value;
  const userRole = document.getElementById("js-userRole").value;

  //アカウント名変更ボタンクリック時処理
  changeName.addEventListener("click", () => {
    const nameText = inputName.value;

    //判別用FLAG
    let flg = 0;

    if (nameText == null || nameText == "") {
      errarName.style.display = "block";
      flg = 1;
    } else {
      errarName.style.display = "none";
    }

    if (flg === 0) {
      //データ格納用
      const data = {
        id: userId,
        loginId: "0", //仮
        password: userPass,
        name: nameText,
        role: userRole,
      };

      console.log("click");
      console.log(data);

      fetch("/updateUserName", {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
      })
        .then((response) => {
          if (response.ok) {
            console.log("PUT request processed");
            window.setTimeout(function () {
              alert("アカウント名変更完了");
            }, 1000);
          } else {
            console.error("failed");
            // ポップアップを表示
            window.setTimeout(function () {
              alert("エラーが発生しました");
            }, 1000);
          }
        })
        .catch((error) => {
          console.error("Error:", error);
        });
    }
  });

  //パスワード変更ボタンクリック時処理
  changePass.addEventListener("click", () => {
    const oldPassText = inputOldPass.value;
    const newPassText = inputNewPass.value;

    //判別用FLAG
    let flg = 0;

    if (oldPassText != userPass) {
      errarOldPass.style.display = "block";
      flg = 1;
    } else {
      errarOldPass.style.display = "none";
    }

    if (newPassText == null || newPassText == "" || newPassText < 4) {
      errarNewPass.style.display = "block";
      flg = 1;
    } else {
      errarNewPass.style.display = "none";
    }

    if (flg === 0) {
      //データ格納用
      const data = {
        id: userId,
        loginId: "0", //仮
        password: newPassText,
        name: "0", //仮
        role: userRole,
      };

      console.log("click");
      console.log(data);

      fetch("/updateUserPass", {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
      })
        .then((response) => {
          if (response.ok) {
            console.log("PUT request processed");
            window.setTimeout(function () {
              alert("パスワード変更完了");
            }, 1000);
          } else {
            console.error("failed");
            // ポップアップを表示
            window.setTimeout(function () {
              alert("エラーが発生しました");
            }, 1000);
          }
        })
        .catch((error) => {
          console.error("Error:", error);
        });
    }
  });

  //削除ボタンクリック時処理
  deleteAccount.addEventListener("click", () => {
    //データ格納用
    const data = {
      id: userId,
      loginId: "0", //仮
      password: "0", //仮
      name: "0", //仮
      role: userRole,
    };

    console.log("click");
    console.log(data);

    fetch("/deleteUser", {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    })
      .then((response) => {
        if (response.ok) {
          console.log("DELETE request processed");
          window.location.href = "/index";
          window.setTimeout(function () {
            alert("ユーザー削除完了");
          }, 1000);
        } else {
          console.error("failed");
          // ポップアップを表示
          window.setTimeout(function () {
            alert("エラーが発生しました");
          }, 1000);
        }
      })
      .catch((error) => {
        console.error("Error:", error);
      });
  });
});
