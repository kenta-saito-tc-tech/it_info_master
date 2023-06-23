document.addEventListener("DOMContentLoaded", () => {
  const listCounts = document.getElementById("js-listCounts");
  const pageCounts = document.getElementById("js-pageCounts");

  /**
   * 初期のリストの生成
   */
  function listInquiryShow() {
    fetch("/inquiries_admin").then((res) => {
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
          window.location.href = `/admin_send_inquiry/${data.id}`; //ControllerのGetに指示を出す
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
    th1.textContent = "ユーザー名";
    headerRow.appendChild(th1);
    const th2 = document.createElement("th");
    th2.textContent = "タイトル";
    headerRow.appendChild(th2);
    const th3 = document.createElement("th");
    th3.textContent = "対応状況";
    headerRow.appendChild(th3);
    thead.appendChild(headerRow);

    // テーブルボディの生成
    const tbody = document.createElement("tbody");
    currentPageData.forEach((inquiry) => {
      const row = document.createElement("tr");
      for (const key in inquiry) {
        const cell = document.createElement("td");
        if (key == "checkInquiry") {
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
      const cell = document.createElement("td");
      cell.textContent = "対応";
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
