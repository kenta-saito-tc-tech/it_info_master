document.addEventListener("DOMContentLoaded", () => {
  //input
  const inputTitle = document.getElementById("js-inputTitle");
  const inputInquiry = document.getElementById("js-inputInquiry");
  const answerInquiry = document.getElementById("js-answerInquiry");

  //IDの値
  const id = document.getElementById("js-id").value;

  //初期表示

  fetch(`/inquiry?searchId=${id}`).then((res) => {
    //RestControllerから受け取った値->res(成功/200 失敗/400)
    if (res.status === 400) {
      console.log(res);
    } else {
      res.json().then((data) => {
        console.log(data);
        inputTitle.value = data.inquiryTitle;
        inputInquiry.textContent = data.inquiryText;
        answerInquiry.textContent = data.inquiryAnswer;
      });
    }
  });
});
