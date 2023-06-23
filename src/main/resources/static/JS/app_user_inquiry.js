document.addEventListener("DOMContentLoaded", () => {
  //各ボタン
  const addInquiry = document.getElementById("js-addInquiry");

  //エラー
  const errarTitle = document.getElementById("js-errarTitle");
  const errarInquiry = document.getElementById("js-errarInquiry");

  //input
  const inputTitle = document.getElementById("js-inputTitle");
  const inputInquiry = document.getElementById("js-inputInquiry");

  //エラーメッセージ非表示
  errarTitle.style.display = "none";
  errarInquiry.style.display = "none";

  //ID.PASS.ROLE
  const userId = document.getElementById("js-userId").value;
  console.log(userId);

  const listCounts = document.getElementById("js-listCounts");
  const pageCounts = document.getElementById("js-pageCounts");

  //問い合わせ送信ボタンクリック時処理
  addInquiry.addEventListener("click", () => {
    const titleText = inputTitle.value;
    const inquiryText = inputInquiry.value;

    //判別用FLAG
    let flg = 0;

    if (titleText == null || titleText == "") {
      errarTitle.style.display = "block";
      flg = 1;
    } else {
      errarTitle.style.display = "none";
    }

    if (inquiryText == null || inquiryText == "") {
      errarInquiry.style.display = "block";
      flg = 1;
    } else {
      errarInquiry.style.display = "none";
    }

    if (flg === 0) {
      //データ格納用
      const data = {
        id: 0, //仮
        inquiryTitle: titleText,
        inquiryText: inquiryText,
        userId: userId,
        inquiryAnswer: "",
        checkInquiry: 0,
        readInquiry: 0,
      };

      console.log("click");
      console.log(data);

      fetch("/insertInquiry", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
      })
        .then((response) => {
          if (response.ok) {
            console.log("POST request processed");
            listInquiryShow();
            window.setTimeout(function () {
              alert("お問い合わせ送信完了");
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

  /**
   * 初期のリストの生成
   */
  function listInquiryShow() {
    fetch(`/inquiries?searchId=${userId}`).then((res) => {
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

  // 詳細情報の表示
  function showInquiryDetails(inquiry) {
    console.log("click");

    //IDを検索と受け渡し
    fetch(`/inquiry?searchId=${inquiry.id}`).then((res) => {
      //RestControllerから受け取った値->res(成功/200 失敗/400)
      if (res.status === 400) {
        console.log("no");
      } else {
        res.json().then((data) => {
          console.log(data);

          //既読マークを付ける場合（解答済みのみ）
          if(data.checkInquiry == 1){
            fetch("/updateReadInquiry", {
              method: "PUT",
              headers: {
                "Content-Type": "application/json",
              },
              body: JSON.stringify(data),
            })
              .then((response) => {
                if (response.ok) {
                  console.log("既読マークOK");
                } else {
                  console.error("エラー");
                }
              })
              .catch((error) => {
                console.error("Error:", error);
              });
            
          }
          window.location.href = `/detail_inquiry/${data.id}`; //ControllerのGetに指示を出す
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
        listInquiryShow();
      }
    });
    //ページ切り替え用ボタン(next)
    nextButton.addEventListener("click", () => {
      const totalPages = Math.ceil(data.length / itemsPerPage);
      console.log(totalPages);
      if (currentPage < totalPages) {
        currentPage++;
        listInquiryShow();
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
    const th0 = document.createElement("th");
    th0.textContent = "ID";
    headerRow.appendChild(th0);
    const th1 = document.createElement("th");
    th1.textContent = "タイトル";
    headerRow.appendChild(th1);
    const th2 = document.createElement("th");
    th2.textContent = "対応状況";
    headerRow.appendChild(th2);
    const th3 = document.createElement("th");
    th3.textContent = "既読状況";
    headerRow.appendChild(th3);
    // const th4 = document.createElement("th");
    // th4.textContent = "詳細";
    // headerRow.appendChild(th4);
    thead.appendChild(headerRow);
    //.jsonで受け取った値->data

    // テーブルボディの生成
    const tbody = document.createElement("tbody");
    currentPageData.forEach((inquiry) => {
      const row = document.createElement("tr");
      for (const key in inquiry) {
        if (
          key == "inquiryAnswer" ||
          key == "inquiryText" ||
          key == "userId" ||
          key == "inquiryText"
        ) {
          //処理なし
        } else {
          const cell = document.createElement("td");
          if (key == "checkInquiry") {
            if (inquiry[key] == 1) {
              cell.textContent = "☑";
            } else {
              cell.textContent = "";
            }
          } else if (key == "readInquiry") {
            if (inquiry[key] == 1) {
              cell.textContent = "☑";
            } else {
              cell.textContent = "";
            }
          } else {
            cell.textContent = inquiry[key];
          }
          row.appendChild(cell);
        }
      }
      const cell = document.createElement("td");
      cell.textContent = "詳細";
      cell.addEventListener("click", () => showInquiryDetails(inquiry));
      cell.classList.add("detailBtn");

      row.appendChild(cell);
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

  listInquiryShow();
});
