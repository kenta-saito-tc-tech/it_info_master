document.addEventListener("DOMContentLoaded", () => {
  //button
  const sendInquiryAns = document.getElementById("js-sendInquiryAns");

  //input
  const inputTitle = document.getElementById("js-inputTitle");
  const inputInquiry = document.getElementById("js-inputInquiry");
  const answerInquiry = document.getElementById("js-answerInquiry");

  const answerOk = document.getElementById("js-answerOk");

  //IDの値
  const id = document.getElementById("js-id").value;

  function sendDetailShow(){
  //初期表示
  fetch(`/inquiry?searchId=${id}`).then((res) => {
    //RestControllerから受け取った値->res(成功/200 失敗/400)
    if (res.status === 400) {
      console.log(res);
    } else {
      res.json().then((data) => {
        inputTitle.value = data.inquiryTitle;
        inputInquiry.textContent = data.inquiryText;
        answerInquiry.textContent = data.inquiryAnswer;
        //対応済み処理
        if (data.checkInquiry == 1) {
          answerOk.textContent = "対応済み";
          answerInquiry.readOnly = true;
          sendInquiryAns.style.display = "none";
        }
      });
    }
  });
  }



  //送信ボタンクリック時処理
  sendInquiryAns.addEventListener("click", () => {
    const titleText = inputTitle.value;
    const inquiryText = inputInquiry.value;
    const answerText = answerInquiry.value;

    //判別用FLAG
    let flg = 0;

    if (answerText == null || answerText == "") {
      flg = 1;
    } else {
      flg = 0;
    }

    if (flg === 0) {
      //データ格納用
      const inquiry = {
        id: id,
        inquiryTitle: titleText,
        inquiryText: inquiryText,
        userId: 0, //仮
        inquiryAnswer: answerText,
        checkInquiry: 1,
        readInquiry: 0,
      };

      console.log("click");
      console.log(inquiry);

      fetch("/updateAnswerInquiry", {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(inquiry),
      })
        .then((response) => {
          if (response.ok) {
            console.log("PUT request processed");
            sendDetailShow();
            window.setTimeout(function () {
              alert("解答送信完了");
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

  sendDetailShow();
});
