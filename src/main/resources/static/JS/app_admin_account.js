document.addEventListener("DOMContentLoaded", () => {
  //各ボタン
  const addAccount = document.getElementById("js-addAccount");
  const deleteAccount = document.getElementById("js-deleteAccount");

  //エラー
  const errarId = document.getElementById("js-errarId");
  const errarName = document.getElementById("js-errarName");
  const errarPass = document.getElementById("js-errarPass");
  const errarRole = document.getElementById("js-errarRole");
  //input
  const inputId = document.getElementById("js-inputId");
  const inputName = document.getElementById("js-inputName");
  const inputPass = document.getElementById("js-inputPass");
  const listSelect = document.getElementById("js-listSelect");

  //エラーメッセージ非表示
  errarId.style.display = "none";
  errarName.style.display = "none";
  errarPass.style.display = "none";
  errarRole.style.display = "none";

  const listCounts = document.getElementById("js-listCounts");
  const pageCounts = document.getElementById("js-pageCounts");

  //権限を入れる
  const option1 = document.createElement("option");
  option1.value = "user";
  option1.text = "一般ユーザー";
  listSelect.appendChild(option1);
  const option2 = document.createElement("option");
  option2.value = "admin";
  option2.text = "管理者";
  listSelect.appendChild(option2);

  //addボタンクリック時処理
  addAccount.addEventListener("click", () => {
    const idText = inputId.value;
    const passText = inputPass.value;
    const nameText = inputName.value;
    const roleText = listSelect.value;

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
    if (roleText == null || roleText == "") {
      errarRole.style.display = "block";
      flg = 1;
    } else {
      errarRole.style.display = "none";
    }

    if (flg === 0) {
      //データ格納用
      const data = {
        id: 0,
        loginId: idText,
        password: passText,
        name: nameText,
        role: roleText,
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
            window.setTimeout(function () {
              alert("ユーザーの登録完了");
            }, 1000);
          } else {
            console.error("failed");
            // ポップアップを表示
            window.setTimeout(function () {
              alert("エラーが発生");
            }, 1000);
          }
        })
        .catch((error) => {
          console.error("Error:", error);
        });
    }
  });

  let checkedData = null;

  /**
   * 初期のリストの生成
   */
  function listUserShow() {
    fetch("/findAllUser").then((res) => {
      //RestControllerから受け取った値->res(成功/200 失敗/400)
      if (res.status === 400) {
        console.log("no");
      } else {
        //.jsonは非同期処理(.jsonの受け取り)
        res.json().then((data) => {
          console.log(data);
          listShow(data);
        });
      }
    });
  }

    /**
   * ページの表示切替
   */

    const itemsPerPage = 10; // 1ページあたりの件数
    let currentPage = 1; // 現在のページ番号
  
    const backButton = document.getElementById("backButton");
    const nextButton = document.getElementById("nextButton");

  //リスト表示用
  function listShow(data) {
    //ページ切り替え用ボタン（back）
    backButton.addEventListener("click", () => {
      console.log(currentPage);
      if (currentPage > 1) {
        currentPage--;
        listProductShow();
      }
    });
    //ページ切り替え用ボタン(next)
    nextButton.addEventListener("click", () => {
      const totalPages = Math.ceil(data.length / itemsPerPage);
      console.log(totalPages);
      if (currentPage < totalPages) {
        currentPage++;
        listProductShow();
      }
    });

    //表示するスタート位置
    const startIndex = (currentPage - 1) * itemsPerPage;
    const endIndex = startIndex + itemsPerPage;
    const currentPageData = data.slice(startIndex, endIndex);

    let counts = 0;
    // テーブルをHTMLに追加
    const tableContainer = document.getElementById("tableContainer");
    tableContainer.innerHTML = "";
    // テーブル要素の生成
    const table = document.createElement("table");
    // テーブルヘッダーの生成
    const thead = document.createElement("thead");
    const headerRow = document.createElement("tr");
    const th = document.createElement("th");
    th.textContent = "選択";
    headerRow.appendChild(th);
    const th0 = document.createElement("th");
    th0.textContent = "ID";
    headerRow.appendChild(th0);
    const th1 = document.createElement("th");
    th1.textContent = "ログインID";
    headerRow.appendChild(th1);
    const th2 = document.createElement("th");
    th2.textContent = "パスワード";
    headerRow.appendChild(th2);
    const th3 = document.createElement("th");
    th3.textContent = "名前";
    headerRow.appendChild(th3);
    const th4 = document.createElement("th");
    th4.textContent = "権限";
    headerRow.appendChild(th4);
    thead.appendChild(headerRow);
    //.jsonで受け取った値->data

    // テーブルボディの生成
    const tbody = document.createElement("tbody");
    currentPageData.forEach((userData) => {
      const row = document.createElement("tr");

      //チェックボックスの追加
      const radio = document.createElement("input");
      radio.type = "radio";
      radio.name="option";
      radio.addEventListener("change", function (event) {
        if (event.target.checked) {
          console.log("チェックされました");
          checkedData = userData; //チェックされたデータを格納
          console.log(checkedData);
        } else {
          console.log("チェックが解除されました");
          checkedData = null;
        }
      });
      row.appendChild(radio);

      //データの格納
      for (const key in userData) {
        const cell = document.createElement("td");
        if (key == "role") {
          if (userData[key] == "user") {
            cell.textContent = "一般ユーザー";
          } else {
            cell.textContent = "管理者";
          }
        } else {
          cell.textContent = userData[key];
        }
        row.appendChild(cell);
      }
      counts++;
      tbody.appendChild(row);
    });
    // テーブルにヘッダーとボディを追加
    table.appendChild(thead);
    table.appendChild(tbody);

    tableContainer.appendChild(table);
    listCounts.textContent = counts;
    pageCounts.textContent = currentPage;
  }

  //削除ボタンクリック時処理
  deleteAccount.addEventListener("click", () => {
    console.log("click");
    console.log(checkedData);

    fetch("/deleteUser", {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(checkedData),
    })
      .then((response) => {
        if (response.ok) {
          console.log("DELETE request processed");
          listUserShow();
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

  listUserShow();
});
